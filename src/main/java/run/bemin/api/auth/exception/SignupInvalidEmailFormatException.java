package run.bemin.api.auth.exception;

public class SignupInvalidEmailFormatException extends RuntimeException {
  public SignupInvalidEmailFormatException(String message) {
    super(message);
  }
}
