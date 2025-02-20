package run.bemin.api.review.dto;

import lombok.Getter;
import run.bemin.api.review.domain.ReviewRating;

@Getter
public class ReviewUpdateRequestDto {
  private int rate;
  private String description;

  public ReviewRating getReviewRating() {
    return ReviewRating.fromValue(rate);
  }
}
