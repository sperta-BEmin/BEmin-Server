package run.bemin.api.order.exception;

public class OrderCantCancelled extends RuntimeException {
  public OrderCantCancelled(String message) {
    super(message);
  }
}
