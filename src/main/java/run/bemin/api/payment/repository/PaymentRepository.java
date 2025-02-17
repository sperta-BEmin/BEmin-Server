package run.bemin.api.payment.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import run.bemin.api.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
