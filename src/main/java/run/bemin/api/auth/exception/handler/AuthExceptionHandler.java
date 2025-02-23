package run.bemin.api.auth.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import run.bemin.api.auth.exception.AuthAccessDeniedException;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.general.exception.ErrorResponse;

@RestControllerAdvice
public class AuthExceptionHandler {

  @ExceptionHandler({AuthAccessDeniedException.class, AccessDeniedException.class})
  public ResponseEntity<ErrorResponse> handleAccessDeniedExceptions(Exception e) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ErrorResponse.of(ErrorCode.AUTH_ACCESS_DENIED));
  }
}
