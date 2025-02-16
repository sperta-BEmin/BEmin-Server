package run.bemin.api.category.entity;

import static java.lang.Boolean.FALSE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "p_category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "category_id", nullable = false, updatable = false, unique = true)
  private UUID id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "is_deleted", nullable = false)
  @ColumnDefault("true")
  private Boolean isDeleted;

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

  @Column(name = "deleted_at", nullable = true)
  private LocalDateTime deletedAt;

  private Category(String name, Boolean isDeleted, String createdBy, String updatedBy, String deletedBy,
                   LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
    this.name = name;
    this.isDeleted = isDeleted;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.deletedBy = deletedBy;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }

  public static Category create(String name, String createdBy) {
    return new Category(
        name,
        false,
        createdBy,
        null,
        null,
        LocalDateTime.now(),
        null,
        null);
  }

  public void update(String updatedBy, String name, Boolean isDeleted) {
    this.updatedBy = updatedBy;
    this.name = name;
    this.isDeleted = isDeleted != null ? isDeleted : this.isDeleted;
    this.updatedAt = LocalDateTime.now();
  }

  public void delete(String deletedBy) {
    this.deletedBy = deletedBy;
    this.isDeleted = true;
    this.deletedAt = LocalDateTime.now();
  }

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }


}
