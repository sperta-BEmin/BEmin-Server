package run.bemin.api.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "p_store")
public class Store {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "store_id", nullable = false, updatable = false, unique = true)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "phone", nullable = true)
  private String phone;

  @Column(name = "minimum_price")
  private Integer minimumPrice;

  @Column(name = "rating")
  private Float rating;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_address_id")
  private StoreAddress storeAddress;

  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = false)
  private final List<StoreCategory> storeCategories = new ArrayList<>();

  @Column(name = "is_deleted")
  private Boolean isDeleted;

  @Column(name = "deleted_at", nullable = true)
  private LocalDateTime deletedAt;

  @Column(name = "user_email", nullable = false)
  private String userEmail;

  @Column(name = "created_by", updatable = false)
  private String createdBy;

  @Column(name = "updated_by", nullable = true)
  private String updatedBy;

  @Column(name = "deleted_by", nullable = true)
  private String deletedBy;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = true)
  private LocalDateTime updatedAt;

  private Store(String name, String phone, Integer minimumPrice, String createdBy, String userEmail) {
    this.name = name;
    this.phone = phone;
    this.minimumPrice = minimumPrice;
    this.isDeleted = false;
    this.createdBy = createdBy;
    this.userEmail = userEmail;
    this.createdAt = LocalDateTime.now();
  }

  public static Store create(String name, String phone, Integer minimumPrice, String createdBy, String userEmail) {
    return new Store(name, phone, minimumPrice, createdBy, userEmail);
  }
}
