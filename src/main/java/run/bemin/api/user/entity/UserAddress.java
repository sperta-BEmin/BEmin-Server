package run.bemin.api.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "p_user_address")
public class UserAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "user_address_id", unique = true, nullable = false)
  private UUID id;

  @Column(name = "bcode", nullable = false)
  private String bcode; // 법정동/법정리 코드

  @Column(name = "jibun_address", nullable = false)
  private String jibunAddress; // 지번 주소

  @Column(name = "road_address", nullable = false)
  private String roadAddress; // 도로명 주소

  @Column(name = "detail", nullable = false)
  private String detail;

  // 대표 주소 여부 플래그 boolean -> Boolean
  @Column(name = "is_representative", nullable = false)
  private Boolean isRepresentative = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_email")
  private User user;

  @Builder
  public UserAddress(String bcode, String jibunAddress, String roadAddress,
                     String detail, Boolean isRepresentative, User user) {
    this.bcode = bcode;
    this.jibunAddress = jibunAddress;
    this.roadAddress = roadAddress;
    this.detail = detail;
    this.isRepresentative = isRepresentative;
    this.user = user;
  }

  public void setRepresentative(Boolean isRepresentative) {
    this.isRepresentative = isRepresentative != null ? isRepresentative : Boolean.FALSE;
  }

}