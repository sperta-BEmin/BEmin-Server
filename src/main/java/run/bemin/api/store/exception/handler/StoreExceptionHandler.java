package run.bemin.api.store.exception.handler;

import static run.bemin.api.general.exception.ErrorCode.CATEGORY_ALREADY_EXISTS;
import static run.bemin.api.general.exception.ErrorCode.CATEGORY_NAME_INVALID;
import static run.bemin.api.general.exception.ErrorCode.CATEGORY_NOT_FOUND;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import run.bemin.api.general.exception.ErrorResponse;
import run.bemin.api.general.exception.ErrorResponse.FieldError;
import run.bemin.api.store.exception.StoreAlreadyExistsByNameException;
import run.bemin.api.store.exception.StoreNameInvalidException;
import run.bemin.api.store.exception.StoreNotFoundException;

@RestControllerAdvice
public class StoreExceptionHandler {

  @ExceptionHandler(StoreNotFoundException.class)
  public ResponseEntity<ErrorResponse> CategoryNotFoundException(StoreNotFoundException e) {
    List<FieldError> errors = FieldError.of("id", e.getMessage(), CATEGORY_NOT_FOUND.getMessage());

    return ResponseEntity.status(CATEGORY_NOT_FOUND.getStatus())
        .body(ErrorResponse.of(CATEGORY_NOT_FOUND, errors));
  }

  @ExceptionHandler(StoreNameInvalidException.class)
  public ResponseEntity<ErrorResponse> CategoryNameInvalidException(StoreNameInvalidException e) {
    return ResponseEntity.status(CATEGORY_NAME_INVALID.getStatus())
        .body(ErrorResponse.of(CATEGORY_NAME_INVALID));
  }

  @ExceptionHandler(StoreAlreadyExistsByNameException.class)
  public ResponseEntity<ErrorResponse> CategoryAlreadyExistsByNameException(StoreAlreadyExistsByNameException e) {
    List<FieldError> errors = FieldError.of("name", e.getMessage(), CATEGORY_ALREADY_EXISTS.getMessage());

    return ResponseEntity.status(CATEGORY_ALREADY_EXISTS.getStatus())
        .body(ErrorResponse.of(CATEGORY_ALREADY_EXISTS, errors));
  }
}
