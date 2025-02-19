package run.bemin.api.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

//  @Column(nullable = false, updatable = false)
//  @CreatedDate
//  private LocalDateTime createdAt;
//
//  @Column(nullable = false, length = 100, updatable = false)
//  @CreatedBy
//  private String createdBy;
//
//  @Column(nullable = false, updatable = false)
//  @LastModifiedDate
//  private LocalDateTime updatedAt;
//
//  @Column(nullable = false, length =  100)
//  @LastModifiedBy
//  private String updatedBy;
//
//  @Column
//  private LocalDateTime deletedAt;
//
//  @Column(length = 100)
//  private String deletedBy;


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

}
