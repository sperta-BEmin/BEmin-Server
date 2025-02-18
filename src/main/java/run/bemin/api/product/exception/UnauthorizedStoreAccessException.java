package run.bemin.api.product.exception;

public class UnauthorizedStoreAccessException extends RuntimeException {
  public UnauthorizedStoreAccessException() {
  }
  public UnauthorizedStoreAccessException(String message) {
    super(message);
  }
}
