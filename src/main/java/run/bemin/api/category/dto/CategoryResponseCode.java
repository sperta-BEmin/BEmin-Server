package run.bemin.api.category.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CategoryResponseCode {

  // 카테고리 응답 코드 Category Success
  SUCCESS(HttpStatus.OK, "CCS000", "요청이 성공적으로 처리되었습니다."),
  CATEGORY_CREATED(HttpStatus.CREATED, "CCS001", "카테고리 등록에 성공했습니다."),
  CATEGORY_UPDATED(HttpStatus.OK, "CCS002", "카테고리 정보가 수정되었습니다."),
  CATEGORY_DELETED(HttpStatus.OK, "CCS003", "카테고리가 삭제되었습니다."),
  CATEGORY_FETCHED(HttpStatus.OK, "CCS004", "카테고리 정보를 성공적으로 조회했습니다."),
  CATEGORIES_FETCHED(HttpStatus.OK, "CCS005", "전체 카테고리 목록을 성공적으로 조회했습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  public int getStatusCode() {
    return status.value();
  }
}
