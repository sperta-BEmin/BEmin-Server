package run.bemin.api.user.dto;

import java.util.UUID;
import lombok.Getter;
import run.bemin.api.user.entity.UserAddress;

@Getter
public class AddressResponseDto {
  private UUID userAddressId;
  private String zoneCode;
  private String bcode;
  private String jibunAddress;
  private String roadAddress;
  private String detail;
  private boolean isRepresentative;

  public AddressResponseDto(UserAddress address) {
    this.userAddressId = address.getId();
    this.zoneCode = address.getZoneCode();
    this.bcode = address.getBcode();
    this.jibunAddress = address.getJibunAddress();
    this.roadAddress = address.getRoadAddress();
    this.detail = address.getDetail();
    this.isRepresentative = address.isRepresentative();
  }
}
