package run.bemin.api.order.repo;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import run.bemin.api.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {
  /**
   * 특정 사용자(userId)의 주문 내역을 페이징 처리하여 조회
   */
  @Query("SELECT o FROM Order o WHERE o.deleted = false AND o.user.userEmail = :userEmail")
  Page<Order> findAllByUser_UserEmail(@Param("userEmail") String userEmail, Pageable pageable);

}
