package run.bemin.api.store.dto;

public class StoreValidationMessages {

  public static final String STORE_NAME_BLANK = "가게 이름: 필수 정보입니다.";
  public static final String STORE_NAME_INVALID = "가게 이름은 한글, 영문, 숫자, 특수 문자(·, !), 공백만 입력 가능하며, 1~50글자 이내여야 합니다.";

  public static final String STORE_PHONE_BLANK = "가게 전화번호: 필수 정보입니다.";
  public static final String STORE_PHONE_INVALID = "가게 전화번호는 숫자와 '-' 기호만 입력 가능합니다.";

  public static final String STORE_MINIMUM_PRICE_BLANK = "가게 최소 주문 금액: 필수 정보입니다.";
  public static final String STORE_MINIMUM_PRICE_INVALID = "가게 최소 주문 금액은 0원 이상이어야 합니다.";

  public static final String STORE_IS_ACTIVE_BLANK = "가게 활성화 여부: 필수 정보입니다.";


  // 가게 테이블이 하나로 관리되는 경우, 분리가 필요합니다.
  public static final String STORE_ZONE_CODE_BLANK = "가게 국가기초구역번호: 필수 정보입니다.";
  public static final String STORE_ZONE_CODE_INVALID = "국가기초구역번호는 5자리 숫자입니다.";

  public static final String STORE_BCODE_BLANK = "가게 법정동/법정리 코드: 필수 정보입니다.";
  public static final String STORE_BCODE_INVALID = "가게 법정동/법정리 코드는 10자리 숫자입니다.";

  public static final String STORE_JIBUN_ADDRESS_BLANK = "가게 지번 주소: 필수 정보입니다.";
  public static final String STORE_JIBUN_ADDRESS_INVALID = "가게 지번 주소는 한글, 영문, 숫자, 특수 문자(-, ,), 공백만 입력 가능 최대 7~84글자 이내여야 합니다.";

  public static final String STORE_ROAD_ADDRESS_BLANK = "가게 도로 주소: 필수 정보입니다.";
  public static final String STORE_ROAD_ADDRESS_INVALID = "가게 도로 주소는 한글, 영문, 숫자, 특수 문자(-, ,), 공백만 입력 가능 최대 7~84글자 이내여야 합니다.";

  public static final String STORE_ADDRESS_DETAIL_BLACK = "가게 상세 주소: 필수 정보입니다.";
  public static final String STORE_ADDRESS_DETAIL_INVALID = "가게 상세 주소는 한글, 영문, 숫자, 특수 문자(-, ,), 공백만 입력 가능 최대 1~84글자 이내여야 합니다.";

  public static final String USER_EMAIL_BLANK = "이메일: 필수 정보 입니다.";
  public static final String USER_EMAIL_INVALID = "이메일: 잘못된 이메일 주소입니다.";

}
