package run.bemin.api.review.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import run.bemin.api.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}
