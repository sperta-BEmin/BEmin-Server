package run.bemin.api.auth.exception;

public class AuthAccessDeniedException extends RuntimeException {
  public AuthAccessDeniedException(String message) {
    super(message);
  }
}
