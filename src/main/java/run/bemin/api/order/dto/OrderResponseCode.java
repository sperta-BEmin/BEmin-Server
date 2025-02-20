package run.bemin.api.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum OrderResponseCode {

  SUCCESS(HttpStatus.OK, "ORS000", "요청이 성공적으로 처리되었습니다."),
  ORDER_CREATED(HttpStatus.CREATED, "ORS001", "주문에 성공했습니다."),
  ORDER_UPDATED(HttpStatus.OK, "ORS002", "주문이 업데이트 되었습니다."),
  ORDER_CANCELED(HttpStatus.OK, "ORS003", "주문이 취소되었습니다."),
  ORDER_FETCHED(HttpStatus.OK, "ORS004", "주문을 성공적으로 조회했습니다."),
  ORDER_DETAIL_FETCHED(HttpStatus.OK, "ORS005", "주문상세를 성공적으로 조회했습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  public int getStatusCode() { return status.value(); }
}
