package run.bemin.api.review.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.bemin.api.review.domain.ReviewStatus;
import run.bemin.api.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
  // ✅ 특정 가게(`Store`)의 활성화된 리뷰 조회
  List<Review> findByStore_IdAndStatus(UUID storeId, ReviewStatus status);

  @Query("SELECT COALESCE(AVG(CAST(r.reviewRating AS integer)), 0.0) FROM Review r WHERE r.store.id = :storeId AND r.status = 'ACTIVE'")
  double findAverageRatingByStore(UUID storeId);
}
