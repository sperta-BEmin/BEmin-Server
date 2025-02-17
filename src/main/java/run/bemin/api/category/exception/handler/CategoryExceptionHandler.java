package run.bemin.api.category.exception.handler;

import static run.bemin.api.general.exception.ErrorCode.*;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import run.bemin.api.category.exception.CategoryAlreadyExistsByNameException;
import run.bemin.api.category.exception.CategoryNameInvalidException;
import run.bemin.api.category.exception.CategoryNotFoundException;
import run.bemin.api.general.exception.ErrorResponse;
import run.bemin.api.general.exception.ErrorResponse.FieldError;

@RestControllerAdvice
public class CategoryExceptionHandler {

  @ExceptionHandler(CategoryNotFoundException.class)
  public ResponseEntity<ErrorResponse> CategoryNotFoundException(CategoryNotFoundException e) {
    List<FieldError> errors = FieldError.of("id", e.getMessage(), CATEGORY_NOT_FOUND.getMessage());

    return ResponseEntity.status(CATEGORY_NOT_FOUND.getStatus())
        .body(ErrorResponse.of(CATEGORY_NOT_FOUND, errors));
  }

  @ExceptionHandler(CategoryNameInvalidException.class)
  public ResponseEntity<ErrorResponse> CategoryNameInvalidException(CategoryNameInvalidException e) {
    return ResponseEntity.status(CATEGORY_NAME_INVALID.getStatus())
        .body(ErrorResponse.of(CATEGORY_NAME_INVALID));
  }

  @ExceptionHandler(CategoryAlreadyExistsByNameException.class)
  public ResponseEntity<ErrorResponse> CategoryAlreadyExistsByNameException(CategoryAlreadyExistsByNameException e) {
    List<FieldError> errors = FieldError.of("name", e.getMessage(), CATEGORY_ALREADY_EXISTS.getMessage());

    return ResponseEntity.status(CATEGORY_ALREADY_EXISTS.getStatus())
        .body(ErrorResponse.of(CATEGORY_ALREADY_EXISTS, errors));
  }
}
