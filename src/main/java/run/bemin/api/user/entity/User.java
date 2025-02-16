package run.bemin.api.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "p_user")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate

public class User {
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


}
