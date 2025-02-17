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
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "p_store_address")
public class StoreAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "store_address_id", unique = true, nullable = false)
  private UUID id;

  @Column(name = "bcode", nullable = false)
  private String bcode;

  @Column(name = "detail", nullable = false)
  private String detail;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  private Store store;
}
