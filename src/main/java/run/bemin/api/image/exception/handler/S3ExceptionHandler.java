package run.bemin.api.image.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.general.exception.ErrorResponse;
import run.bemin.api.image.exception.S3InvalidFormatException;
import run.bemin.api.image.exception.S3UploadFailException;

@Order(1)
@Slf4j
@RestControllerAdvice
public class S3ExceptionHandler {
  @ExceptionHandler(S3UploadFailException.class)
  public ResponseEntity<ErrorResponse> S3ResponseStatusExceptionExceptionHandler(S3UploadFailException e) {
    log.error("S3 Upload Fail", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.S3_UPLOAD_FAIL);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(S3InvalidFormatException.class)
  public ResponseEntity<ErrorResponse> S3InvalidFormatExceptionHandler(S3InvalidFormatException e) {
    log.error("S3 Invalid Data Format", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.S3_INVALID_FORMAT);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
