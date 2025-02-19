package run.bemin.api.category.dto.request;

import java.util.UUID;

public record UpdateCategoryRequestDto(
    UUID categoryId,
    String name,
    Boolean isDeleted
) {
}
