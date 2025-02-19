package run.bemin.api.payment.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.payment.dto.CreatePaymentDto;
import run.bemin.api.payment.dto.PaymentCancelDto;
import run.bemin.api.payment.dto.PaymentDto;
import run.bemin.api.payment.service.PaymentService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/payments")
  public ResponseEntity<Map<String, Object>> createPayment(@RequestBody CreatePaymentDto createPaymentDto) {
    PaymentDto payment = paymentService.createPayment(createPaymentDto);

    HashMap<String, Object> response = new HashMap<>();
    response.put("status", 200);
    response.put("message", "성공");
    response.put("data", payment);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/payments/cancel")
  public ResponseEntity<Map<String, Object>> deletePayment(
      HttpServletRequest request,
      @RequestParam UUID paymentId) {

    String authToken = request.getHeader("Authorization");

    PaymentCancelDto payment = paymentService.cancelPayment(authToken, paymentId);

    HashMap<String, Object> response = new HashMap<>();
    response.put("status", 200);
    response.put("message", "성공");
    response.put("data", payment);

    return ResponseEntity.ok(response);
  }
}
