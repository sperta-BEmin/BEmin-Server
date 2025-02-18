package run.bemin.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.user.dto.UserResponseDto;
import run.bemin.api.user.entity.User;
import run.bemin.api.user.exception.UserListNotFoundException;
import run.bemin.api.user.exception.UserPageIndexInvalidException;
import run.bemin.api.user.exception.UserPageSizeInvalidException;
import run.bemin.api.user.exception.UserRetrievalFailedException;
import run.bemin.api.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

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
}
