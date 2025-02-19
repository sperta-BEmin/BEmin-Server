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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import run.bemin.api.category.entity.Category;
import run.bemin.api.general.auditing.AuditableEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "p_store")
public class Store extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "store_id", nullable = false, updatable = false, unique = true)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "phone")
  private String phone;

  @Column(name = "minimum_price")
  private Integer minimumPrice;

  @Column(name = "is_active", nullable = false)
  @ColumnDefault("true")
  private boolean isActive = true;

  @Column(name = "rating")
  private Float rating;

  // Cascade 옵션 추가 (주소와 함께 저장)
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "store_address_id")
  private StoreAddress storeAddress;

  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = false)
  private final List<StoreCategory> storeCategories = new ArrayList<>();

  @Column(name = "is_deleted", nullable = false)
  @ColumnDefault("false")
  private boolean isDeleted = false;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "deleted_by")
  private String deletedBy;

  @Column(name = "user_email", nullable = false)
  private String userEmail;

//  @Column(name = "created_by", updatable = false)
//  private String createdBy;
//
//  @Column(name = "updated_by")
//  private String updatedBy;

//  @Column(name = "created_at", nullable = false, updatable = false)
//  private LocalDateTime createdAt;
//
//  @Column(name = "updated_at")
//  private LocalDateTime updatedAt;

  @Builder
  public Store(
      String name,
      String phone,
      Integer minimumPrice,
      boolean isActive,
      StoreAddress storeAddress,
      String userEmail
//      String createdBy,
//      LocalDateTime createdAt
  ) {
    this.name = name;
    this.phone = phone;
    this.minimumPrice = minimumPrice;
    this.isActive = isActive;
    this.storeAddress = storeAddress;
    this.userEmail = userEmail;
//    this.createdBy = createdBy;
//    this.createdAt = createdAt;
  }

  public static Store create(
      String name,
      String phone,
      Integer minimumPrice,
      boolean isActive,
      StoreAddress storeAddress,
      String userEmail
//      String createdBy,
//      LocalDateTime createdAt
  ) {
    return Store.builder()
        .name(name)
        .phone(phone)
        .minimumPrice(minimumPrice)
        .isActive(isActive)
        .storeAddress(storeAddress)
        .userEmail(userEmail)
//        .createdBy(createdBy)
//        .createdAt(createdAt)
        .build();
  }

  // 카테고리 연결을 위한 헬퍼 메서드 (첫번째 카테고리는 primary 로 설정)
  public void addCategory(Category category, String createdBy) {
    boolean isPrimary = this.storeCategories.isEmpty();
    StoreCategory storeCategory = StoreCategory.create(this, category, isPrimary, createdBy);
    this.storeCategories.add(storeCategory);
  }
}
