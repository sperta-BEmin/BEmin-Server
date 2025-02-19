package run.bemin.api.store.exception;

public class CategoryCountExceededException extends RuntimeException {
  public CategoryCountExceededException(String message) {
    super(message);
  }
}
