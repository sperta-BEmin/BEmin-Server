package run.bemin.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
  private String zoneCode;
  private String bcode;
  private String jibunAddress;
  private String roadAddress;
  private String detail;
}
