package run.bemin.api.auth.exception;

public class SignupInvalidNicknameFormatException extends RuntimeException {
  public SignupInvalidNicknameFormatException(String message) {
    super(message);
  }
}
