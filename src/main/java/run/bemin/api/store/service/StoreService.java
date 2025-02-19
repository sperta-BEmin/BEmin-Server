package run.bemin.api.store.service;

import static run.bemin.api.general.exception.ErrorCode.CATEGORY_NOT_FOUND;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.category.entity.Category;
import run.bemin.api.category.exception.CategoryNotFoundException;
import run.bemin.api.category.repository.CategoryRepository;
import run.bemin.api.security.UserDetailsImpl;
import run.bemin.api.store.dto.StoreDto;
import run.bemin.api.store.dto.request.CreateStoreRequestDto;
import run.bemin.api.store.dto.request.UpdateAllStoreRequestDto;
import run.bemin.api.store.entity.Store;
import run.bemin.api.store.entity.StoreAddress;
import run.bemin.api.store.exception.CategoryCountExceededException;
import run.bemin.api.store.repository.StoreRepository;

@RequiredArgsConstructor
@Service
public class StoreService {

  private static final int MAX_CATEGORY_COUNT = 4;

  private final StoreRepository storeRepository;
  private final CategoryRepository categoryRepository;

  public Boolean existsStoreByName(String name) {
    return storeRepository.existsByName(name);
  }

  // 카테고리 수 검증 및 존재 여부 검증을 함께 수행
  private List<Category> validateAndFetchCategories(List<UUID> categoryIds) {
    if (categoryIds.size() > MAX_CATEGORY_COUNT) {
      throw new CategoryCountExceededException("최대 " + MAX_CATEGORY_COUNT + "개의 카테고리만 선택할 수 있습니다.");
    }
    List<Category> categories = categoryRepository.findAllByIdIn(categoryIds);
    if (categories.size() != categoryIds.size()) {
      throw new CategoryNotFoundException(CATEGORY_NOT_FOUND.getMessage());
    }
    return categories;
  }

  @Transactional
  public StoreDto createStore(CreateStoreRequestDto requestDto, UserDetailsImpl userDetails) {
    // 카테고리 검증 및 조회
    List<Category> categories = validateAndFetchCategories(requestDto.categoryIds());

    // StoreAddress 생성
    StoreAddress storeAddress = StoreAddress.builder()
        .zoneCode(requestDto.zoneCode())
        .bcode(requestDto.bcode())
        .jibunAddress(requestDto.jibunAddress())
        .roadAddress(requestDto.roadAddress())
        .detail(requestDto.detail())
        .build();

    // Store 생성
    Store store = Store.create(
        requestDto.name(),
        requestDto.phone(),
        requestDto.minimumPrice(),
        true, // isActive 기본값
        storeAddress,
        requestDto.userEmail()
    );

    // 양방향 연관관계 설정: StoreAddress에 Store 할당
    storeAddress.setStore(store);

    // 카테고리 연결 (각 카테고리 등록 시 createdBy 정보를 전달)
    categories.forEach(category -> store.addCategory(category, userDetails.getUsername()));

    // Store 저장 (Cascade 옵션 덕분에 주소도 함께 저장됨)
    Store savedStore = storeRepository.save(store);
    return StoreDto.fromEntity(savedStore);
  }

  @Transactional
  public StoreDto updateAllStore(UUID storeId, UpdateAllStoreRequestDto requestDto, UserDetailsImpl userDetails) {
    // 1. 가게 조회
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new IllegalArgumentException("해당 가게가 존재하지 않습니다."));

    // 2. 카테고리 검증 및 조회
    List<Category> categories = validateAndFetchCategories(requestDto.categoryIds());

    // 3. 기본 가게 정보 업데이트
    store.update(
        requestDto.name(),
        requestDto.phone(),
        requestDto.minimumPrice(),
        requestDto.isActive(),
        requestDto.userEmail()
    );

    // 4. 가게 주소 업데이트 (가게 주소가 null이 아니라고 가정)
    store.getStoreAddress().update(
        requestDto.zoneCode(),
        requestDto.bcode(),
        requestDto.jibunAddress(),
        requestDto.roadAddress(),
        requestDto.detail()
    );

    // 5. 카테고리 업데이트 (기존 목록 삭제 후 재설정)
    store.updateCategories(categories, userDetails.getUsername());

    // 6. 업데이트된 가게 저장
    Store updatedStore = storeRepository.save(store);
    return StoreDto.fromEntity(updatedStore);
  }
}
