package run.bemin.api.product.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.general.exception.ErrorResponse;
import run.bemin.api.product.exception.DeletedProductException;
import run.bemin.api.product.exception.ProductNotFoundException;
import run.bemin.api.product.exception.UnauthorizedStoreAccessException;

@Order(1)
@Slf4j
@RestControllerAdvice
public class ProductExceptionHandler {
  @ExceptionHandler(UnauthorizedStoreAccessException.class)
  public ResponseEntity<ErrorResponse> unauthorizedStoreAccessExceptionHandler(UnauthorizedStoreAccessException e) {
    log.error("Unauthorized Store Access", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.STORE_ACCESS_DENIED);
    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ErrorResponse> productNotFoundExceptionHandler(ProductNotFoundException e) {
    log.error("Product Not Found", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.PRODUCT_NOT_FOUND);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DeletedProductException.class)
  public ResponseEntity<ErrorResponse> deletedProductExceptionHandler(DeletedProductException e) {
    log.error("Deleted Product Exception", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.DELETED_PRODUCT);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

}

