package run.bemin.api.payment.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import run.bemin.api.payment.entity.Payment;

@Getter
@Builder
public class PaymentStatusResponseDto {
  private String paymentId;
  private String orderId;
  private String payment;
  private int amount;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String createdBy;
  private String deletedBy;

  public static PaymentStatusResponseDto from(Payment payment) {
    return PaymentStatusResponseDto.builder()
        .paymentId(String.valueOf(payment.getPaymentId()))
        .orderId(String.valueOf(payment.getOrder().getOrderId()))
        .payment(payment.getPayment().name())
        .amount(payment.getAmount())
        .status(payment.getStatus().name())
        .createdAt(payment.getCreatedAt())
        .updatedAt(payment.getUpdatedAt())
        .createdBy(payment.getCreatedBy())
        .deletedBy(payment.getDeletedBy())
        .build();
  }
}
