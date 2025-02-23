package run.bemin.api.user.exception.handler;

import static run.bemin.api.general.exception.ErrorCode.USER_LIST_NOT_FOUND;
import static run.bemin.api.general.exception.ErrorCode.USER_PAGE_INDEX_INVALID;
import static run.bemin.api.general.exception.ErrorCode.USER_PAGE_SIZE_INVALID;
import static run.bemin.api.general.exception.ErrorCode.USER_RETRIEVAL_FAILED;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
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

@Order(1)
@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

  @ExceptionHandler(UserPageIndexInvalidException.class)
  public ResponseEntity<ErrorResponse> handleUserPageIndexInvalidException(UserPageIndexInvalidException e) {
    log.error("Invalid page index", e);
    final ErrorResponse response = ErrorResponse.of(USER_PAGE_INDEX_INVALID);
    return new ResponseEntity<>(response, HttpStatus.valueOf(USER_PAGE_INDEX_INVALID.getStatus()));
  }

  @ExceptionHandler(UserPageSizeInvalidException.class)
  public ResponseEntity<ErrorResponse> handleUserPageSizeInvalidException(UserPageSizeInvalidException e) {
    log.error("Invalid page size", e);
    final ErrorResponse response = ErrorResponse.of(USER_PAGE_SIZE_INVALID);
    return new ResponseEntity<>(response, HttpStatus.valueOf(USER_PAGE_SIZE_INVALID.getStatus()));
  }

  @ExceptionHandler(UserRetrievalFailedException.class)
  public ResponseEntity<ErrorResponse> handleUserRetrievalFailedException(UserRetrievalFailedException e) {
    log.error("Failed to retrieve users", e);
    final ErrorResponse response = ErrorResponse.of(USER_RETRIEVAL_FAILED);
    return new ResponseEntity<>(response, HttpStatus.valueOf(USER_RETRIEVAL_FAILED.getStatus()));
  }

  @ExceptionHandler(UserListNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserListNotFoundException(UserListNotFoundException e) {
    log.error("User list not found", e);
    List<FieldError> errors = ErrorResponse.FieldError.of("id", e.getMessage(), USER_LIST_NOT_FOUND.getMessage());
    final ErrorResponse response = ErrorResponse.of(USER_LIST_NOT_FOUND, errors);
    return new ResponseEntity<>(response, HttpStatus.valueOf(USER_LIST_NOT_FOUND.getStatus()));
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
    log.error("User not found", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.USER_NOT_FOUND);
    return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.USER_NOT_FOUND.getStatus()));
  }

  @ExceptionHandler(UserDuplicateNicknameException.class)
  public ResponseEntity<ErrorResponse> handleUserDuplicateNicknameException(UserDuplicateNicknameException e) {
    log.error("Duplicate nickname", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.USER_DUPLICATE_NICKNAME);
    return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.USER_DUPLICATE_NICKNAME.getStatus()));
  }

  @ExceptionHandler(UserNoFieldUpdatedException.class)
  public ResponseEntity<ErrorResponse> handleUserNoFieldUpdatedException(UserNoFieldUpdatedException e) {
    log.error("No field updated", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.USER_NO_FIELD_UPDATED);
    return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.USER_NO_FIELD_UPDATED.getStatus()));
  }
}
