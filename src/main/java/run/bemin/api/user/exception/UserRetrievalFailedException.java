package run.bemin.api.user.exception;

public class UserRetrievalFailedException extends RuntimeException {
  public UserRetrievalFailedException(String message) {
    super(message);
  }
}
