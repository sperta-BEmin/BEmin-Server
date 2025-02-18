package run.bemin.api.product.dto;


import java.util.Objects;

public record ProductSearchDto(
    int price,
    String title,
    String comment,
    String imageUrl,
    boolean is_hidden
) {
  public ProductSearchDto {
    Objects.requireNonNull(price);
    Objects.requireNonNull(title);
    Objects.requireNonNull(is_hidden);
  }
}