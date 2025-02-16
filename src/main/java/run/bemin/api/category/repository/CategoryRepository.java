package run.bemin.api.category.repository;


import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import run.bemin.api.category.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, UUID> {

  Boolean existsCategoryByName(String name);

  Page<Category> findAll(Pageable pageable);

  Page<Category> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);

  Page<Category> findAllByIsDeletedAndNameContainingIgnoreCase(Boolean isDeleted, String name, Pageable pageable);
}
