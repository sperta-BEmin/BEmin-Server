package run.bemin.api.category.entity;

import static java.lang.Boolean.FALSE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  @Column(name = "is_active", nullable = false)
  @ColumnDefault("true")
  private Boolean isDeleted;

  @Column(name = "created_by", updatable = false)
  private String createdBy;

  @Column(name = "updated_by", nullable = true)
  private String updatedBy;

  @Column(name = "deleted_by", nullable = true)
  private String deletedBy;

  private Category(
      String name,
      String createdBy,
      Boolean isDeleted,
      String updatedBy,
      String deletedBy) {
    this.name = name;
    this.isDeleted = isDeleted;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.deletedBy = deletedBy;
  }

  public static Category create(
      String name,
      String createdBy) {
    return new Category(name, createdBy, FALSE, null, null);
  }

  public void update(String updatedBy, String name, Boolean isDeleted) {
    this.updatedBy = updatedBy;
    this.name = name;
    this.isDeleted = isDeleted != null ? isDeleted : this.isDeleted;
  }

  public void delete(String deletedBy) {
    this.deletedBy = deletedBy;
  }


}
