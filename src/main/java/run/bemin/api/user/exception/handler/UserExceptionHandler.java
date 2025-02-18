package run.bemin.api.user.exception.handler;

import static run.bemin.api.general.exception.ErrorCode.USER_LIST_NOT_FOUND;
import static run.bemin.api.general.exception.ErrorCode.USER_PAGE_INDEX_INVALID;
import static run.bemin.api.general.exception.ErrorCode.USER_PAGE_SIZE_INVALID;
import static run.bemin.api.general.exception.ErrorCode.USER_RETRIEVAL_FAILED;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.general.exception.ErrorResponse;
import run.bemin.api.general.exception.ErrorResponse.FieldError;
import run.bemin.api.user.exception.UserDuplicateNicknameException;
import run.bemin.api.user.exception.UserListNotFoundException;
import run.bemin.api.user.exception.UserNoFieldUpdatedException;
import run.bemin.api.user.exception.UserNotFoundException;
import run.bemin.api.user.exception.UserPageIndexInvalidException;
import run.bemin.api.user.exception.UserPageSizeInvalidException;
import run.bemin.api.user.exception.UserRetrievalFailedException;

@RestControllerAdvice
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

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
    return ResponseEntity.status(ErrorCode.USER_NOT_FOUND.getStatus())
        .body(ErrorResponse.of(ErrorCode.USER_NOT_FOUND));
  }

  @ExceptionHandler(UserDuplicateNicknameException.class)
  public ResponseEntity<ErrorResponse> handleUserDuplicateNicknameException(UserDuplicateNicknameException e) {
    return ResponseEntity.status(ErrorCode.USER_DUPLICATE_NICKNAME.getStatus())
        .body(ErrorResponse.of(ErrorCode.USER_DUPLICATE_NICKNAME));
  }

  @ExceptionHandler(UserNoFieldUpdatedException.class)
  public ResponseEntity<ErrorResponse> handleUserNoFieldUpdatedException(UserNoFieldUpdatedException e) {
    return ResponseEntity.status(ErrorCode.USER_NO_FIELD_UPDATED.getStatus())
        .body(ErrorResponse.of(ErrorCode.USER_NO_FIELD_UPDATED));
  }
}
