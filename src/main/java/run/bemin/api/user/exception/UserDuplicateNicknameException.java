package run.bemin.api.user.exception;

public class UserDuplicateNicknameException extends RuntimeException {
  public UserDuplicateNicknameException(String message) {
    super(message);
  }
}
