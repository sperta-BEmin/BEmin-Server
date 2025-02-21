package run.bemin.api.review.dto;

import lombok.Getter;

@Getter
public class ReviewCreateRequestDto {
  private String orderId;
  private String storeId;
  private int reviewRating;
  private String description;
}
