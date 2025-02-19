package run.bemin.api.category.dto.response;

import java.util.UUID;
import run.bemin.api.category.entity.Category;

public record GetStoreCategoryResponseDto(
    UUID categoryId,
    String name
) {
  public static GetStoreCategoryResponseDto fromEntity(Category category) {
    return new GetStoreCategoryResponseDto(
        category.getId(),
        category.getName()
    );
  }
}
