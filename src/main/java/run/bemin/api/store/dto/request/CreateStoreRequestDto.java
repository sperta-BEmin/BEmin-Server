package run.bemin.api.store.dto.request;

import static run.bemin.api.store.dto.StoreValidationMessages.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateStoreRequestDto(
    @NotBlank(message = STORE_NAME_BLANK)
    @Pattern(
        regexp = "^[a-zA-Z가-힣0-9\\s]{1,36}$",
        message = STORE_NAME_INVALID
    )
    String name,

    @NotBlank(message = STORE_PHONE_BLANK)
    @Pattern(
        regexp = "^\\d{10,11}$",
        message = STORE_PHONE_INVALID
    )
    String phone,

    @Min(value = 0, message = STORE_MINIMUM_PRICE_INVALID)
    Integer minimumPrice,

    String userEmail
) {
}
