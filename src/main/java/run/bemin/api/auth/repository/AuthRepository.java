package run.bemin.api.auth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.bemin.api.user.entity.User;

@Repository
public interface AuthRepository extends JpaRepository<User, String> {
  Optional<User> findByUserEmail(String userEmail);

  Optional<User> findByNickname(String nickname);

  boolean existsByUserEmail(String userEmail);

  boolean existsByNickname(String nickname);

}
