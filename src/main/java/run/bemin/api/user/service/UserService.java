package run.bemin.api.user.service;

import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.user.dto.EmailCheckResponseDto;
import run.bemin.api.user.dto.NicknameCheckResponseDto;
import run.bemin.api.user.dto.UserResponseDto;
import run.bemin.api.user.entity.User;
import run.bemin.api.user.exception.UserException;
import run.bemin.api.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  /**
   * 이메일 중복 체크
   **/
  @Transactional(readOnly = true)
  public EmailCheckResponseDto checkEmail(String email) {
    validateEmail(email);
    boolean isDuplicate = userRepository.existsByUserEmail(email);

    if (isDuplicate) {
      return new EmailCheckResponseDto(true, ErrorCode.DUPLICATE_EMAIL.getMessage(),
          ErrorCode.DUPLICATE_EMAIL.getCode());
    }

    return new EmailCheckResponseDto(false, "사용 가능한 이메일입니다.", null);
  }

  // 이메일 형식 검증
  private void validateEmail(String email) {
    String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    if (!Pattern.matches(emailPattern, email)) {
      throw new UserException(ErrorCode.INVALID_EMAIL_FORMAT);
    }
  }

  /**
   * 닉네임 중복 체크
   **/
  @Transactional(readOnly = true)
  public NicknameCheckResponseDto checkNickname(String nickname) {
    validateNickname(nickname);
    boolean isDuplicate = userRepository.existsByNickname(nickname);

    if (isDuplicate) {
      return new NicknameCheckResponseDto(true, ErrorCode.DUPLICATE_NICKNAME.getMessage(),
          ErrorCode.DUPLICATE_NICKNAME.getCode());
    }

    return new NicknameCheckResponseDto(false, "사용 가능한 닉네임입니다.", null);
  }

  // 닉네임 형식 검증
  private void validateNickname(String nickname) {
    String nicknamePattern = "^[a-z0-9]{4,10}$";
    if (!Pattern.matches(nicknamePattern, nickname)) {
      throw new UserException(ErrorCode.INVALID_NICKNAME_FORMAT);
    }
  }

  /**
   * 전체 회원 조회
   */
  @Transactional(readOnly = true)
  public Page<UserResponseDto> getAllUsers(int page, int size) {
    if (page < 0) {
      throw new UserException(ErrorCode.USER_PAGE_INDEX_INVALID);
    }

    if (size <= 0) {
      throw new UserException(ErrorCode.USER_PAGE_SIZE_INVALID);
    }

    Pageable pageable = PageRequest.of(page, size);
    Page<User> userPage;
    // 데이터베이스 연결 문제 등 실패할 경우
    try {
      userPage = userRepository.findAll(pageable);
    } catch (Exception e) {
      throw new UserException(ErrorCode.USER_RETRIEVAL_FAILED);
    }

    if (userPage.isEmpty()) {
      throw new UserException(ErrorCode.USER_LIST_NOT_FOUND);
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
}
