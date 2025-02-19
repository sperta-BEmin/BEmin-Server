package run.bemin.api.review.dto;

import lombok.Builder;
import lombok.Getter;
import run.bemin.api.review.entity.Review;

@Getter
@Builder
public class ReviewCreateResponseDto {
  private String orderId;
  private int reviewRating;
  private String description;

  public static ReviewCreateResponseDto from(Review review) {
    return ReviewCreateResponseDto.builder()
        .orderId(String.valueOf(review.getOrder().getOrderId()))
        .reviewRating(review.getReviewRating().getValue())
        .description(review.getDescription())
        .build();
  }
}
