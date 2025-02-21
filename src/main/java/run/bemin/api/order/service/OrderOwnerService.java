package run.bemin.api.order.service;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.order.dto.request.CancelOrderRequest;
import run.bemin.api.order.dto.request.UpdateOrderRequest;
import run.bemin.api.order.dto.response.PagesResponse;
import run.bemin.api.order.dto.response.ReadOrderResponse;
import run.bemin.api.order.entity.Order;
import run.bemin.api.order.entity.OrderDomainService;
import run.bemin.api.order.exception.OrderCantCancelled;
import run.bemin.api.order.exception.OrderNotFoundException;
import run.bemin.api.order.repo.OrderDetailRepository;
import run.bemin.api.order.repo.OrderRepository;
import run.bemin.api.security.UserDetailsImpl;
import run.bemin.api.store.entity.Store;
import run.bemin.api.store.repository.StoreRepository;

@Service
@RequiredArgsConstructor
public class OrderOwnerService {

  private final OrderRepository orderRepository;
  private final OrderDetailRepository orderDetailRepository;
  private final StoreRepository storeRepository;
  private final OrderDomainService orderDomainService = new OrderDomainService();

  @Transactional(readOnly = true)
  public PagesResponse<ReadOrderResponse> getOrdersByStoreIdAndDate(UUID storeId, LocalDate date, int page, int size, String sortOrder) {
    Sort sort = sortOrder.equals("asc") ? Sort.by("createdAt").ascending() : Sort.by("createdAt").descending();
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Order> orders = orderRepository.findByStoreIdAndOrderDate(storeId, date, pageable);

    List<ReadOrderResponse> data = orders.getContent().stream()
        .map(order -> ReadOrderResponse.builder()
            .orderId(order.getOrderId())
            .storeId(order.getStoreId())
            .storeName(order.getStoreName())
            .orderType(order.getOrderType().getCode())
            .orderStatus(order.getOrderStatus().getCode())
            .orderAddress(order.getOrderAddress())
            .cancelled(order.getCancelled())
            .createdAt(order.getCreatedAt())
            .totalPrice(order.getTotalPrice())
            .build())
        .toList();

    return PagesResponse.<ReadOrderResponse>builder()
        .data(data)
        .pageNumber(orders.getNumber())
        .pageSize(orders.getSize())
        .totalPages(orders.getTotalPages())
        .totalElements(orders.getTotalPages())
        .build();

  }

  @Transactional
  public void updateOrder(UpdateOrderRequest req, UserDetailsImpl user) {
    // 1. Order, Store 객체 찾기
    Order order = orderRepository.findById(req.getOrderId())
        .orElseThrow(() -> new OrderNotFoundException(req.getOrderId()));

    Store store = storeRepository.findByOwner_UserEmail(user.getUsername())
        .stream().findFirst().orElseThrow(() -> new OrderCantCancelled("유효하지 않는 OWNER 요청"));

    // 2. 도메인 서비스로 비즈니스 로직 실행
    if (!order.getStoreId().equals(store.getId())) return;

    orderDomainService.updateOrder(order, req);
    orderRepository.save(order);
  }

  @Transactional
  public void cancelOrder(CancelOrderRequest req, UserDetailsImpl user) {
    // 1. Order, Store 객체 찾기
    Order order = orderRepository.findById(req.getOrderId())
        .orElseThrow(() -> new OrderNotFoundException(req.getOrderId()));

    Store store = storeRepository.findByOwner_UserEmail(user.getUsername())
        .stream().findFirst().orElseThrow(() -> new OrderCantCancelled("유효하지 않는 OWNER 요청"));

    // 2. 비즈니스 로직 실행
    if (!order.getStoreId().equals(store.getId())) return;

    orderDomainService.cancelOrder(order);
    orderRepository.save(order);
  }
}
