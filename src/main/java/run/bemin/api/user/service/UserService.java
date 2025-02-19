package run.bemin.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.user.dto.UserResponseDto;
import run.bemin.api.user.dto.UserUpdateRequestDto;
import run.bemin.api.user.entity.User;
import run.bemin.api.user.exception.UserDuplicateNicknameException;
import run.bemin.api.user.exception.UserListNotFoundException;
import run.bemin.api.user.exception.UserNoFieldUpdatedException;
import run.bemin.api.user.exception.UserNotFoundException;
import run.bemin.api.user.exception.UserPageIndexInvalidException;
import run.bemin.api.user.exception.UserPageSizeInvalidException;
import run.bemin.api.user.exception.UserRetrievalFailedException;
import run.bemin.api.user.repository.UserAddressRepository;
import run.bemin.api.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserAddressRepository userAddressRepository;

  /**
   * 전체 회원 조회
   */
  @Transactional(readOnly = true)
  public Page<UserResponseDto> getAllUsers(int page, int size) {
    if (page < 0) {
      throw new UserPageIndexInvalidException(ErrorCode.USER_PAGE_INDEX_INVALID.getMessage());
    }
    if (size <= 0) {
      throw new UserPageSizeInvalidException(ErrorCode.USER_PAGE_SIZE_INVALID.getMessage());
    }

    Pageable pageable = PageRequest.of(page, size);
    Page<User> userPage;

    try {
      userPage = userRepository.findAll(pageable);
    } catch (Exception e) {
      throw new UserRetrievalFailedException(ErrorCode.USER_RETRIEVAL_FAILED.getMessage());
    }

    if (userPage.isEmpty()) {
      throw new UserListNotFoundException(ErrorCode.USER_LIST_NOT_FOUND.getMessage());
    }

    return userPage.map(user -> new UserResponseDto(
        user.getUserEmail(),
        user.getName(),
        user.getNickname(),
        user.getPhone(),
        user.getAddress(),
        user.getRole()
    ));
  }

  /**
   * 특정 회원 조회
   */
  @Transactional(readOnly = true)
  public UserResponseDto getUserByUserEmail(String userEmail) {
    return userRepository.findByUserEmail(userEmail)
        .map(user -> new UserResponseDto(
            user.getUserEmail(),
            user.getName(),
            user.getNickname(),
            user.getPhone(),
            user.getAddress(),
            user.getRole()
        ))
        .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));
  }

  /**
   * 특정 회원 조회 후 유저로 반환
   */
  @Transactional
  public User findByUserEmail(String userEmail) {
    return userRepository.findByUserEmail(userEmail)
        .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));
  }

  @Transactional
  public UserResponseDto updateUser(String userEmail, UserUpdateRequestDto requestDto) {
    return updateUserInfo(userEmail, requestDto);
  }

  private UserResponseDto updateUserInfo(String userEmail, UserUpdateRequestDto requestDto) {
    User user = userRepository.findById(userEmail)
        .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

    boolean isUpdateRequested = false;

    String encodePassword = null;
    if (requestDto.getPassword() != null && !requestDto.getPassword().trim().isEmpty()) {
      encodePassword = passwordEncoder.encode(requestDto.getPassword());
      isUpdateRequested = true;
    }

    String nickname = null;
    if (requestDto.getNickname() != null && !requestDto.getNickname().trim().isEmpty()) {
      nickname = requestDto.getNickname();
      isUpdateRequested = true;

      // 닉네임 중복 검증
      if (!nickname.equals(user.getNickname()) && userRepository.existsByNickname(nickname)) {
        throw new UserDuplicateNicknameException(ErrorCode.USER_DUPLICATE_NICKNAME.getMessage());
      }
    }

    String phone = null;
    if (requestDto.getPhone() != null && !requestDto.getPhone().trim().isEmpty()) {
      phone = requestDto.getPhone();
      isUpdateRequested = true;
    }

    // 아무 필드도 업데이트 요청이 없는 경우 예외 발생
    if (!isUpdateRequested) {
      throw new UserNoFieldUpdatedException(ErrorCode.USER_NO_FIELD_UPDATED.getMessage());
    }

    user.updateUserInfo(encodePassword, nickname, phone);
    return new UserResponseDto(user);
  }

  /**
   * 회원 탈퇴
   */
  @Transactional
  public void deleteUser(String userEmail, String deletedBy) {
    User user = userRepository.findByUserEmail(userEmail)
        .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

    user.delete(deletedBy);
  }


}