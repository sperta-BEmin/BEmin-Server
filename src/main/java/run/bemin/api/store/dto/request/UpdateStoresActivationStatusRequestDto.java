package run.bemin.api.store.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record UpdateStoresActivationStatusRequestDto(
    @NotEmpty
    List<StoreActivationStatusDto> storeActivationStatusList
) {}