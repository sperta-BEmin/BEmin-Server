package run.bemin.api.store.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import run.bemin.api.store.entity.Store;

public interface StoreRepository extends CrudRepository<Store, UUID> {
  Boolean existsByName(String name);

  Optional<Store> findByUserEmail(String userEmail);
}
