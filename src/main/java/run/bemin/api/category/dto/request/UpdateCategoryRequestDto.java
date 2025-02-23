package run.bemin.api.category.dto.request;

import java.util.UUID;

public record UpdateCategoryRequestDto(
    String userEmail,
    UUID categoryId,
    String name,
    Boolean isDeleted
) {
}
