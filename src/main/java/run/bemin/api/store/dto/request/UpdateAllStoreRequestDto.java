package run.bemin.api.store.dto.request;

import static run.bemin.api.store.dto.StoreValidationMessages.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public record UpdateAllStoreRequestDto(
    @NotBlank(message = STORE_NAME_BLANK)
    @Pattern(regexp = "^[a-zA-Z가-힣0-9\\s]{1,36}$", message = STORE_NAME_INVALID)
    String name,

    @NotBlank(message = STORE_PHONE_BLANK)
    @Pattern(regexp = "^\\d{10,11}$", message = STORE_PHONE_INVALID)
    String phone,

    @Min(value = 0, message = STORE_MINIMUM_PRICE_INVALID)
    Integer minimumPrice,

    @NotBlank(message = STORE_IS_ACTIVE_BLANK)
    Boolean isActive,

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
    String detail,

    @Size(max = 4, message = "최대 4개의 카테고리만 선택할 수 있습니다.")
    List<UUID> categoryIds
) {}
