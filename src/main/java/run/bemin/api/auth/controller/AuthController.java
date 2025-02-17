package run.bemin.api.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.auth.dto.SigninRequestDto;
import run.bemin.api.auth.dto.SigninResponseDto;
import run.bemin.api.auth.jwt.JwtUtil;
import run.bemin.api.auth.service.AuthService;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.user.dto.SignupRequestDto;
import run.bemin.api.user.dto.SignupResponseDto;


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

  @PostMapping("/signin")
  public ResponseEntity<ApiResponse<SigninResponseDto>> signin(@Valid @RequestBody SigninRequestDto requestDto) {
    SigninResponseDto responseDto = authService.signin(requestDto.getUserEmail(), requestDto.getPassword());

    log.info("request = {}", requestDto);
    return ResponseEntity.ok()
        .header(JwtUtil.AUTHORIZATION_HEADER, responseDto.getToken())
        .body(ApiResponse.from(HttpStatus.OK, "성공", responseDto));
  }

}
