package run.bemin.api.store.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import run.bemin.api.category.dto.CategoryDto;
import run.bemin.api.store.dto.StoreAddressDto;
import run.bemin.api.store.entity.Store;

public record AdminStoreResponseDto(
    UUID storeId,
    String name,
    String phone,
    Integer minimumPrice,
    Float rating,
    StoreAddressDto storeAddress,    // 관리자용 주소 DTO (모든 감사 정보 포함)
    List<CategoryDto> categories,    // 관리자용 카테고리 DTO (모든 감사 정보 포함)
    LocalDateTime createdAt,
    String createdBy,
    LocalDateTime updatedAt,
    String updatedBy
) {
  public static AdminStoreResponseDto fromEntity(Store store) {
    StoreAddressDto storeAddressResponse = store.getStoreAddress() != null
        ? StoreAddressDto.fromEntity(store.getStoreAddress())
        : null;

    List<CategoryDto> categoryResponses = store.getStoreCategories().stream()
        .filter(sc -> !sc.getIsDeleted())
        .map(sc -> CategoryDto.fromEntity(sc.getCategory()))
        .collect(Collectors.toList());

    return new AdminStoreResponseDto(
        store.getId(),
        store.getName(),
        store.getPhone(),
        store.getMinimumPrice(),
        store.getRating(),
        storeAddressResponse,
        categoryResponses,
        store.getCreatedAt(),
        store.getCreatedBy(),
        store.getUpdatedAt(),
        store.getUpdatedBy()
    );
  }

  public static List<AdminStoreResponseDto> fromEntities(Iterable<Store> stores) {
    return StreamSupport.stream(stores.spliterator(), false)
        .map(AdminStoreResponseDto::fromEntity)
        .collect(Collectors.toList());
  }
}
