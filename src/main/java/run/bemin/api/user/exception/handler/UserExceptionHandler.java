package run.bemin.api.user.exception.handler;

import static run.bemin.api.general.exception.ErrorCode.USER_LIST_NOT_FOUND;
import static run.bemin.api.general.exception.ErrorCode.USER_PAGE_INDEX_INVALID;
import static run.bemin.api.general.exception.ErrorCode.USER_PAGE_SIZE_INVALID;
import static run.bemin.api.general.exception.ErrorCode.USER_RETRIEVAL_FAILED;

import java.util.List;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import run.bemin.api.general.exception.ErrorResponse;
import run.bemin.api.general.exception.ErrorResponse.FieldError;
import run.bemin.api.user.exception.UserListNotFoundException;
import run.bemin.api.user.exception.UserPageIndexInvalidException;
import run.bemin.api.user.exception.UserPageSizeInvalidException;
import run.bemin.api.user.exception.UserRetrievalFailedException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE) // 설정한 예외 처리가 되지 않아 전역 핸들러보다 우선순위가 높도록 설정했습니다..
public class UserExceptionHandler {
  @ExceptionHandler(UserPageIndexInvalidException.class)
  public ResponseEntity<ErrorResponse> handleUserPageIndexInvalidException(UserPageIndexInvalidException e) {
    return ResponseEntity.status(USER_PAGE_INDEX_INVALID.getStatus())
        .body(ErrorResponse.of(USER_PAGE_INDEX_INVALID));
  }

  @ExceptionHandler(UserPageSizeInvalidException.class)
  public ResponseEntity<ErrorResponse> handleUserPageSizeInvalidException(UserPageSizeInvalidException e) {
    return ResponseEntity.status(USER_PAGE_SIZE_INVALID.getStatus())
        .body(ErrorResponse.of(USER_PAGE_SIZE_INVALID));
  }

  @ExceptionHandler(UserRetrievalFailedException.class)
  public ResponseEntity<ErrorResponse> handleUserRetrievalFailedException(UserRetrievalFailedException e) {
    return ResponseEntity.status(USER_RETRIEVAL_FAILED.getStatus())
        .body(ErrorResponse.of(USER_RETRIEVAL_FAILED));
  }

  @ExceptionHandler(UserListNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserListNotFoundException(UserListNotFoundException e) {
    List<FieldError> errors = ErrorResponse.FieldError.of("id", e.getMessage(), USER_LIST_NOT_FOUND.getMessage());
    return ResponseEntity.status(USER_LIST_NOT_FOUND.getStatus())
        .body(ErrorResponse.of(USER_LIST_NOT_FOUND, errors));
  }
}
