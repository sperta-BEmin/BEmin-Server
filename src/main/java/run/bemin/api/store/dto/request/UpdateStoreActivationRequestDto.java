package run.bemin.api.store.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateStoreActivationRequestDto(
    @NotNull
    Boolean isActive
) {}