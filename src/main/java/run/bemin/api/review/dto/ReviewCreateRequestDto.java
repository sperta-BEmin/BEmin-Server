package run.bemin.api.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ReviewCreateRequestDto {
  private UUID orderId;
  private String userEmail;

  @Min(1)
  @Max(5)
  private int reviewRating;

  @Size(min = 20, message = "리뷰 내용은 최소 20자 이상이어야 합니다.")
  private String description;
}
