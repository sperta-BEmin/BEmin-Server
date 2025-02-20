package run.bemin.api.category.controller;


import static run.bemin.api.category.dto.CategoryResponseCode.CATEGORIES_FETCHED;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.category.dto.response.GetCategoryResponseDto;
import run.bemin.api.category.service.CategoryService;
import run.bemin.api.general.response.ApiResponse;

@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@RestController
public class CategoryController {

  private final CategoryService categoryService;

  @PreAuthorize("hasRole('CUSTOMER')")
  @GetMapping
  public ResponseEntity<ApiResponse<Page<GetCategoryResponseDto>>> getAllCategories(
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size
  ) {
    Page<GetCategoryResponseDto> categories = categoryService.getAllCategory(
        name,
        false,
        page,
        size,
        "createdAt",
        true);
    return ResponseEntity.ok(
        ApiResponse.from(CATEGORIES_FETCHED.getStatus(), CATEGORIES_FETCHED.getMessage(), categories)
    );
  }


}
