package run.bemin.api.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum StoreResponseCode {

  SUCCESS(HttpStatus.OK, "STS000", "요청이 성공적으로 처리되었습니다."),
  STORE_CREATED(HttpStatus.CREATED, "STS001", "가게 등록에 성공했습니다."),
  STORE_UPDATED(HttpStatus.OK, "STS002", "가게 정보가 수정되었습니다."),
  STORE_DELETED(HttpStatus.OK, "STS003", "가게가 소프트 삭제되었습니다."),
  STORE_FETCHED(HttpStatus.OK, "STS004", "가게 정보를 성공적으로 조회했습니다."),
  STORES_FETCHED(HttpStatus.OK, "STS005", "전체 가게 목록을 성공적으로 조회했습니다."),
  STORE_DELETED_HARD(HttpStatus.OK, "STS006", "가게가 하드 삭제되었습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  public int getStatusCode() {
    return status.value();
  }
}
