package run.bemin.api.auth.exception;

public class SignupDuplicateNicknameException extends RuntimeException {
  public SignupDuplicateNicknameException(String message) {
    super(message);
  }
}
