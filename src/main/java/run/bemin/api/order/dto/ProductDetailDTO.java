package run.bemin.api.order.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDetailDTO {
  @NotNull
  private UUID productId;

  @NotNull
  private String productName;

  @NotNull
  private Integer quantity;

  @NotNull
  private Integer price;
}