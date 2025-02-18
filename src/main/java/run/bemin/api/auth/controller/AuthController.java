package run.bemin.api.auth.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.auth.dto.EmailCheckResponseDto;
import run.bemin.api.auth.dto.NicknameCheckResponseDto;
import run.bemin.api.auth.dto.SigninRequestDto;
import run.bemin.api.auth.dto.SigninResponseDto;
import run.bemin.api.auth.dto.SignupRequestDto;
import run.bemin.api.auth.dto.SignupResponseDto;
import run.bemin.api.auth.jwt.JwtUtil;
import run.bemin.api.auth.service.AuthService;
import run.bemin.api.general.response.ApiResponse;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ApiResponse<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDTO) {
    SignupResponseDto responseDto = authService.signup(requestDTO);
    return ApiResponse.from(HttpStatus.OK, "성공", responseDto);
  }

  @GetMapping("/email/exists")
  public ApiResponse<EmailCheckResponseDto> checkEmail(
      @RequestParam @NotBlank(message = "이메일을 입력해주세요.") String email) {
    EmailCheckResponseDto responseDto = authService.checkEmail(email);
    return ApiResponse.from(HttpStatus.OK, "이메일 중복 여부 확인", responseDto);
  }

  @GetMapping("/nickname/exists")
  public ApiResponse<NicknameCheckResponseDto> checkNickname(
      @RequestParam @NotBlank(message = "닉네임을 입력해주세요.") String nickname) {
    NicknameCheckResponseDto responseDto = authService.checkNickname(nickname);
    return ApiResponse.from(HttpStatus.OK, "닉네임 중복 여부 확인", responseDto);
  }

  @PostMapping("/signin")
  public ResponseEntity<ApiResponse<SigninResponseDto>> signin(@Valid @RequestBody SigninRequestDto requestDto) {
    SigninResponseDto responseDto = authService.signin(requestDto.getUserEmail(), requestDto.getPassword());

    log.info("request = {}", requestDto);
    return ResponseEntity.ok()
        .header(JwtUtil.AUTHORIZATION_HEADER, responseDto.getToken())
        .body(ApiResponse.from(HttpStatus.OK, "성공", responseDto));
  }

}
