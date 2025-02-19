package run.bemin.api.review.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import run.bemin.api.review.entity.Review;

@Getter
public class ReviewUpdateResponseDto {
  private final UUID reviewId;
  private final int reviewRating;
  private final String description;
  private final LocalDateTime updatedAt;
  private final UUID updatedBy;

  public ReviewUpdateResponseDto(Review review) {
    this.reviewId = review.getReviewId();
    this.reviewRating = review.getReviewRating().getValue();
    this.description = review.getDescription();
    this.updatedAt = review.getUpdatedAt();
    this.updatedBy = review.getUpdateBy();
  }
}
