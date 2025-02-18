package run.bemin.api.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.payment.dto.CreatePaymentDto;
import run.bemin.api.payment.dto.PaymentDto;
import run.bemin.api.payment.service.PaymentService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {
  private final PaymentService paymentService;

  // TODO : Order UUID 받을 수 있으면 완성하기
  @PostMapping("/payments")
  public ResponseEntity<PaymentDto> createPayment(@RequestBody CreatePaymentDto createPaymentDto) {
    PaymentDto payment = paymentService.createPayment(createPaymentDto);
    return ResponseEntity.ok(payment);
  }
}
