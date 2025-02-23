package run.bemin.api.order.control;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.order.dto.OrderResponseCode;
import run.bemin.api.order.dto.request.DeleteOrderRequest;
import run.bemin.api.order.dto.response.ReadOrderResponse;
import run.bemin.api.order.service.OrderMasterService;
import run.bemin.api.order.service.OrderService;
import run.bemin.api.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/master-orders")
@PreAuthorize("hasRole('MANANGER') or hasRole('MASTER')")
public class MasterOrderController {

  private final OrderMasterService orderMasterService;
  private final OrderService orderService;

  /**
   * MASTER or MANAGER 가 주문을 삭제
   * @param req 삭제할 Order 의 UUID 가 담긴 req
   * @param user 현재 접속한 MASTER, MANAGER 의 유저 정보
   * @return delete 된 order 를 ReadOrderResponse 객체로 반환
   */
  @PatchMapping("delete")
  public ResponseEntity<ApiResponse<ReadOrderResponse>> deleteOrder (
      @RequestBody @Valid DeleteOrderRequest req,
      @AuthenticationPrincipal UserDetailsImpl user
  ) {
    orderMasterService.deleteOrder(req, user);
    ReadOrderResponse res = orderService.getOrderById(req.getOrderId());

    return ResponseEntity
        .status(OrderResponseCode.ORDER_DELETED.getStatus())
        .body(ApiResponse.from(
            OrderResponseCode.ORDER_DELETED.getStatus(),
            OrderResponseCode.ORDER_DELETED.getMessage(),
            res
        ));
  }
}
