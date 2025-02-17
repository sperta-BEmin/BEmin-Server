package run.bemin.api.order.control;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.order.dto.CancelOrderRequest;
import run.bemin.api.order.dto.CreateOrderRequest;
import run.bemin.api.order.dto.PagesResponse;
import run.bemin.api.order.dto.ProductDetailDTO;
import run.bemin.api.order.dto.ReadOrderResponse;
import run.bemin.api.order.dto.UpdateOrderRequest;
import run.bemin.api.order.entity.Order;
import run.bemin.api.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  /**
   * 주문 생성
   */
  @PostMapping("/order")
  public ResponseEntity<Order> createOrder(@RequestBody @Valid CreateOrderRequest req) {
    Order createOrder = orderService.createOrder(req);
    return ResponseEntity.ok(createOrder);
  }

  /**
   * 주문 내역 조회 (페이징 처리)
   */
  @GetMapping("/check")
  public ResponseEntity<PagesResponse<ReadOrderResponse>> getOrdersByUserEmail(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestAttribute("userEmail") String userEmail // JWT 공용 메서드에서 값 획득
  ) {
    PagesResponse<ReadOrderResponse> rep = orderService.getOrdersByUserEmail(userEmail, page, size);
    return ResponseEntity.ok(rep);
  }

  /**
   * 주문 상세 조회
   */
  @GetMapping("/detail")
  public ResponseEntity<List<ProductDetailDTO>> getOrderDetailsByOrderId(@RequestParam UUID orderId) {
    List<ProductDetailDTO> productDetailDTOS = orderService.getOrderDetailsByOrderId(orderId);
    return ResponseEntity.ok(productDetailDTOS);
  }

  /**
   * 주문 상태 및 배달기사 정보 수정
   */
  @PatchMapping("/update")
  public ResponseEntity<Order> updateOrder(@RequestBody @Valid UpdateOrderRequest req) {
    Order updatedOrder = orderService.updateOrder(req);
    return ResponseEntity.ok(updatedOrder);
  }

  /**
   * 주문 취소
   */
  @PatchMapping("/cancel")
  public ResponseEntity<Void> cancelOrder(@RequestBody @Valid CancelOrderRequest req) {
    orderService.cancelOrder(req);
    return ResponseEntity.noContent().build();
  }
}
