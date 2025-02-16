package run.bemin.api.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import run.bemin.api.auth.dto.SigninRequestDto;
import run.bemin.api.auth.dto.SigninResponseDto;
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
    public ApiResponse<SigninResponseDto> signin(@Valid @RequestBody SigninRequestDto requestDto) {
        SigninResponseDto responseDto = authService.signin(requestDto.getUserEmail(), requestDto.getPassword());

        log.info("request = {}",requestDto);
        return ApiResponse.from(HttpStatus.OK, "성공", responseDto);
    }




}
