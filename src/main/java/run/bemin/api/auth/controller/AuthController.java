package run.bemin.api.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import run.bemin.api.auth.service.AuthService;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.user.dto.SignupRequestDto;
import run.bemin.api.user.dto.SignupResponseDto;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDTO) {
        SignupResponseDto responseDto = authService.signup(requestDTO);
        return ApiResponse.from(HttpStatus.OK, "성공", responseDto);
    }


}
