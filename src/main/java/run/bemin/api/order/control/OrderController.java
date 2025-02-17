package run.bemin.api.order.control;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.order.dto.OrderResponseCode;
import run.bemin.api.order.dto.request.CancelOrderRequest;
import run.bemin.api.order.dto.request.CreateOrderRequest;
import run.bemin.api.order.dto.response.PagesResponse;
import run.bemin.api.order.dto.ProductDetailDTO;
import run.bemin.api.order.dto.response.ReadOrderResponse;
import run.bemin.api.order.dto.request.UpdateOrderRequest;
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
  public ResponseEntity<ApiResponse<Order>> createOrder(@RequestBody @Valid CreateOrderRequest req) {
    Order createdOrder = orderService.createOrder(req);
    return ResponseEntity
        .status(OrderResponseCode.ORDER_CREATED.getStatus())
        .body (ApiResponse.from(
            OrderResponseCode.ORDER_CREATED.getStatus(),
            OrderResponseCode.ORDER_CREATED.getMessage(),
            createdOrder
        ));
  }

  /**
   * 주문 내역 조회 (페이징 처리)
   */
  @GetMapping("/check")
  public ResponseEntity<ApiResponse<PagesResponse<ReadOrderResponse>>> getOrdersByUserEmail(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestAttribute("userEmail") String userEmail // JWT 공용 메서드에서 값 획득
  ) {
    PagesResponse<ReadOrderResponse> response = orderService.getOrdersByUserEmail(userEmail, page, size);
    return ResponseEntity
        .status(OrderResponseCode.ORDER_FETCHED.getStatus())
        .body(ApiResponse.from(
            OrderResponseCode.ORDER_FETCHED.getStatus(),
            OrderResponseCode.ORDER_FETCHED.getMessage(),
            response
        ));
  }

  /**
   * 주문 상세 조회
   */
  @GetMapping("/detail")
  public ResponseEntity<ApiResponse<List<ProductDetailDTO>>> getOrderDetailsByOrderId(@RequestParam UUID orderId) {
    List<ProductDetailDTO> productDetails = orderService.getOrderDetailsByOrderId(orderId);
    return ResponseEntity
        .status(OrderResponseCode.ORDER_DETAIL_FETCHED.getStatus())
        .body(ApiResponse.from(
            OrderResponseCode.ORDER_DETAIL_FETCHED.getStatus(),
            OrderResponseCode.ORDER_DETAIL_FETCHED.getMessage(),
            productDetails
        ));
  }

  /**
   * 주문 상태 및 배달기사 정보 수정
   */
  @PatchMapping("/update")
  public ResponseEntity<ApiResponse<Order>> updateOrder(@RequestBody @Valid UpdateOrderRequest req) {
    Order updatedOrder = orderService.updateOrder(req);
    return ResponseEntity
        .status(OrderResponseCode.ORDER_UPDATED.getStatus())
        .body(ApiResponse.from(
            OrderResponseCode.ORDER_UPDATED.getStatus(),
            OrderResponseCode.ORDER_UPDATED.getMessage(),
            updatedOrder
        ));
  }

  /**
   * 주문 취소
   */
  @PatchMapping("/cancel")
  public ResponseEntity<ApiResponse<Order>> cancelOrder(@RequestBody @Valid CancelOrderRequest req) {
    Order cancelledOrder = orderService.cancelOrder(req);
    return ResponseEntity
        .status(OrderResponseCode.ORDER_CANCELED.getStatus())
        .body(ApiResponse.from(
            OrderResponseCode.ORDER_CANCELED.getStatus(),
            OrderResponseCode.ORDER_CANCELED.getMessage(),
            cancelledOrder
        ));
  }
}
