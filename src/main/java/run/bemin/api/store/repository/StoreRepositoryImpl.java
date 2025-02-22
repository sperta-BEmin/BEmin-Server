package run.bemin.api.store.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import run.bemin.api.category.entity.QCategory;
import run.bemin.api.store.entity.QStore;
import run.bemin.api.store.entity.QStoreCategory;
import run.bemin.api.store.entity.Store;

public class StoreRepositoryImpl implements StoreRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public StoreRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Page<Store> searchStores(String categoryName, String storeName, Pageable pageable) {
    QStore store = QStore.store;
    QStoreCategory storeCategory = QStoreCategory.storeCategory;
    QCategory category = QCategory.category;

    boolean hasCategory = (categoryName != null && !categoryName.trim().isEmpty());
    boolean hasStoreName = (storeName != null && !storeName.trim().isEmpty());

    // [사전 체크] 입력된 카테고리명이 Category 테이블에 존재하는지 확인
    if (hasCategory) {
      long categoryExistCount = queryFactory
          .selectFrom(category)
          .where(category.name.eq(categoryName))
          .fetchCount();
      if (categoryExistCount == 0) {
        // 요청된 카테고리가 DB에 전혀 없으면 빈 결과 반환
        return new PageImpl<>(List.of(), pageable, 0);
      }
    }

    // 동적 조건 구성
    BooleanBuilder builder = new BooleanBuilder();
    // 삭제되지 않은 가게만 조회
    builder.and(store.isDeleted.eq(false));

    if (hasCategory) {
      // storeCategory 와 category 엔티티를 이용해서,
      // 가게가 해당 카테고리(ex: "한식")와 연결되어 있고, 해당 연결(StoreCategory)이 삭제되지 않았어야 함
      builder.and(storeCategory.isDeleted.eq(false))
          .and(category.name.eq(categoryName));
    }

    if (hasStoreName) {
      // 가게 이름에 storeName 키워드가 포함되어야 함 (대소문자 무시)
      builder.and(store.name.containsIgnoreCase(storeName));
    }

    // join 처리: 카테고리 조건을 위해 StoreCategory와 Category 엔티티와 조인
    JPAQuery<Store> query = queryFactory.selectFrom(store)
        .distinct()  // 동일 가게가 여러 카테고리로 인해 중복되지 않도록
        .leftJoin(store.storeCategories, storeCategory)
        .leftJoin(storeCategory.category, category)
        .where(builder);

    // 전체 개수 조회
    long total = query.fetchCount();

    // 페이징 적용: offset, limit
    query.offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    // 정렬 조건 적용 (Pageable의 정렬 조건 사용)
    for (Sort.Order order : pageable.getSort()) {
      PathBuilder<Store> path = new PathBuilder<>(Store.class, "store");
      query.orderBy(new OrderSpecifier<>(
          order.isAscending() ? Order.ASC : Order.DESC,
          path.get(order.getProperty(), Comparable.class)
      ));
    }

    List<Store> stores = query.fetch();
    return new PageImpl<>(stores, pageable, total);
  }

}