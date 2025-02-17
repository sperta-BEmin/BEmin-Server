package run.bemin.api.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDetailDTO {
  @NotNull
  private String productId;

  @NotNull
  private String productName;

  @NotNull
  private Integer quantity;

  @NotNull
  private Integer price;

  private Integer totalPrice;
}