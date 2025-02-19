package run.bemin.api.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import run.bemin.api.order.entity.Order;
import run.bemin.api.review.domain.ReviewRating;
import run.bemin.api.user.entity.User;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID reviewId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  // TODO : User 와 Mapping
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userEmail", nullable = false)
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ReviewRating review;

  @Lob
  @Column(columnDefinition = "text")
  private String description;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  private UUID createdBy;
  private UUID deletedBy;
}
