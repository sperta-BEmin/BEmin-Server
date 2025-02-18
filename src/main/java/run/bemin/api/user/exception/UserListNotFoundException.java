package run.bemin.api.user.exception;

public class UserListNotFoundException extends RuntimeException {
  public UserListNotFoundException(String message) {
    super(message);
  }
}
