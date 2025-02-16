package run.bemin.api.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.category.dto.CategoryDto;
import run.bemin.api.category.dto.request.CreateCategoryRequestDto;
import run.bemin.api.category.entity.Category;
import run.bemin.api.category.exception.CategoryAlreadyExistsByNameException;
import run.bemin.api.category.repository.CategoryRepository;

@RequiredArgsConstructor
@Service
public class CategoryService { // TODO: 회원 등록 유무/권한 체크 기능 구현이 필요하다.

  private final CategoryRepository categoryRepository;

  private void existsCategoryByName(String name) {
    if (categoryRepository.existsCategoryByName(name)) {
      throw new CategoryAlreadyExistsByNameException(name);
    }
  }


  @Transactional
  public CategoryDto createCategory(CreateCategoryRequestDto requestDto) {
    existsCategoryByName(requestDto.name());

    Category category = Category.create(requestDto.name(), requestDto.userEmail());

    categoryRepository.save(category);

    return CategoryDto.fromEntity(category);
  }

}
