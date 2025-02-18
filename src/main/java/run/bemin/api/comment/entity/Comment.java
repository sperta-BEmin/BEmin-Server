package run.bemin.api.comment.entity;

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
import run.bemin.api.product.entity.Product;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends AuditableEntity {

  @Id
  @UuidGenerator
  @Column(name = "comment_id")
  private UUID commentId;

  @Column(columnDefinition = "TEXT")
  private String content;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "deleted_by")
  private String deletedBy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Builder
  private Comment(
      String content,
      Product product
  ) {
    this.content = content;
    this.product = product;
  }

}
