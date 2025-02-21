package run.bemin.api.user.exception;

public class UserAddressNotFoundException extends RuntimeException {
  public UserAddressNotFoundException(String message) {
    super(message);
  }
}
