package run.bemin.api.payment.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
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

  @PostMapping("/payments")
  public ResponseEntity<Map<String, Object>> createPayment(
      HttpServletRequest request,
      @RequestBody CreatePaymentDto createPaymentDto) {

    String authToken = request.getHeader("Authorization");

    PaymentDto payment = paymentService.createPayment(createPaymentDto);

    HashMap<String, Object> response = new HashMap<>();
    response.put("status", 200);
    response.put("message", "성공");
    response.put("data", payment);

    return ResponseEntity.ok(response);
  }
}
