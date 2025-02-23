package run.bemin.api.auth.exception.handler;

import static run.bemin.api.general.exception.ErrorCode.FAIL_REQUEST_PARAMETER_VALIDATION;
import static run.bemin.api.general.exception.ErrorCode.SIGNUP_DUPLICATE_EMAIL;
import static run.bemin.api.general.exception.ErrorCode.SIGNUP_DUPLICATE_NICKNAME;
import static run.bemin.api.general.exception.ErrorCode.SIGNUP_EMAIL_NICKNAME_REQUIRED;
import static run.bemin.api.general.exception.ErrorCode.SIGNUP_INVALID_EMAIL_FORMAT;
import static run.bemin.api.general.exception.ErrorCode.SIGNUP_INVALID_NICKNAME_FORMAT;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import run.bemin.api.auth.exception.SignupDuplicateEmailException;
import run.bemin.api.auth.exception.SignupDuplicateNicknameException;
import run.bemin.api.auth.exception.SignupInvalidEmailFormatException;
import run.bemin.api.auth.exception.SignupInvalidNicknameFormatException;
import run.bemin.api.general.exception.ErrorResponse;

@Order(1)
@Slf4j
@RestControllerAdvice
public class SignupExceptionHandler {

  @ExceptionHandler(SignupDuplicateEmailException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateEmail(SignupDuplicateEmailException e) {
    log.error("Duplicate email during signup", e);
    final ErrorResponse response = ErrorResponse.of(SIGNUP_DUPLICATE_EMAIL);
    return new ResponseEntity<>(response, HttpStatus.valueOf(SIGNUP_DUPLICATE_EMAIL.getStatus()));
  }

  @ExceptionHandler(SignupDuplicateNicknameException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateNickname(SignupDuplicateNicknameException e) {
    log.error("Duplicate nickname during signup", e);
    final ErrorResponse response = ErrorResponse.of(SIGNUP_DUPLICATE_NICKNAME);
    return new ResponseEntity<>(response, HttpStatus.valueOf(SIGNUP_DUPLICATE_NICKNAME.getStatus()));
  }

  @ExceptionHandler(SignupInvalidEmailFormatException.class)
  public ResponseEntity<ErrorResponse> handleInvalidEmailFormat(SignupInvalidEmailFormatException e) {
    log.error("Invalid email format during signup", e);
    final ErrorResponse response = ErrorResponse.of(SIGNUP_INVALID_EMAIL_FORMAT);
    return new ResponseEntity<>(response, HttpStatus.valueOf(SIGNUP_INVALID_EMAIL_FORMAT.getStatus()));
  }

  @ExceptionHandler(SignupInvalidNicknameFormatException.class)
  public ResponseEntity<ErrorResponse> handleInvalidNicknameFormat(SignupInvalidNicknameFormatException e) {
    log.error("Invalid nickname format during signup", e);
    final ErrorResponse response = ErrorResponse.of(SIGNUP_INVALID_NICKNAME_FORMAT);
    return new ResponseEntity<>(response, HttpStatus.valueOf(SIGNUP_INVALID_NICKNAME_FORMAT.getStatus()));
  }

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
    log.error("Email and Nickname are required for signup", e);
    final ErrorResponse response = ErrorResponse.of(SIGNUP_EMAIL_NICKNAME_REQUIRED);
    return new ResponseEntity<>(response, HttpStatus.valueOf(SIGNUP_EMAIL_NICKNAME_REQUIRED.getStatus()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleSignupValidation(MethodArgumentNotValidException e) {
    log.error("Signup validation error", e);
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

    for (FieldError fieldError : fieldErrors) {
      if ("userEmail".equals(fieldError.getField())) {
        final ErrorResponse response = ErrorResponse.of(SIGNUP_INVALID_EMAIL_FORMAT);
        return new ResponseEntity<>(response, HttpStatus.valueOf(SIGNUP_INVALID_EMAIL_FORMAT.getStatus()));
      }

      if ("nickname".equals(fieldError.getField())) {
        final ErrorResponse response = ErrorResponse.of(SIGNUP_INVALID_NICKNAME_FORMAT);
        return new ResponseEntity<>(response, HttpStatus.valueOf(SIGNUP_INVALID_NICKNAME_FORMAT.getStatus()));
      }
    }

    final ErrorResponse response = ErrorResponse.of(FAIL_REQUEST_PARAMETER_VALIDATION);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
