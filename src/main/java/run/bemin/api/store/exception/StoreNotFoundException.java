package run.bemin.api.store.exception;

public class StoreNotFoundException extends RuntimeException {

  public StoreNotFoundException(String message) {
    super(message);
  }
}
