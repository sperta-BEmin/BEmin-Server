package run.bemin.api.review.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import run.bemin.api.review.entity.Review;

@Getter
@Builder
public class ReviewDeleteResponseDto {
  private String reviewId;
  private LocalDateTime deletedAt;
  private String deletedBy;

  public static ReviewDeleteResponseDto from(Review review) {
    return ReviewDeleteResponseDto.builder()
        .reviewId(String.valueOf(review.getReviewId()))
        .deletedAt(review.getDeletedAt())
        .deletedBy(String.valueOf(review.getDeletedBy()))
        .build();
  }
}
