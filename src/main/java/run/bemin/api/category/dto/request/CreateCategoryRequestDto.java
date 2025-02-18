package run.bemin.api.category.dto.request;

import static run.bemin.api.category.dto.CategoryValidationMessages.CATEGORY_NAME_BLANK;
import static run.bemin.api.category.dto.CategoryValidationMessages.CATEGORY_NAME_INVALID;
import static run.bemin.api.category.dto.CategoryValidationMessages.USER_EMAIL_BLANK;
import static run.bemin.api.category.dto.CategoryValidationMessages.USER_EMAIL_INVALID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateCategoryRequestDto(

    @NotBlank(message = CATEGORY_NAME_BLANK)
    @Pattern(
        regexp = "^[가-힣0-9·!\\s]{1,16}$",
        message = CATEGORY_NAME_INVALID
    )
    String name
) {

}
