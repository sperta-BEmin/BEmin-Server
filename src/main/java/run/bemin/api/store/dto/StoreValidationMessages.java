package run.bemin.api.store.dto;

public class StoreValidationMessages {

  public static final String STORE_NAME_BLANK = "가게 이름: 필수 정보입니다.";
  public static final String STORE_NAME_INVALID = "가게 이름은 한글, 영문, 숫자, 특수 문자(·, !), 공백만 입력 가능하며, 1~50글자 이내여야 합니다.";

  public static final String STORE_PHONE_BLANK = "가게 전화번호: 필수 정보입니다.";
  public static final String STORE_PHONE_INVALID = "가게 전화번호는 숫자와 '-' 기호만 입력 가능합니다.";

  public static final String STORE_MINIMUM_PRICE_BLANK = "최소 주문 금액: 필수 정보입니다.";
  public static final String STORE_MINIMUM_PRICE_INVALID = "최소 주문 금액은 0원 이상이어야 합니다.";

  public static final String STORE_IS_ACTIVE_BLANK = "가게 활성화 여부: 필수 정보입니다.";

}
