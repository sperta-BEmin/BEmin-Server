package run.bemin.api.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.bemin.api.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUserEmail(String userEmail);

  boolean existsByUserEmail(String userEmail);

  boolean existsByNickname(String nickname);

}
