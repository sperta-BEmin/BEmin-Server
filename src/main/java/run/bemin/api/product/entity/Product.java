package run.bemin.api.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import run.bemin.api.user.entity.User;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

  @Id
  @UuidGenerator
  @Column(name = "product_id")
  private UUID productId;

  @Column(name = "price", nullable = false)
  private int price;

  @Column(name = "title", nullable = false)
  private String title;

  // TEXT 타입을 위한 설정
  //@Lob
  @Column(columnDefinition = "TEXT")
  private String comment;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "is_hidden", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean isHidden;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "activated", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean activated;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "deleted_by")
  private String deletedBy;

  @Builder
  private Product(
      User user,
      String imageUrl,
      String comment,
      String title,
      int price) {
    this.user = user;
    this.imageUrl = imageUrl;
    this.comment = comment;
    this.title = title;
    this.price = price;
  }
}
