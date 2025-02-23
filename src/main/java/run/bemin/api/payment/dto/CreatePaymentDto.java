package run.bemin.api.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import run.bemin.api.payment.domain.PaymentMethod;

@Getter
@NoArgsConstructor
public class CreatePaymentDto {
  private String orderId;
  private PaymentMethod paymentMethod;
  private int amount;
}
