package run.bemin.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.bemin.api.user.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findByNickname(String nickname);
    boolean existsByUserEmail(String userEmail);
}
