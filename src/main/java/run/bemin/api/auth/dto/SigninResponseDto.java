package run.bemin.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SigninResponseDto {
  private String token;
  private String email;
  private String nickname;
}
