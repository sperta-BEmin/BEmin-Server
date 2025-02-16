package run.bemin.api.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import run.bemin.api.category.entity.Category;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StoreCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id", nullable = false)
  private Store store;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @Column(name = "is_primary", nullable = false)
  private Boolean isPrimary;

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "created_by", nullable = false, updatable = false)
  private String createdBy;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "deleted_by")
  private String deletedBy;

  private StoreCategory(Store store, Category category, Boolean isPrimary, String createdBy) {
    this.store = store;
    this.category = category;
    this.isPrimary = isPrimary != null ? isPrimary : false;
    this.isDeleted = false;
    this.createdBy = createdBy;
    this.createdAt = LocalDateTime.now();
  }

  public static StoreCategory create(Store store, Category category, Boolean isPrimary, String createdBy) {
    return new StoreCategory(store, category, isPrimary, createdBy);
  }

  public void softDelete(String deletedBy) {
    this.isDeleted = true;
    this.deletedBy = deletedBy;
    this.deletedAt = LocalDateTime.now();
  }

  public void update(String updatedBy, Boolean isPrimary) {
    this.updatedBy = updatedBy;
    this.updatedAt = LocalDateTime.now();
    this.isPrimary = isPrimary != null ? isPrimary : this.isPrimary;
  }
}
