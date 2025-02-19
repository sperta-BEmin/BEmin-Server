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
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.ISBN;
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

  @Builder
  public Store(
      String name,
      String phone,
      Integer minimumPrice,
      boolean isActive,
      StoreAddress storeAddress,
      String userEmail
  ) {
    this.name = name;
    this.phone = phone;
    this.minimumPrice = minimumPrice;
    this.isActive = isActive;
    this.storeAddress = storeAddress;
    this.userEmail = userEmail;
  }

  public static Store create(
      String name,
      String phone,
      Integer minimumPrice,
      boolean isActive,
      StoreAddress storeAddress,
      String userEmail
  ) {
    return Store.builder()
        .name(name)
        .phone(phone)
        .minimumPrice(minimumPrice)
        .isActive(isActive)
        .storeAddress(storeAddress)
        .userEmail(userEmail)
        .build();
  }

  // 카테고리 연결을 위한 헬퍼 메서드 (첫번째 카테고리는 primary 로 설정)
  public void addCategory(Category category, String createdBy) {
    boolean isPrimary = this.storeCategories.isEmpty();
    StoreCategory storeCategory = StoreCategory.create(this, category, isPrimary, createdBy);
    this.storeCategories.add(storeCategory);
  }

  // 업데이트용 메서드 (setter 대신 엔티티 내부에서 처리)
  public void update(String name, String phone, Integer minimumPrice, Boolean isActive, String userEmail) {
    this.name = name;
    this.phone = phone;
    this.minimumPrice = minimumPrice;
    this.isActive = isActive;
    this.userEmail = userEmail;
  }

  public void updateCategories(List<Category> newCategories, String currentUser) {
    // 새 목록을 빠르게 확인하기 위한 Map 생성 (Category ID -> Category)
    Map<UUID, Category> newCategoryMap = newCategories.stream()
        .collect(Collectors.toMap(Category::getId, category -> category));

    // 1. 기존 연결 중, 새 목록에 없는 항목은 소프트 삭제 처리
    for (StoreCategory storeCategory : storeCategories) {
      if (!newCategoryMap.containsKey(storeCategory.getCategory().getId()) && !storeCategory.getIsDeleted()) {
        storeCategory.softDelete(currentUser);
      }
    }

    // 2. 새 목록에 대해, 기존 연결이 있으면 업데이트(또는 복원), 없으면 추가
    for (int i = 0; i < newCategories.size(); i++) {
      Category newCategory = newCategories.get(i);
      boolean isPrimary = (i == 0); // 첫 번째 카테고리를 primary 로 설정
      Optional<StoreCategory> existingOpt = storeCategories.stream()
          .filter(sc -> sc.getCategory().getId().equals(newCategory.getId()))
          .findFirst();

      if (existingOpt.isPresent()) {
        StoreCategory sc = existingOpt.get();
        if (sc.getIsDeleted()) {
          sc.restore(currentUser, isPrimary);  // 소프트 삭제된 항목이면 복원
        } else {
          sc.update(currentUser, isPrimary); // 이미 활성화된 항목이면 primary 플래그만 업데이트
        }
      } else {
        // 신규 연결 생성 및 추가
        StoreCategory newSC = StoreCategory.create(this, newCategory, isPrimary, currentUser);
        storeCategories.add(newSC);
      }
    }
  }
}
 
