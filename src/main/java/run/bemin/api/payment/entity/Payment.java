package run.bemin.api.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import run.bemin.api.order.entity.Order;
import run.bemin.api.payment.domain.PaymentMethod;
import run.bemin.api.payment.domain.PaymentStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID paymentId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentMethod payment;

  @Column(nullable = false)
  private Integer amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentStatus status;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  private UUID createdBy;
  private UUID deletedBy;

  // 결제 상태 변경 메서드 : COMPLETED -> FAILED
  public void updateStatus(PaymentStatus status) {
    this.status = status;
  }

  // 결제 취소(환불) 메서드 : COMPLETED -> CANCELED
  public void cancelPayment(UUID deletedBy) {
    this.status = PaymentStatus.CANCELED;
    this.deletedBy = deletedBy;
  }

  @Builder
  public Payment(Order order, PaymentMethod payment, int amount, PaymentStatus status, UUID createdBy) {
    this.order = order;
    this.payment = payment;
    this.amount = amount;
    this.status = status;
    this.createdBy = createdBy;
  }
}
