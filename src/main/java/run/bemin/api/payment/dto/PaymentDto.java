package run.bemin.api.payment.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import run.bemin.api.payment.domain.PaymentMethod;
import run.bemin.api.payment.domain.PaymentStatus;
import run.bemin.api.payment.entity.Payment;

@Getter
@Builder
public class PaymentDto {
  private UUID paymentId;
  private UUID orderId;
  private PaymentMethod paymentMethod;
  private int amount;
  private PaymentStatus status;
  private LocalDateTime createdAt;

  public static PaymentDto from(Payment payment) {
    return PaymentDto.builder()
        .paymentId(payment.getPaymentId())
        .orderId(payment.getOrder().getOrderId())
        .paymentMethod(payment.getPayment())
        .amount(payment.getAmount())
        .status(payment.getStatus())
        .createdAt(payment.getCreatedAt())
        .build();
  }
}
