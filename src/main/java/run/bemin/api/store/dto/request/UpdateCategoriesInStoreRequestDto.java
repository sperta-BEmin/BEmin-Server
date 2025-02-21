package run.bemin.api.store.dto.request;

import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public record UpdateCategoriesInStoreRequestDto(
    // 선택 가능한 카테고리 ID 목록 (최대 4개)
    @Size(max = 4, message = "최대 4개의 카테고리만 선택할 수 있습니다.")
    List<UUID> categoryIds
) {
}
