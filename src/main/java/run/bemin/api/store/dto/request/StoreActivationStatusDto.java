package run.bemin.api.store.dto.request;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;

public record StoreActivationStatusDto(
    @NotNull UUID storeId,
    @NotNull Boolean isActive
) {}
