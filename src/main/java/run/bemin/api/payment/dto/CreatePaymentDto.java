package run.bemin.api.payment.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import run.bemin.api.payment.domain.PaymentMethod;

@Getter
@NoArgsConstructor
public class CreatePaymentDto {
  private UUID orderId;
  private PaymentMethod paymentMethod;
  private int amount;
  private UUID createdBy;
}
