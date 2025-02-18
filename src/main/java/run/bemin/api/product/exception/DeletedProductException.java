package run.bemin.api.product.exception;

public class DeletedProductException extends RuntimeException{
  public DeletedProductException() {
  }

  public DeletedProductException(String message) {
    super(message);
  }
}
