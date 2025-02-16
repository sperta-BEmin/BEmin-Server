package run.bemin.api.category.dto;

public class CategoryValidationMessages {

  public static final String CATEGORY_NAME_BLANK = "카테고리 이름: 필수 정보입니다.";
  public static final String CATEGORY_NAME_INVALID = "카테고리 이름은 한글, 숫자, 특수 문자(·, !), 공백만 입력 가능하며, 1~16글자 이내여야 합니다.";
  public static final String CATEGORY_IS_ACTIVE_BLANK = "카테고리 활성화 여부: 필수 정보 입니다.";

  // TODO: 회원 Validation 메시지에서 가져와야 할 필요가 있다.
  public static final String USER_EMAIL_BLANK = "이메일: 필수 정보 입니다.";
  public static final String USER_EMAIL_INVALID = "이메일: 잘못된 이메일 주소입니다.";

}
