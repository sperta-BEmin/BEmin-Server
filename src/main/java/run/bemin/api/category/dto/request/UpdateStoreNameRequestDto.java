package run.bemin.api.category.dto.request;

import static run.bemin.api.store.dto.StoreValidationMessages.STORE_NAME_BLANK;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_NAME_INVALID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateStoreNameRequestDto(
    // 가게 이름: 1~36자의 영문, 한글, 숫자, 공백만 허용
    @NotBlank(message = STORE_NAME_BLANK)
    @Pattern(
        regexp = "^[a-zA-Z가-힣0-9\\s]{1,36}$",
        message = STORE_NAME_INVALID
    )
    String name
) {
}
