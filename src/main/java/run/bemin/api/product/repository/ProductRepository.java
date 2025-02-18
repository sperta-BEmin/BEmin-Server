package run.bemin.api.product.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import run.bemin.api.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
