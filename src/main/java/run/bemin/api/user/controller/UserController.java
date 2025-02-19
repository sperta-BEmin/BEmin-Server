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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.security.UserDetailsImpl;
import run.bemin.api.user.dto.UserAddressRequestDto;
import run.bemin.api.user.dto.UserAddressResponseDto;
import run.bemin.api.user.dto.UserListResponseDto;
import run.bemin.api.user.dto.UserResponseDto;
import run.bemin.api.user.dto.UserUpdateRequestDto;
import run.bemin.api.user.exception.UserUnauthorizedException;
import run.bemin.api.user.service.UserAddressService;
import run.bemin.api.user.service.UserService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;
  private final UserAddressService userAddressService;

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

  @DeleteMapping("/{userEmail}")
  @PreAuthorize("hasAnyRole('CUSTOMER','OWNER','MANAGER','MASTER')")
  public ResponseEntity<?> deleteUser(@PathVariable String userEmail,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    userService.deleteUser(userEmail, userDetails.getUsername());
    return ResponseEntity.ok(ApiResponse.from(HttpStatus.OK, "성공", userEmail));
  }

  /**
   * 배달 주소 추가
   */
  @PostMapping("/{userEmail}/addresses")
  @PreAuthorize("hasAnyRole('CUSTOMER')")
  public ResponseEntity<ApiResponse<UserAddressResponseDto>> addAddress(
      @PathVariable("userEmail") String userEmail,
      @RequestBody @Valid UserAddressRequestDto addressRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    // 인증된 사용자와 요청한 이메일이 일치하는지 검증
    validateAuthenticatedUser(userEmail, userDetails);

    UserAddressResponseDto addedAddress = userAddressService.addAddress(userEmail, addressRequestDto);

    return ResponseEntity.ok(ApiResponse.from(HttpStatus.OK, "배달 주소 추가 성공", addedAddress));
  }

  private void validateAuthenticatedUser(String userEmail, UserDetailsImpl userDetails) {
    if (!userEmail.equals(userDetails.getUsername())) {
      throw new UserUnauthorizedException(ErrorCode.USER_UNAUTHORIZED.getMessage());
    }
  }

}
