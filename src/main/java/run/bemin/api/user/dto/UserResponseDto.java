package run.bemin.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import run.bemin.api.user.entity.UserRoleEnum;

@Getter
@AllArgsConstructor
public class UserResponseDto {
  private String userEmail;
  private String name;
  private String nickname;
  private String phone;
  private String address;
  private UserRoleEnum role;
}
