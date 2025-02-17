package run.bemin.api.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자는 JPA를 위해 protected
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 빌더 및 생성자 패턴 사용
public class OrderDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "UUID")
  private UUID orderDetailId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Column(nullable = false)
  private String productId;

  @Column(nullable = false)
  private String productName;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false)
  private Integer price;

  @Column(nullable = false)
  private Integer totalPrice; // 총 가격 (단가 * 수량)

  /**
   * OrderDetail 빌더 총 가격 자동 계산 로직
   */
  public static class OrderDetailBuilder {
    public OrderDetail build() {
      OrderDetail orderDetail = new OrderDetail(orderDetailId, order, productId, productName, quantity, price, null);
      orderDetail.calculateTotalPrice();
      return orderDetail;
    }
  }

  /**
   * 총 가격 계산
   */
  public void calculateTotalPrice() {
    if (price != null && quantity != null) {
      this.totalPrice = price * quantity;
    }
  }

  /**
   * 연관 관계 설정을 위한 set
   */
  public void setOrder(Order order) {
    if (this.order != null) {
      throw new IllegalStateException("Order has already been set");
    }
    this.order = order;
  }
}
