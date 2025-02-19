package run.bemin.api.review.dto;

import java.util.UUID;
import lombok.Getter;
import run.bemin.api.review.entity.Review;

@Getter
public class ReviewCreateResponseDto {
  private final UUID orderId;
  private final int reviewRating;
  private String description;
  private UUID createdBy;

  public ReviewCreateResponseDto(Review review) {
    this.orderId = review.getOrder().getOrderId();
    this.reviewRating = review.getReviewRating().getValue();
    this.description = review.getDescription();
    this.createdBy = review.getCreatedBy();
  }
}
