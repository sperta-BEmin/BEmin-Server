package run.bemin.api.payment.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import run.bemin.api.auth.jwt.JwtUtil;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.order.entity.Order;
import run.bemin.api.order.repo.OrderRepository;
import run.bemin.api.payment.domain.PaymentStatus;
import run.bemin.api.payment.dto.CreatePaymentDto;
import run.bemin.api.payment.dto.PaymentDto;
import run.bemin.api.payment.entity.Payment;
import run.bemin.api.payment.exception.PaymentException;
import run.bemin.api.payment.repository.PaymentRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

  private final OrderRepository orderRepository;
  private final PaymentRepository paymentRepository;
  private final JwtUtil jwtUtil;

  // 토큰 추출하기
  public String extractToken(String token) {
    if (token == null || !token.startsWith("Bearer ")) {
      throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
    }

    String extractToken = token.substring(7);
    return jwtUtil.getUserEmailFromToken(extractToken);
  }

  /*
   * 메서드명 : createPayment
   * 목적 : 결제 생성하기
   * */
  @Transactional
  public PaymentDto createPayment(@RequestBody CreatePaymentDto createPaymentDto) {
    // 주문이 존재하는지 확인하기
    Order order = orderRepository.findById(UUID.fromString(createPaymentDto.getOrderId()))
        .orElseThrow(() -> new PaymentException(ErrorCode.ORDER_NOT_FOUND));

    // 결제 금액이 0보다 작거나 같은 경우 예외 발생
    if (createPaymentDto.getAmount() < 0) {
      throw new PaymentException(ErrorCode.INVALID_INPUT_VALUE);
    }

    Payment payment = Payment.builder()
        .order(order)
        .payment(createPaymentDto.getPaymentMethod())
        .status(PaymentStatus.COMPLETED)
        .amount(createPaymentDto.getAmount())
        .build();

    paymentRepository.save(payment);

    return PaymentDto.from(payment);
  }

  /*
   * 메서드명 :
   * */

}
