package run.bemin.api.auth.exception.handler;

import static run.bemin.api.general.exception.ErrorCode.FAIL_REQUEST_PARAMETER_VALIDATION;
import static run.bemin.api.general.exception.ErrorCode.SIGNUP_DUPLICATE_EMAIL;
import static run.bemin.api.general.exception.ErrorCode.SIGNUP_DUPLICATE_NICKNAME;
import static run.bemin.api.general.exception.ErrorCode.SIGNUP_EMAIL_REQUIRED;
import static run.bemin.api.general.exception.ErrorCode.SIGNUP_INVALID_EMAIL_FORMAT;
import static run.bemin.api.general.exception.ErrorCode.SIGNUP_INVALID_NICKNAME_FORMAT;

import java.util.List;
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

@RestControllerAdvice
public class SignupExceptionHandler {

  @ExceptionHandler(SignupDuplicateEmailException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateEmail(SignupDuplicateEmailException e) {
    return ResponseEntity.status(SIGNUP_DUPLICATE_EMAIL.getStatus())
        .body(ErrorResponse.of(SIGNUP_DUPLICATE_EMAIL));
  }

  @ExceptionHandler(SignupDuplicateNicknameException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateNickname(SignupDuplicateNicknameException e) {
    return ResponseEntity.status(SIGNUP_DUPLICATE_NICKNAME.getStatus())
        .body(ErrorResponse.of(SIGNUP_DUPLICATE_NICKNAME));
  }

  /**
   * 이메일 형식이 올바르지 않을 때 예외 처리
   */
  @ExceptionHandler(SignupInvalidEmailFormatException.class)
  public ResponseEntity<ErrorResponse> handleInvalidEmailFormat(SignupInvalidEmailFormatException e) {
    return ResponseEntity.status(SIGNUP_INVALID_EMAIL_FORMAT.getStatus())
        .body(ErrorResponse.of(SIGNUP_INVALID_EMAIL_FORMAT));
  }

  /**
   * 닉네임 형식이 올바르지 않을 때 예외 처리
   */
  @ExceptionHandler(SignupInvalidNicknameFormatException.class)
  public ResponseEntity<ErrorResponse> handleInvalidNicknameFormat(SignupInvalidNicknameFormatException e) {
    return ResponseEntity.status(SIGNUP_INVALID_NICKNAME_FORMAT.getStatus())
        .body(ErrorResponse.of(SIGNUP_INVALID_NICKNAME_FORMAT));
  }

  /**
   * `@RequestParam`에서 이메일을 입력하지 않았을 때 예외 처리
   */
  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
    return ResponseEntity.status(SIGNUP_EMAIL_REQUIRED.getStatus())
        .body(ErrorResponse.of(SIGNUP_EMAIL_REQUIRED));
  }


  /**
   * 회원가입 유효성 검사 예외 처리
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleSignupValidation(MethodArgumentNotValidException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

    for (FieldError fieldError : fieldErrors) {
      // 이메일이 비어 있을 때 (S003)
      if ("userEmail".equals(fieldError.getField())) {
        return ResponseEntity.status(SIGNUP_INVALID_EMAIL_FORMAT.getStatus())
            .body(ErrorResponse.of(SIGNUP_INVALID_EMAIL_FORMAT));
      }

      // 닉네임 형식 오류 (S005)
      if ("nickname".equals(fieldError.getField())) {
        return ResponseEntity.status(SIGNUP_INVALID_NICKNAME_FORMAT.getStatus())
            .body(ErrorResponse.of(SIGNUP_INVALID_NICKNAME_FORMAT));
      }
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.of(FAIL_REQUEST_PARAMETER_VALIDATION));
  }
}
