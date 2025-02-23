package run.bemin.api.category.dto.response;

import java.util.UUID;
import run.bemin.api.category.entity.Category;

public record GetCategoryResponseDto(
    UUID categoryId,
    String name
) {
  public static GetCategoryResponseDto fromEntity(Category category) {
    return new GetCategoryResponseDto(
        category.getId(),
        category.getName()
    );
  }
}
