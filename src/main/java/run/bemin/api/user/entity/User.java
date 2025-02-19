package run.bemin.api.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import run.bemin.api.general.auditing.AuditableEntity;

@Entity
@Table(name = "p_user")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
public class User extends AuditableEntity {
  @Id
  @Column(nullable = false, unique = true)
  private String userEmail;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String nickname;

  @Column(nullable = false)
  private String phone;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private UserRoleEnum role;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "deleted_by")
  private String deletedBy;

  @OneToMany(mappedBy = "user")
  private List<UserAddress> addresses = new ArrayList<>();

  public void updateUserInfo(
      String password,
      String nickname,
      String phone,
      String address
  ) {
    if (password != null && !password.trim().isEmpty()) {
      this.password = password;
    }
    if (nickname != null && !nickname.trim().isEmpty()) {
      this.nickname = nickname;
    }
    if (phone != null && !phone.trim().isEmpty()) {
      this.phone = phone;
    }
    if (address != null && !address.trim().isEmpty()) {
      this.address = address;
    }
  }

  public void delete(String deletedBy) {
    this.deletedAt = LocalDateTime.now();
    this.deletedBy = deletedBy;
  }

}
