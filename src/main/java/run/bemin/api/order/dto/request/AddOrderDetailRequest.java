package run.bemin.api.order.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import run.bemin.api.order.dto.ProductDetailDTO;

@Getter
public class AddOrderDetailRequest {

  @NotNull
  private UUID orderId; // 주문 ID

  @NotEmpty
  private List<ProductDetailDTO> products; // 상품 리스트
}
