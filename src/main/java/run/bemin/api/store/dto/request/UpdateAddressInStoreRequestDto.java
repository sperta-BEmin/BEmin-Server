package run.bemin.api.store.dto.request;

import static run.bemin.api.store.dto.StoreValidationMessages.STORE_ADDRESS_DETAIL_BLACK;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_ADDRESS_DETAIL_INVALID;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_BCODE_BLANK;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_BCODE_INVALID;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_JIBUN_ADDRESS_BLANK;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_JIBUN_ADDRESS_INVALID;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_ROAD_ADDRESS_BLANK;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_ROAD_ADDRESS_INVALID;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_ZONE_CODE_BLANK;
import static run.bemin.api.store.dto.StoreValidationMessages.STORE_ZONE_CODE_INVALID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateAddressInStoreRequestDto(

    @NotBlank(message = STORE_ZONE_CODE_BLANK)
    @Pattern(regexp = "^\\d{5}$", message = STORE_ZONE_CODE_INVALID)
    String zoneCode,

    @NotBlank(message = STORE_BCODE_BLANK)
    @Pattern(regexp = "^\\d{10}$", message = STORE_BCODE_INVALID)
    String bcode,

    @NotBlank(message = STORE_JIBUN_ADDRESS_BLANK)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s\\-,]{7,84}$", message = STORE_JIBUN_ADDRESS_INVALID)
    String jibunAddress,

    @NotBlank(message = STORE_ROAD_ADDRESS_BLANK)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s\\-,]{7,84}$", message = STORE_ROAD_ADDRESS_INVALID)
    String roadAddress,

    @NotBlank(message = STORE_ADDRESS_DETAIL_BLACK)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s\\-,]{1,84}$", message = STORE_ADDRESS_DETAIL_INVALID)
    String detail
) {
}
