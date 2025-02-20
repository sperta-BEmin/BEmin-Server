package run.bemin.api.store.dto.request;

import static run.bemin.api.store.dto.StoreValidationMessages.STORE_PHONE_BLANK;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_PHONE_INVALID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateStorePhoneRequestDto(
    // 전화번호: 10~11자리 숫자만 허용
    @NotBlank(message = STORE_PHONE_BLANK)
    @Pattern(
        regexp = "^\\d{10,11}$",
        message = STORE_PHONE_INVALID
    )
    String phone
) {}