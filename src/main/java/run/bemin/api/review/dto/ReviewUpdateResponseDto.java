package run.bemin.api.review.dto;

import lombok.Builder;
import lombok.Getter;
import run.bemin.api.review.entity.Review;

@Getter
@Builder
public class ReviewUpdateResponseDto {
  private int rate;
  private String description;

  public static ReviewUpdateResponseDto from(Review review) {
    return ReviewUpdateResponseDto.builder()
        .rate(review.getReviewRating().getValue())
        .description(review.getDescription())
        .build();
  }
}
