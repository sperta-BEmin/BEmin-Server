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
import run.bemin.api.general.auditing.AuditableEntity;
import run.bemin.api.store.entity.Store;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends AuditableEntity {

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
  @JoinColumn(name = "store_id", nullable = false)
  private Store store;

  @Column(name = "activated", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
  private boolean activated;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "deleted_by")
  private String deletedBy;

  @Builder
  private Product(
      Store store,
      String imageUrl,
      String comment,
      String title,
      int price) {
    this.store = store;
    this.imageUrl = imageUrl;
    this.comment = comment;
    this.title = title;
    this.price = price;
  }

  public void updatePrice(int price) {
    this.price = price;
  }

  public void updateTitle(String title) {
    this.title = title;
  }

  public void updateImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void updateIsHidden(boolean state) {
    this.isHidden = state;
  }

  public void deleteProduct(String deletedBy, LocalDateTime time) {
    this.activated = false;
    this.deletedBy = deletedBy;
    this.deletedAt = time;
  }
}
