package run.bemin.api.product.dto;

import java.util.Objects;

public record ProductRequestDto(
    int price,
    String title,
    String comment,
    String imageUrl
) {
  public ProductRequestDto {
    Objects.requireNonNull(price);
    Objects.requireNonNull(title);
  }
}
