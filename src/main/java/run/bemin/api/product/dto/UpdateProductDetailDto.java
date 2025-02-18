package run.bemin.api.product.dto;

import java.util.Optional;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateProductDetailDto {

  private Optional<Integer> price;
  private Optional<String> title;
  private Optional<String> imageUrl;
  private Optional<Boolean> isHidden;

  @Builder
  public UpdateProductDetailDto(Integer price,
                                String title,
                                String imageUrl,
                                Boolean isHidden) {
    this.price = Optional.ofNullable(price);
    this.title = Optional.ofNullable(title);
    this.imageUrl = Optional.ofNullable(imageUrl);
    this.isHidden = Optional.ofNullable(isHidden);
  }
}
