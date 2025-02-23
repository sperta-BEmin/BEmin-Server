package run.bemin.api.order.repo;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import run.bemin.api.order.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
}
