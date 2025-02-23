package run.bemin.api.category.dto.request;

import static run.bemin.api.store.dto.StoreValidationMessages.STORE_MINIMUM_PRICE_INVALID;

import jakarta.validation.constraints.Min;

public record UpdateMinimumPriceRequestDto(

    @Min(value = 0, message = STORE_MINIMUM_PRICE_INVALID)
    Integer minimumPrice

) {
}
