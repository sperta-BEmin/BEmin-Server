package run.bemin.api.review.dto;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PagedReviewResponseDto {
  private UUID storeId;
  private List<ReviewResponseDto> reviews;
  private PageInfoDto page;

  public PagedReviewResponseDto(UUID storeId, List<ReviewResponseDto> reviews, PageInfoDto page) {
    this.storeId = storeId;
    this.reviews = reviews;
    this.page = page;
  }
}