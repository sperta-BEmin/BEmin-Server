package run.bemin.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import run.bemin.api.user.entity.UserRoleEnum;

@Getter
@AllArgsConstructor
public class SigninResponseDto {
  private String token;
  private String email;
  private String nickname;
  private UserRoleEnum role;
}
