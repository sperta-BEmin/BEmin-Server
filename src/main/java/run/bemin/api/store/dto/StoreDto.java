package run.bemin.api.store.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import run.bemin.api.category.dto.CategoryDto;
import run.bemin.api.store.dto.StoreAddressDto;
import run.bemin.api.store.entity.Store;

public record StoreDto(
    UUID id,
    String name,
    String phone,
    Integer minimumPrice,
    Float rating,
    Boolean isDeleted,
    String userEmail,
    StoreAddressDto storeAddress,
    List<CategoryDto> categories,
    String createdBy,
    LocalDateTime createdAt,
    String updatedBy,
    LocalDateTime updatedAt,
    String deletedBy,
    LocalDateTime deletedAt
) {

  public static StoreDto fromEntity(Store store) {
    return new StoreDto(
        store.getId(),
        store.getName(),
        store.getPhone(),
        store.getMinimumPrice(),
        store.getRating(),
        store.getIsDeleted(),
        store.getOwner() != null ? store.getOwner().getUserEmail() : null,
        store.getStoreAddress() != null ? StoreAddressDto.fromEntity(store.getStoreAddress()) : null,
        store.getStoreCategories().stream()
            .filter(sc -> !sc.isDeleted())
            .map(sc -> CategoryDto.fromEntity(sc.getCategory()))
            .collect(Collectors.toList()),
        store.getCreatedBy(),
        store.getCreatedAt(),
        store.getUpdatedBy(),
        store.getUpdatedAt(),
        store.getDeletedBy(),
        store.getDeletedAt()
    );
  }
}
