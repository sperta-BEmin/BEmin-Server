package run.bemin.api.user.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import run.bemin.api.user.entity.User;
import run.bemin.api.user.entity.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, UUID> {
  List<UserAddress> findByUser(User user);

  List<UserAddress> findByUserAndIsRepresentativeTrue(User user);

}
