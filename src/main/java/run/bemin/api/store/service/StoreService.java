package run.bemin.api.store.service;

import static run.bemin.api.general.exception.ErrorCode.CATEGORY_NOT_FOUND;
import static run.bemin.api.general.exception.ErrorCode.STORE_ALREADY_DELETE;
import static run.bemin.api.general.exception.ErrorCode.STORE_NOT_FOUND;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.category.entity.Category;
import run.bemin.api.category.exception.CategoryNotFoundException;
import run.bemin.api.category.repository.CategoryRepository;
import run.bemin.api.security.UserDetailsImpl;
import run.bemin.api.store.dto.StoreDto;
import run.bemin.api.store.dto.request.CreateStoreRequestDto;
import run.bemin.api.store.dto.request.UpdateAllStoreRequestDto;
import run.bemin.api.store.dto.response.GetStoreResponseDto;
import run.bemin.api.store.entity.Store;
import run.bemin.api.store.entity.StoreAddress;
import run.bemin.api.store.exception.CategoryCountExceededException;
import run.bemin.api.store.exception.StoreNotFoundException;
import run.bemin.api.store.repository.StoreRepository;

@RequiredArgsConstructor
@Service
public class StoreService {

  private static final int MAX_CATEGORY_COUNT = 4;

  private final StoreRepository storeRepository;
  private final CategoryRepository categoryRepository;

  @Transactional(readOnly = true)
  public List<StoreDto> getStoresByUserEmail(String userEmail) {
    List<Store> stores = storeRepository.findByOwner_UserEmail(userEmail);
    return stores.stream()
        .map(StoreDto::fromEntity)
        .collect(Collectors.toList());
  }

  // 카테고리 수(MAX_CATEGORY_COUNT) 검증 및 존재 여부 검증을 함께 수행
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
    List<Category> categories = validateAndFetchCategories(requestDto.categoryIds());

    StoreAddress storeAddress = StoreAddress.builder()
        .zoneCode(requestDto.zoneCode())
        .bcode(requestDto.bcode())
        .jibunAddress(requestDto.jibunAddress())
        .roadAddress(requestDto.roadAddress())
        .detail(requestDto.detail())
        .build();

    // User 객체를 DTO의 userEmail 대신 인증된 사용자에서 가져옴
    Store store = Store.create(
        requestDto.name(),
        requestDto.phone(),
        requestDto.minimumPrice(),
        true, // isActive 기본값
        storeAddress,
        userDetails.getUser() // 수정된 부분: User 객체 전달
    );

    storeAddress.setStore(store); // 양방향 연관관계 설정: StoreAddress에 Store 할당

    // 카테고리 연결 (각 카테고리 등록 시 createdBy 정보를 전달)
    categories.forEach(category -> store.addCategory(category, userDetails.getUsername()));

    Store savedStore = storeRepository.save(store); // Store 저장 (Cascade 옵션 덕분에 주소도 함께 저장됨)
    return StoreDto.fromEntity(savedStore);
  }

  @Transactional
  public StoreDto updateAllStore(UUID storeId, UpdateAllStoreRequestDto requestDto, UserDetailsImpl userDetails) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new StoreNotFoundException(STORE_NOT_FOUND.getMessage()));

    List<Category> categories = validateAndFetchCategories(requestDto.categoryIds());

    // 여기서 userDetails.getUser()를 사용하여 User 객체를 전달합니다.
    store.update(
        requestDto.name(),
        requestDto.phone(),
        requestDto.minimumPrice(),
        requestDto.isActive(),
        userDetails.getUser()  // 수정된 부분: User 객체 전달
    );

    // 가게 주소 업데이트 (가게 주소가 null이 아니라고 가정)
    store.getStoreAddress().update(
        requestDto.zoneCode(),
        requestDto.bcode(),
        requestDto.jibunAddress(),
        requestDto.roadAddress(),
        requestDto.detail()
    );

    // 카테고리 업데이트 (기존 목록 삭제 후 재설정)
    store.updateCategories(categories, userDetails.getUsername());

    Store updatedStore = storeRepository.save(store);
    return StoreDto.fromEntity(updatedStore);
  }


  @Transactional(readOnly = true)
  public GetStoreResponseDto getStore(UUID storeId) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new StoreNotFoundException(STORE_NOT_FOUND.getMessage()));

    if (store.getIsDeleted()) {
      throw new RuntimeException(STORE_ALREADY_DELETE.getMessage());
    }
    return GetStoreResponseDto.fromEntity(store);
  }

  @Transactional
  public StoreDto softDeleteStore(UUID storeId, UserDetailsImpl userDetails) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new StoreNotFoundException(STORE_NOT_FOUND.getMessage()));

    store.softDelete(userDetails.getUsername());
    Store updatedStore = storeRepository.save(store);

    return StoreDto.fromEntity(updatedStore);
  }

  @Transactional(readOnly = true)
  public Page<GetStoreResponseDto> getAllStores(String storeName,
                                                Boolean isDeleted,
                                                Integer page,
                                                Integer size,
                                                String sortBy,
                                                Boolean isAsc) {
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
    Boolean filterDeleted = Optional.ofNullable(isDeleted).orElse(false);

    Page<Store> storePage = (storeName != null && !storeName.trim().isEmpty())
        ? storeRepository.findAllByIsDeletedAndNameContainingIgnoreCase(filterDeleted, storeName, pageable)
        : storeRepository.findAllByIsDeleted(filterDeleted, pageable);

    return storePage.map(GetStoreResponseDto::fromEntity);
  }


  @Transactional(readOnly = true)
  public Page<StoreDto> getAdminAllStores(String name,
                                          Integer page,
                                          Integer size,
                                          String sortBy,
                                          Boolean isAsc) {
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

    if (name != null && !name.trim().isEmpty()) {
      return storeRepository.findAllByNameContainingIgnoreCase(name, pageable)
          .map(StoreDto::fromEntity);
    }
    return storeRepository.findAll(pageable)
        .map(StoreDto::fromEntity);
  }
}
