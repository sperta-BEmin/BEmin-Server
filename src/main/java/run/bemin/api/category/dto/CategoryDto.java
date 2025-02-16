package run.bemin.api.category.dto;

import java.util.UUID;
import run.bemin.api.category.entity.Category;

public record CategoryDto(
    UUID categoryId,
    String name,
    Boolean isDeleted,
    String createdBy,
    String updatedBy,
    String deletedBy
) {

  // TODO: 추후에 isDeleted, createdBy, UpdatedBy, DeletedBy 는 권한 확인 후 수정할 수 있도록 수정하기
  public static CategoryDto fromEntity(Category category) {
    return new CategoryDto(
        category.getId(),
        category.getName(),
        category.getIsDeleted(),
        category.getCreatedBy(),
        category.getUpdatedBy(),
        category.getDeletedBy()
    );
  }


}
