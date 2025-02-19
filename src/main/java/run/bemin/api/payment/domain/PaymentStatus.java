package run.bemin.api.payment.domain;

public enum PaymentStatus {
  COMPLETED("결제 완료"),    // 결제 완료
  FAILED("결제 실패"),       // 결제 실패
  CANCELED("결제 취소");      // 결제 취소 : 승인 취소(환불)

  private final String value;

  PaymentStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
