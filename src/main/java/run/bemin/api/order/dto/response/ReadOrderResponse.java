package run.bemin.api.order.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import run.bemin.api.order.entity.OrderAddress;

@Getter
@Builder
public class ReadOrderResponse {
  private UUID orderId;
  private String storeId;
  private String storeName;
  private Integer orderType;
  private Integer orderStatus;
  private OrderAddress orderAddress;
  private String riderTel;
}
