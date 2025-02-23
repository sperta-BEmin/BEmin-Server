package run.bemin.api.order.dto;

import lombok.Builder;
import lombok.Getter;
import run.bemin.api.order.entity.OrderAddress;

@Getter
@Builder
public class CreateOrderRequest {
  private String storeId; // 상점 ID
  private Integer orderType; // 주문 타입 코드
  private String storeName; // 상점 이름
  private OrderAddress address; // 배달주소 객체
}
