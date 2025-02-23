package run.bemin.api.order.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateOrderRequest {
  private UUID orderId;
  @NotNull
  private Integer statusCode; // 상태코드
  private String riderTel; // 배달기사 전화번호
}
