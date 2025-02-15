package run.bemin.api.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.user.dto.EmailCheckResponseDto;
import run.bemin.api.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 이메일 중복 체크
    @GetMapping("/email/exists")
    public ApiResponse<EmailCheckResponseDto> checkEmail(@RequestParam String email) {
        EmailCheckResponseDto responseDto = userService.checkEmail(email);
        return ApiResponse.from(HttpStatus.OK, "이메일 중복 여부 확인", responseDto);
    }
}
