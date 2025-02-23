package run.bemin.api.order.exception;

public class OrderUserNotFoundException extends RuntimeException {
  public OrderUserNotFoundException(String message) {
    super(message);
  }
}
