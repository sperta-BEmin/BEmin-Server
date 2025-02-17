package run.bemin.api.order.exception;

import java.util.UUID;
import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {
  private final UUID orderId;

  public OrderNotFoundException(UUID orderId) {
    super("Order Not Found -> ID: " + orderId); // 메시지에 ID 포함
    this.orderId = orderId;
  }

}
