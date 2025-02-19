package run.bemin.api.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import run.bemin.api.general.auditing.AuditableEntity;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "p_store_address")
public class StoreAddress extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "store_address_id", unique = true, nullable = false)
  private UUID id;

  @Column(name = "zone_code")
  private String zoneCode; // 국가기초구역번호

  @Column(name = "bcode", nullable = false)
  private String bcode; // 법정동/법정리 코드

  @Column(name = "jibun_address", nullable = false)
  private String jibunAddress; // 지번 주소

  @Column(name = "road_address", nullable = false)
  private String roadAddress; // 도로명 주소

  @Column(name = "detail", nullable = false)
  private String detail; // 상세 주소

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  private Store store;

  @Builder
  public StoreAddress(String zoneCode, String bcode, String jibunAddress, String roadAddress, String detail,
                      Store store) {
    this.zoneCode = zoneCode;
    this.bcode = bcode;
    this.jibunAddress = jibunAddress;
    this.roadAddress = roadAddress;
    this.detail = detail;
    this.store = store;
  }

  // Store와의 양방향 연관관계 설정용 메서드
  public void setStore(Store store) {
    this.store = store;
  }

  public void update(String zoneCode, String bcode, String jibunAddress, String roadAddress, String detail) {
    this.zoneCode = zoneCode;
    this.bcode = bcode;
    this.jibunAddress = jibunAddress;
    this.roadAddress = roadAddress;
    this.detail = detail;
  }
}