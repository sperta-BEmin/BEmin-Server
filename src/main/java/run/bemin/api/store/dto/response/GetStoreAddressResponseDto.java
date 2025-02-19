package run.bemin.api.store.dto.response;

import java.util.UUID;
import run.bemin.api.store.entity.StoreAddress;

public record GetStoreAddressResponseDto(
    UUID storeAddressId,
    String zoneCode,
    String bcode,
    String jibunAddress,
    String roadAddress,
    String detail
) {
  public static GetStoreAddressResponseDto fromEntity(StoreAddress storeAddress) {
    return new GetStoreAddressResponseDto(
        storeAddress.getId(),
        storeAddress.getZoneCode(),
        storeAddress.getBcode(),
        storeAddress.getJibunAddress(),
        storeAddress.getRoadAddress(),
        storeAddress.getDetail()
    );
  }
}
