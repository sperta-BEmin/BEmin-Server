package run.bemin.api.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.auth.dto.SigninRequestDto;
import run.bemin.api.auth.dto.SigninResponseDto;
import run.bemin.api.auth.dto.SignoutResponseDto;
import run.bemin.api.auth.jwt.JwtUtil;
import run.bemin.api.auth.service.AuthService;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthSessionController {

  private final AuthService authService;

  /**
   * 로그인
   */
  @PostMapping("/signin")
  public ResponseEntity<ApiResponse<SigninResponseDto>> signin(@Valid @RequestBody SigninRequestDto requestDto) {
    SigninResponseDto responseDto = authService.signin(requestDto.getUserEmail(), requestDto.getPassword());
    log.info("request = {}", requestDto);
    return ResponseEntity.ok()
        .header(JwtUtil.AUTHORIZATION_HEADER, responseDto.getToken())
        .body(ApiResponse.from(HttpStatus.OK, "성공", responseDto));
  }

  /**
   * 로그아웃 기능 (JWT 기반) - 추후 확장성(블랙리스트, Redis 활용 등)을 고려하여 로그아웃을 구현할 수 있도록 함. - 현재는 JWT 기반 로그아웃으로, 서버 측에서는 단순히 성공 메시지만
   * 반환하고, 클라이언트가 토큰을 제거하여 로그아웃을 완료하도록 처리함.
   */
  @PostMapping("/signout")
  public ResponseEntity<ApiResponse<SignoutResponseDto>> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    String userEmail = userDetails.getUsername();
    SignoutResponseDto signout = new SignoutResponseDto(userEmail, "로그아웃 성공");
    return ResponseEntity.ok(ApiResponse.from(HttpStatus.OK, "로그아웃 성공", signout));
  }
}
