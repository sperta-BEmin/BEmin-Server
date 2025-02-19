package run.bemin.api.store.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.category.entity.Category;
import run.bemin.api.category.repository.CategoryRepository;
import run.bemin.api.security.UserDetailsImpl;
import run.bemin.api.store.dto.StoreDto;
import run.bemin.api.store.dto.request.CreateStoreRequestDto;
import run.bemin.api.store.entity.Store;
import run.bemin.api.store.entity.StoreAddress;
import run.bemin.api.store.repository.StoreRepository;


@RequiredArgsConstructor
@Service
public class StoreService {

  private final StoreRepository storeRepository;
  private final CategoryRepository categoryRepository;

  public Boolean existsStoreByName(String name) {
    return storeRepository.existsByName(name);
  }

  @Transactional
  public StoreDto createStore(CreateStoreRequestDto requestDto, UserDetailsImpl userDetails) {
    if (requestDto.categoryIds().size() > 4) { // (추가 검증이 필요하면) 카테고리 선택 개수 검증
      throw new IllegalArgumentException("최대 4개의 카테고리만 선택할 수 있습니다.");
    }

    // 카테고리 조회
    List<Category> categories = categoryRepository.findAllByIdIn(requestDto.categoryIds());
    if (categories.size() != requestDto.categoryIds().size()) {
      throw new IllegalArgumentException("존재하지 않는 카테고리가 있습니다.");
    }

    // StoreAddress 생성
    StoreAddress storeAddress = StoreAddress.builder()
        .zoneCode(requestDto.zoneCode())
        .bcode(requestDto.bcode())
        .jibunAddress(requestDto.jibunAddress())
        .roadAddress(requestDto.roadAddress())
        .detail(requestDto.detail())
        .build();

    // Store 생성 (생성일은 현재 시간 사용)
    Store store = Store.create(
        requestDto.name(),
        requestDto.phone(),
        requestDto.minimumPrice(),
        true, // isActive 기본값
        storeAddress,
        requestDto.userEmail()
//        userDetails.getUsername(),
//        LocalDateTime.now()
    );

    // 양방향 연관관계 설정: StoreAddress 에 Store 할당
    storeAddress.setStore(store);

    // Store 와 여러 카테고리 연결 (각 카테고리 등록 시 createdBy 정보를 함께 전달)
    categories.forEach(category -> store.addCategory(category, userDetails.getUsername()));

    // Store 저장 (Cascade 옵션 덕분에 주소도 함께 저장됨)
    Store savedStore = storeRepository.save(store);
    return StoreDto.fromEntity(savedStore);
  }
}