package run.bemin.api.category.controller;


import static run.bemin.api.category.dto.CategoryResponseCode.CATEGORIES_FETCHED;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.category.dto.CategoryDto;
import run.bemin.api.category.service.CategoryService;
import run.bemin.api.general.response.ApiResponse;

@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@RestController
public class CategoryController {

  private final CategoryService categoryService;


  @GetMapping
  public ResponseEntity<ApiResponse<Page<CategoryDto>>> getAllCategories(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size
  ) {
    Page<CategoryDto> categories = categoryService.getAllCategories(
        null,
        false,
        page,
        size,
        "createdAt",
        true,
        false);

    return ResponseEntity.ok(
        ApiResponse.from(CATEGORIES_FETCHED.getStatus(), CATEGORIES_FETCHED.getMessage(), categories));
  }
}
