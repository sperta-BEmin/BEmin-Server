package run.bemin.api.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import run.bemin.api.review.domain.ReviewRating;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
  private String userEmail;
  private String description;
  private int reviewRating;

  public ReviewResponseDto(String userEmail, String description, ReviewRating reviewRating) {
    this.userEmail = userEmail;
    this.description = description;
    this.reviewRating = reviewRating.getValue();
  }
}
