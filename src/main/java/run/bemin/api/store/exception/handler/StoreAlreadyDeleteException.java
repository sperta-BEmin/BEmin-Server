package run.bemin.api.store.exception.handler;

public class StoreAlreadyDeleteException extends RuntimeException {
  public StoreAlreadyDeleteException(String message) {
    super(message);
  }
}
