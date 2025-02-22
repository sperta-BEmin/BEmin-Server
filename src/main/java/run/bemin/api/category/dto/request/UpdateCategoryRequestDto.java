package run.bemin.api.category.dto.request;

public record UpdateCategoryRequestDto(
    String name,
    Boolean isDeleted
) {
}
