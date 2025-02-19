package run.bemin.api.store.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import run.bemin.api.store.entity.Store;

public record StoreDto(
    UUID id,
    String name,
    String phone,
    Integer minimumPrice,
    Float rating,
    Boolean isDeleted,
    String userEmail,
    String createdBy,
    String updatedBy,
    String deletedBy,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {

  public static StoreDto fromEntity(Store store) {
    return new StoreDto(
        store.getId(),
        store.getName(),
        store.getPhone(),
        store.getMinimumPrice(),
        store.getRating(),
        store.isDeleted(),
        store.getUserEmail(),
        store.getCreatedBy(),
        store.getUpdatedBy(),
        store.getDeletedBy(),
        store.getCreatedAt(),
        store.getUpdatedAt(),
        store.getDeletedAt()
    );
  }
}
