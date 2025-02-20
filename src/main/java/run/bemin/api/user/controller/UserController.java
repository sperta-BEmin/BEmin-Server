package run.bemin.api.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.security.UserDetailsImpl;
import run.bemin.api.user.dto.UserListResponseDto;
import run.bemin.api.user.dto.UserResponseDto;
import run.bemin.api.user.dto.UserUpdateRequestDto;
import run.bemin.api.user.service.UserService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

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

  /**
   * 내 정보 조회
   */
  @GetMapping("/my-info")
  public ResponseEntity<ApiResponse<UserResponseDto>> getMyUsers(
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    UserResponseDto userResponseDto = userService.getUserByUserEmail(userDetails.getUsername());
    return ResponseEntity.ok(ApiResponse.from(HttpStatus.OK, "성공", userResponseDto));
  }

  /**
   * 내 정보 수정
   */
  @PutMapping("/my-info")
  public ResponseEntity<ApiResponse<UserResponseDto>> updateMyInfo(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody @Valid UserUpdateRequestDto requestDto) {
    UserResponseDto responseDto = userService.updateUser(userDetails.getUsername(), requestDto);
    return ResponseEntity.ok(ApiResponse.from(HttpStatus.OK, "성공", responseDto));
  }

  /**
   * 특정 회원 조회
   */
  @GetMapping("/{userEmail}")
  @PreAuthorize("hasAnyRole('MASTER')")
  public ResponseEntity<ApiResponse<UserResponseDto>> getUserByUserEmail(
      @PathVariable("userEmail") String userEmail) {
    UserResponseDto userResponseDto = userService.getUserByUserEmail(userEmail);
    return ResponseEntity.ok(ApiResponse.from(HttpStatus.OK, "성공", userResponseDto));
  }

  @PutMapping("/{userEmail}")
  @PreAuthorize("hasAnyRole('CUSTOMER','OWNER','MANAGER','MASTER')")
  public ResponseEntity<UserResponseDto> updateUser(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody @Valid UserUpdateRequestDto requestDto) {

    UserResponseDto responseDto = userService.updateUser(userDetails.getUsername(), requestDto);
    return ResponseEntity.ok(ApiResponse.from(HttpStatus.OK, "성공", responseDto).data());
  }

}
