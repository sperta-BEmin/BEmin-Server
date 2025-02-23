package run.bemin.api.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PageInfoDto {
  private int size;
  private int number;
  private long totalElements;
  private int totalPages;

  public PageInfoDto(int size, int number, long totalElements, int totalPages) {
    this.size = size;
    this.number = number;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }
}
