package run.bemin.api.auth.exception;

public class SigninInvalidCredentialsException extends RuntimeException {
  public SigninInvalidCredentialsException(String message) {
    super(message);
  }
}
