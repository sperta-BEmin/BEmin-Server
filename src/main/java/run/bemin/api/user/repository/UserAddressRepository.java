package run.bemin.api.user.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import run.bemin.api.user.entity.User;
import run.bemin.api.user.entity.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, UUID> {

  List<UserAddress> findByUserAndIsRepresentativeTrue(User user);

  Page<UserAddress> findByUserAndIsDeleted(User user, Boolean isDeleted, Pageable pageable);

}
