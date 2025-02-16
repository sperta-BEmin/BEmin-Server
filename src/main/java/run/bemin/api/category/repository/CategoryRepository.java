package run.bemin.api.category.repository;


import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import run.bemin.api.category.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, UUID> {

  Boolean existsCategoryByName(String name);
}
