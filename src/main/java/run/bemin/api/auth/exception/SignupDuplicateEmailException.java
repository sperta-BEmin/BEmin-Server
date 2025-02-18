package run.bemin.api.auth.exception;

public class SignupDuplicateEmailException extends RuntimeException {
  public SignupDuplicateEmailException(String message) {
    super(message);
  }
}
