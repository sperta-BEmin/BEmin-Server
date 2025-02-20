package run.bemin.api.payment.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.payment.dto.CreatePaymentDto;
import run.bemin.api.payment.dto.PaymentCancelDto;
import run.bemin.api.payment.dto.PaymentDto;
import run.bemin.api.payment.service.PaymentService;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @GetMapping("/user/payments")
  public ResponseEntity<ApiResponse<List<PaymentDto>>> getUserPayments(HttpServletRequest request) {
    String authToken = request.getHeader("Authorization");

    List<PaymentDto> payments = paymentService.getUserPayments(authToken);

    return ResponseEntity.ok(ApiResponse.from(HttpStatus.OK, "성공", payments));
  }

  @PostMapping("/payments")
  public ResponseEntity<ApiResponse<PaymentDto>> createPayment(@RequestBody CreatePaymentDto createPaymentDto) {
    PaymentDto payment = paymentService.createPayment(createPaymentDto);

    return ResponseEntity.ok(ApiResponse.from(HttpStatus.CREATED, "성공", payment));
  }

  @DeleteMapping("/payments/cancel")
  public ResponseEntity<ApiResponse<PaymentCancelDto>> deletePayment(
      HttpServletRequest request,
      @RequestParam UUID paymentId) {

    String authToken = request.getHeader("Authorization");

    PaymentCancelDto payment = paymentService.cancelPayment(authToken, paymentId);

    return ResponseEntity.ok(ApiResponse.from(HttpStatus.OK, "성공", payment));
  }
}
