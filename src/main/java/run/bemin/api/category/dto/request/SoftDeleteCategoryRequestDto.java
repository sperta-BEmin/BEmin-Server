package run.bemin.api.category.dto.request;

import java.util.UUID;

public record SoftDeleteCategoryRequestDto(
    String userEmail,
    UUID categoryId
) {
}
