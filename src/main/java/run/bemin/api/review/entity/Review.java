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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import run.bemin.api.general.auditing.AuditableEntity;
import run.bemin.api.order.entity.Order;
import run.bemin.api.review.domain.ReviewRating;
import run.bemin.api.user.entity.User;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends AuditableEntity {
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
  private ReviewRating reviewRating;

  @Lob
  @Column(columnDefinition = "text")
  private String description;

  private UUID deletedBy;
  private LocalDateTime deletedAt;

  // 리뷰 수정하기
  public void updateReview(ReviewRating reviewRating, String description) {
    this.reviewRating = reviewRating;
    this.description = description;
  }

  @Builder
  public Review(Order order, User user, ReviewRating reviewRating, String description) {
    this.order = order;
    this.user = user;
    this.reviewRating = reviewRating;
    this.description = description;
  }
}
