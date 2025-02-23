package run.bemin.api.order.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteOrderRequest {
  @NotNull
  private UUID orderId; // 주문 ID
}
