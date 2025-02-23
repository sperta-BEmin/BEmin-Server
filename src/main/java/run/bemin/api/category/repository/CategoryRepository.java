package run.bemin.api.category.repository;


import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import run.bemin.api.category.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, UUID> {

  Boolean existsCategoryByName(String name);

  Page<Category> findAll(Pageable pageable);

  Page<Category> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);

  Page<Category> findAllByIsDeletedAndNameContainingIgnoreCase(Boolean isDeleted, String name, Pageable pageable);

  List<Category> findAllByIdIn(List<UUID> ids);

  // 삭제 여부 상관 없이 검색
  Page<Category> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

  // 삭제 여부 필터 포함
  Page<Category> findAllByIsDeletedAndNameContainingIgnoreCase(boolean isDeleted, String name, Pageable pageable);

  @Query("select c.name from p_category c where c.name in :names")
  List<String> findNamesIn(@Param("names") List<String> names);
}
