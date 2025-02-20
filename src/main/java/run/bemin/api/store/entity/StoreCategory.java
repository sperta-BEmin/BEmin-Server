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
import run.bemin.api.general.auditing.AuditableEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "p_store_category")
public class StoreCategory extends AuditableEntity {

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
  private boolean isPrimary;

  @Column(name = "is_deleted", nullable = false)
  private boolean isDeleted = false;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(name = "deleted_by")
  private String deletedBy;

  private StoreCategory(Store store, Category category, boolean isPrimary) {
    this.store = store;
    this.category = category;
    this.isPrimary = isPrimary;
  }

  public static StoreCategory create(Store store, Category category, boolean isPrimary) {
    return new StoreCategory(store, category, isPrimary);
  }

  public void softDelete(String deletedBy) {
    this.isDeleted = true;
    this.deletedBy = deletedBy;
    this.deletedAt = LocalDateTime.now();
  }

  public void update(boolean isPrimary) {
    this.isPrimary = isPrimary;
  }

  public void restore(String updatedBy, boolean isPrimary) {
    this.isDeleted = false;
    this.deletedBy = null;
    this.deletedAt = null;
    update(isPrimary);
  }
}