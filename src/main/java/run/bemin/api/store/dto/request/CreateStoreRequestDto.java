package run.bemin.api.store.dto.request;

import static run.bemin.api.store.dto.StoreValidationMessages.STORE_ADDRESS_DETAIL_BLACK;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_ADDRESS_DETAIL_INVALID;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_BCODE_BLANK;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_BCODE_INVALID;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_JIBUN_ADDRESS_BLANK;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_JIBUN_ADDRESS_INVALID;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_MINIMUM_PRICE_INVALID;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_NAME_BLANK;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_NAME_INVALID;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_PHONE_BLANK;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_PHONE_INVALID;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_ROAD_ADDRESS_BLANK;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_ROAD_ADDRESS_INVALID;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_ZONE_CODE_BLANK;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_ZONE_CODE_INVALID;
import static run.bemin.api.store.dto.StoreValidationMessages.USER_EMAIL_BLANK;
import static run.bemin.api.store.dto.StoreValidationMessages.USER_EMAIL_INVALID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

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

    @NotBlank(message = USER_EMAIL_BLANK)
    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
        message = USER_EMAIL_INVALID
    )
    String userEmail,

    @NotBlank(message = STORE_ZONE_CODE_BLANK)
    @Pattern(
        regexp = "^\\d{5}$",
        message = STORE_ZONE_CODE_INVALID
    )
    String zoneCode,

    @NotBlank(message = STORE_BCODE_BLANK)
    @Pattern(
        regexp = "^\\d{10}$",
        message = STORE_BCODE_INVALID
    )
    String bcode,

    @NotBlank(message = STORE_JIBUN_ADDRESS_BLANK)
    @Pattern(
        regexp = "^[가-힣a-zA-Z0-9\\s\\-,]{7,84}$",
        message = STORE_JIBUN_ADDRESS_INVALID
    )
    String jibunAddress,

    @NotBlank(message = STORE_ROAD_ADDRESS_BLANK)
    @Pattern(
        regexp = "^[가-힣a-zA-Z0-9\\s\\-,]{7,84}$",
        message = STORE_ROAD_ADDRESS_INVALID
    )
    String roadAddress,

    @NotBlank(message = STORE_ADDRESS_DETAIL_BLACK)
    @Pattern(
        regexp = "^[가-힣a-zA-Z0-9\\s\\-,]{1,84}$",
        message = STORE_ADDRESS_DETAIL_INVALID
    )
    String detail,

    @Size(max = 4, message = "최대 4개의 카테고리만 선택할 수 있습니다.")
    List<UUID> categoryIds
) {
}