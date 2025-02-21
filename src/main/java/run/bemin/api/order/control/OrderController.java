package run.bemin.api.order.control;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.order.dto.OrderResponseCode;
import run.bemin.api.order.dto.request.CancelOrderRequest;
import run.bemin.api.order.dto.request.CreateOrderRequest;
import run.bemin.api.order.dto.response.PagesResponse;
import run.bemin.api.order.dto.ProductDetailDTO;
import run.bemin.api.order.dto.response.ReadOrderResponse;
import run.bemin.api.order.entity.Order;
import run.bemin.api.order.service.OrderService;
import run.bemin.api.security.UserDetailsImpl;
import run.bemin.api.user.entity.User;
import run.bemin.api.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;
  private final UserService userService;

  /**
   * 주문 생성
   */
  @PostMapping("/order")
  public ResponseEntity<ApiResponse<Order>> createOrder(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody @Valid CreateOrderRequest req
  ) {
    User user = userService.findByUserEmail(userDetails.getUsername());
    Order createdOrder = orderService.createOrder(req, user);

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
      @RequestParam(value = "sortOrder", defaultValue = "desc") String sortOrder,
      @AuthenticationPrincipal UserDetailsImpl user // JWT 공용 메서드에서 값 획득
  ) {
    String userEmail = user.getUsername();
    PagesResponse<ReadOrderResponse> response = orderService.getOrdersByUserEmail(userEmail, page, size, sortOrder);

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
   * 주문 취소
   */
  @PatchMapping("/cancel")
  public ResponseEntity<ApiResponse<Void>> cancelOrder(@RequestBody @Valid CancelOrderRequest req) {
    orderService.cancelOrder(req);
    return ResponseEntity
        .status(OrderResponseCode.ORDER_CANCELED.getStatus())
        .build();
  }
}
