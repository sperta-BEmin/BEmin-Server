package run.bemin.api.category.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import run.bemin.api.category.entity.Category;

public record CategoryDto(
    UUID categoryId,
    String name,
    Boolean isDeleted,
    String createdBy,
    String updatedBy,
    String deletedBy,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {
  public static CategoryDto fromEntity(Category category) {
    return new CategoryDto(
        category.getId(),
        category.getName(),
        category.getIsDeleted(),
        category.getCreatedBy(),
        category.getUpdatedBy(),
        category.getDeletedBy(),
        category.getCreatedAt(),
        category.getUpdatedAt(),
        category.getDeletedAt()
    );
  }
}
