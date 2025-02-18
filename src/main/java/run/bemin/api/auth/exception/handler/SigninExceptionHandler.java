package run.bemin.api.auth.exception.handler;

import static run.bemin.api.general.exception.ErrorCode.FAIL_REQUEST_PARAMETER_VALIDATION;
import static run.bemin.api.general.exception.ErrorCode.SIGNIN_INVALID_CREDENTIALS;
import static run.bemin.api.general.exception.ErrorCode.SIGNIN_UNAUTHORIZED_USER;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import run.bemin.api.auth.exception.SigninInvalidCredentialsException;
import run.bemin.api.auth.exception.SigninUnauthorizedException;
import run.bemin.api.general.exception.ErrorResponse;

@RestControllerAdvice
public class SigninExceptionHandler {

  @ExceptionHandler(SigninUnauthorizedException.class)
  public ResponseEntity<ErrorResponse> SigninUnauthorizedException(SigninUnauthorizedException e) {
    return ResponseEntity.status(SIGNIN_UNAUTHORIZED_USER.getStatus())
        .body(ErrorResponse.of(SIGNIN_UNAUTHORIZED_USER));
  }

  @ExceptionHandler(SigninInvalidCredentialsException.class)
  public ResponseEntity<ErrorResponse> SigninInvalidCredentialsException(SigninInvalidCredentialsException e) {
    return ResponseEntity.status(SIGNIN_INVALID_CREDENTIALS.getStatus())
        .body(ErrorResponse.of(SIGNIN_INVALID_CREDENTIALS));
  }

  /**
   * 로그인 시 입력값 유효성 검사 실패 처리
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleSigninValidation(MethodArgumentNotValidException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

    for (FieldError fieldError : fieldErrors) {
      // 이메일 또는 비밀번호 입력되지 않았을 때
      if ("userEmail".equals(fieldError.getField()) || "password".equals(fieldError.getField())) {
        return ResponseEntity.status(SIGNIN_INVALID_CREDENTIALS.getStatus())
            .body(ErrorResponse.of(SIGNIN_INVALID_CREDENTIALS));
      }
    }

    return ResponseEntity.status(FAIL_REQUEST_PARAMETER_VALIDATION.getStatus())
        .body(ErrorResponse.of(FAIL_REQUEST_PARAMETER_VALIDATION));
  }
}
