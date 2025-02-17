package run.bemin.api.user.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.user.dto.EmailCheckResponseDto;
import run.bemin.api.user.dto.NicknameCheckResponseDto;
import run.bemin.api.user.dto.UserListResponseDto;
import run.bemin.api.user.dto.UserResponseDto;
import run.bemin.api.user.service.UserService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  @GetMapping("/email/exists")
  public ApiResponse<EmailCheckResponseDto> checkEmail(
      @RequestParam @NotBlank(message = "이메일을 입력해주세요.") String email) {
    EmailCheckResponseDto responseDto = userService.checkEmail(email);
    return ApiResponse.from(HttpStatus.OK, "이메일 중복 여부 확인", responseDto);
  }

  @GetMapping("/nickname/exists")
  public ApiResponse<NicknameCheckResponseDto> checkNickname(
      @RequestParam @NotBlank(message = "닉네임을 입력해주세요.") String nickname) {
    NicknameCheckResponseDto responseDto = userService.checkNickname(nickname);
    return ApiResponse.from(HttpStatus.OK, "닉네임 중복 여부 확인", responseDto);
  }

  /**
   * 전체 사용자 조회
   */
  @GetMapping
  @PreAuthorize("hasAnyRole('MASTER')")
  public ResponseEntity<ApiResponse<UserListResponseDto>> getAllUsers(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {

    Page<UserResponseDto> userPage = userService.getAllUsers(page - 1, size);

    UserListResponseDto data = new UserListResponseDto(userPage);

    return ResponseEntity.ok(ApiResponse.from(HttpStatus.OK, "성공", data));
  }

}
