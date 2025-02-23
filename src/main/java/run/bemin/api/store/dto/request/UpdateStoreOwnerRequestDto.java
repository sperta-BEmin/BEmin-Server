package run.bemin.api.store.dto.request;


import static run.bemin.api.store.dto.StoreValidationMessages.USER_EMAIL_BLANK;
import static run.bemin.api.store.dto.StoreValidationMessages.USER_EMAIL_INVALID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateStoreOwnerRequestDto(
    // 사용자 이메일: 표준 이메일 형식
    @NotBlank(message = USER_EMAIL_BLANK)
    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
        message = USER_EMAIL_INVALID
    )
    String userEmail
) {}