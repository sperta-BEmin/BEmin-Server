package run.bemin.api.order.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import run.bemin.api.order.dto.ProductDetailDTO;
import run.bemin.api.order.entity.OrderAddress;

@Getter
@Builder
public class CreateOrderRequest {
  @NotNull
  private String storeId; // 상점 ID

  @NotNull
  private Integer orderType; // 주문 타입 코드

  @NotNull
  private String storeName; // 상점 이름

  private OrderAddress address; // 배달주소 객체

  @NotEmpty
  private List<ProductDetailDTO> products;
}
