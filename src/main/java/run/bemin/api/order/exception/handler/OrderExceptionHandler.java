package run.bemin.api.order.exception.handler;

import static run.bemin.api.general.exception.ErrorCode.ORDER_CANT_CANCELLED;
import static run.bemin.api.general.exception.ErrorCode.ORDER_INVALID_STATUS_CODE;
import static run.bemin.api.general.exception.ErrorCode.ORDER_INVALID_TYPE_CODE;
import static run.bemin.api.general.exception.ErrorCode.ORDER_NOT_FOUND2;
import static run.bemin.api.general.exception.ErrorCode.ORDER_NULL_VALUE;
import static run.bemin.api.general.exception.ErrorCode.ORDER_USER_NOT_FOUND;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import run.bemin.api.general.exception.ErrorResponse;
import run.bemin.api.general.exception.ErrorResponse.FieldError;
import run.bemin.api.order.exception.OrderCantCancelled;
import run.bemin.api.order.exception.OrderNotFoundException;
import run.bemin.api.order.exception.OrderNullException;
import run.bemin.api.order.exception.OrderStatusException;
import run.bemin.api.order.exception.OrderTypeException;
import run.bemin.api.order.exception.OrderUserNotFoundException;

@RestControllerAdvice
public class OrderExceptionHandler {

  @ExceptionHandler(OrderNotFoundException.class)
  public ResponseEntity<ErrorResponse> OrderNotFoundException(OrderNotFoundException e) {
    List<FieldError> errors = FieldError.of("id", e.getMessage(), ORDER_NOT_FOUND2.getMessage());

    return ResponseEntity.status(ORDER_NOT_FOUND2.getStatus())
        .body(ErrorResponse.of(ORDER_NOT_FOUND2, errors));
  }

  @ExceptionHandler(OrderStatusException.class)
  public ResponseEntity<ErrorResponse> OrderStatusException(OrderStatusException e) {
    return ResponseEntity.status(ORDER_INVALID_STATUS_CODE.getStatus())
        .body(ErrorResponse.of(ORDER_INVALID_STATUS_CODE));
  }

  @ExceptionHandler(OrderTypeException.class)
  public ResponseEntity<ErrorResponse> OrderTypeException(OrderTypeException e) {
    return ResponseEntity.status(ORDER_INVALID_TYPE_CODE.getStatus())
        .body(ErrorResponse.of(ORDER_INVALID_TYPE_CODE));
  }

  @ExceptionHandler(OrderNullException.class)
  public ResponseEntity<ErrorResponse> OrderNullException(OrderNullException e) {
    return ResponseEntity.status(ORDER_NULL_VALUE.getStatus())
        .body(ErrorResponse.of(ORDER_NULL_VALUE));
  }

  @ExceptionHandler(OrderUserNotFoundException.class)
  public ResponseEntity<ErrorResponse> OrderUserNotFoundException(OrderUserNotFoundException e) {
    return ResponseEntity.status(ORDER_USER_NOT_FOUND.getStatus())
        .body(ErrorResponse.of(ORDER_USER_NOT_FOUND));
  }

  @ExceptionHandler(OrderCantCancelled.class)
  public ResponseEntity<ErrorResponse> OrderCantCancelledException(OrderCantCancelled e) {
    return ResponseEntity.status(ORDER_CANT_CANCELLED.getStatus())
        .body(ErrorResponse.of(ORDER_CANT_CANCELLED));
  }
}
