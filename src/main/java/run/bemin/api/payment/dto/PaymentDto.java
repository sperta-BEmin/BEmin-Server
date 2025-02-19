package run.bemin.api.payment.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import run.bemin.api.payment.domain.PaymentMethod;
import run.bemin.api.payment.entity.Payment;

@Getter
@Builder
public class PaymentDto {
  private UUID paymentId;
  private String orderId;
  private PaymentMethod paymentMethod;
  private int amount;
  private String status;
  private LocalDateTime createdAt;
  private String createdBy;

  public static PaymentDto from(Payment payment) {
    return PaymentDto.builder()
        .paymentId(payment.getPaymentId())
        .orderId(String.valueOf(payment.getOrder().getOrderId()))
        .paymentMethod(payment.getPayment())
        .amount(payment.getAmount())
        .status(payment.getStatus().getValue())
        .createdAt(payment.getCreatedAt())
        .createdBy(payment.getCreatedBy())
        .build();
  }
}
