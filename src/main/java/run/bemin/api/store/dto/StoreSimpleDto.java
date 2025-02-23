package run.bemin.api.store.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import run.bemin.api.store.entity.Store;

public record StoreSimpleDto(
    UUID id,
    String name,
    String phone,
    Boolean isActive,
    List<String> categories
) {
  public static StoreSimpleDto fromEntity(Store store) {
    List<String> categoryNames = store.getStoreCategories().stream()
        .filter(sc -> !sc.isDeleted())
        .map(sc -> sc.getCategory().getName())
        .collect(Collectors.toList());
    return new StoreSimpleDto(
        store.getId(),
        store.getName(),
        store.getPhone(),
        store.getIsActive(),
        categoryNames
    );
  }
}