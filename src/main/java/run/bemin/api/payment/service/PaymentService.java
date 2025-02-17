package run.bemin.api.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.order.entity.Order;
import run.bemin.api.order.repo.OrderRepository;
import run.bemin.api.payment.domain.PaymentStatus;
import run.bemin.api.payment.dto.CreatePaymentDto;
import run.bemin.api.payment.dto.PaymentDto;
import run.bemin.api.payment.entity.Payment;
import run.bemin.api.payment.exception.PaymentException;
import run.bemin.api.payment.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final OrderRepository orderRepository;
  private final PaymentRepository paymentRepository;

  // 결제 생성하기
  @Transactional
  public PaymentDto createPayment(@RequestBody CreatePaymentDto createPaymentDto) {
    // TODO : 주문이 존재하는지 확인하기
    Order order = orderRepository.findById(createPaymentDto.getOrderId())
        .orElseThrow(() -> new PaymentException(ErrorCode.ORDER_NOT_FOUND));

    // 결제 금액이 0보다 작거나 같은 경우 예외 발생
    if (createPaymentDto.getAmount() < 0) {
      throw new PaymentException(ErrorCode.INVALID_INPUT_VALUE);
    }

    // 빌더 패턴으로 payment 생성
    Payment payment = Payment.builder()
        .order(order)
        .payment(createPaymentDto.getPaymentMethod())
        .status(PaymentStatus.COMPLETED)
        .createdBy(createPaymentDto.getCreatedBy())
        .build();

    paymentRepository.save(payment);

    return PaymentDto.from(payment);
  }

}
