package run.bemin.api.review.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import run.bemin.api.review.domain.ReviewStatus;
import run.bemin.api.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
  List<Review> findByStore_IdAndStatus(UUID storeId, ReviewStatus status);

  @Query("SELECT COALESCE(AVG(r.reviewRating), 0.0) " +
      "FROM Review r " +
      "WHERE r.store.id = :storeId AND r.status = 'ACTIVE'")
  double findAverageRatingByStore(@Param("storeId") UUID storeId);
}
