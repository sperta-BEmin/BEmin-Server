package run.bemin.api.auth.exception;

public class SigninUnauthorizedException extends RuntimeException {
  public SigninUnauthorizedException(String message) {
    super(message);
  }
}
