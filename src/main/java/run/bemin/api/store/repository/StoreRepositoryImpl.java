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

    // BooleanBuilder 를 통해 조건을 동적으로 구성
    BooleanBuilder builder = new BooleanBuilder();

    // 가게 조건: 삭제된 가게 제외
    builder.and(store.isDeleted.eq(false));

    // 카테고리 조건: categoryName 이 입력되었으면 해당 카테고리 이름과 정확히 일치하는 조건 추가
    if (categoryName != null && !categoryName.isEmpty()) {
      builder.and(category.name.eq(categoryName));
    }

    // 지점 이름 조건: storeName 이 입력되었으면 지점 이름에 해당 키워드가 포함되는 조건 추가 (대소문자 무시)
    if (storeName != null && !storeName.isEmpty()) {
      builder.and(store.name.containsIgnoreCase(storeName));
    }

    // 기본 QueryDSL 쿼리 구성 (중복 제거를 위해 distinct 사용)
    JPAQuery<Store> query = queryFactory.selectFrom(store)
        .distinct()
        // Store 와 연관된 StoreCategory, Category 엔티티를 조인
        .leftJoin(store.storeCategories, storeCategory)
        .leftJoin(storeCategory.category, category)
        .where(builder);

    // 전체 개수를 구하기 위한 카운트 쿼리 생성
    // (정렬 조건 등은 카운트에 영향을 주지 않도록 별도 처리)
    long total = query.fetchCount();

    // 페이징 적용: offset, limit
    query.offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    for (Sort.Order order : pageable.getSort()) {
      // "store" 엔티티에 대해 동적으로 정렬 조건 지정
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