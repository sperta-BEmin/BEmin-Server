package run.bemin.api.user.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import run.bemin.api.store.entity.Store;

@Entity
@Table(name = "p_user")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
public class User extends AuditableEntity {
  @Id
  @Column(name = "user_email", nullable = false, unique = true)
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
  @Enumerated(value = EnumType.STRING)
  private UserRoleEnum role;

  @Builder.Default
  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = false;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "deleted_by")
  private String deletedBy;

  // 주소 함께 저장 + 주소 함께 삭제
  // 현재클래스To매핑클래스
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
  private final List<UserAddress> userAddressList = new ArrayList<>();

  // 대표 주소를 설정하는 메서드 (대표 주소 변경 시 사용)
  // 대표 주소를 외래 키로 참조 (p_user 테이블에 representative_address_id 컬럼 생성)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "representative_address_id")
  private UserAddress representativeAddress;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = false)
  private final List<Store> stores = new ArrayList<>();

  public void updateUserInfo(
      String password,
      String nickname,
      String phone
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
  }

  public void softDelete(String deletedBy) {
    this.isDeleted = true;
    this.deletedAt = LocalDateTime.now();
    this.deletedBy = deletedBy;

    // 연결된 모든 UserAddress도 soft delete 처리
    if (userAddressList != null) {
      for (UserAddress address : userAddressList) {
        address.softDelete(deletedBy);
      }
    }
  }

  public String getAddress() {
    return userAddressList.stream()
        .filter(UserAddress::getIsRepresentative)
        .findFirst()
        .map(UserAddress::getRoadAddress)
        .orElse(null);
  }

  // 대표 주소 변경을 위한 도메인 메서드 추가
  public void setRepresentativeAddress(UserAddress representativeAddress) {
    this.representativeAddress = representativeAddress;
  }

}
