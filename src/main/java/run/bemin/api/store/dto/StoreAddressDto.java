package run.bemin.api.store.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import run.bemin.api.store.entity.StoreAddress;

public record StoreAddressDto(
    UUID storeAddressId,
    String zoneCode,
    String bcode,
    String jibunAddress,
    String roadAddress,
    String detail,
    LocalDateTime createdAt,
    String createdBy,
    LocalDateTime updatedAt,
    String updatedBy,
    LocalDateTime deletedAt,
    String deletedBy
) {
  public static StoreAddressDto fromEntity(StoreAddress storeAddress) {
    return new StoreAddressDto(
        storeAddress.getId(),
        storeAddress.getZoneCode(),
        storeAddress.getBcode(),
        storeAddress.getJibunAddress(),
        storeAddress.getRoadAddress(),
        storeAddress.getDetail(),
        storeAddress.getCreatedAt(),
        storeAddress.getCreatedBy(),
        storeAddress.getUpdatedAt(),
        storeAddress.getUpdatedBy(),
        storeAddress.getDeletedAt(),
        storeAddress.getDeletedBy()
    );
  }
}
