package run.bemin.api.product.validator;

public enum ProductValidationMessages {
  STORE_NOT_FOUND("존재하지 않은 가게 입니다."),
  UNAUTHORIZED_ACCESS("가게에 대한 권한이 없습니다.");

  private final String message;

  ProductValidationMessages(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
