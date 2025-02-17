package run.bemin.api.category.controller;

import static run.bemin.api.category.dto.CategoryResponseCode.CATEGORIES_FETCHED;
import static run.bemin.api.category.dto.CategoryResponseCode.CATEGORY_CREATED;
import static run.bemin.api.category.dto.CategoryResponseCode.CATEGORY_DELETED;
import static run.bemin.api.category.dto.CategoryResponseCode.CATEGORY_UPDATED;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.category.dto.CategoryDto;
import run.bemin.api.category.dto.request.CreateCategoryRequestDto;
import run.bemin.api.category.dto.request.SoftDeleteCategoryRequestDto;
import run.bemin.api.category.dto.request.UpdateCategoryRequestDto;
import run.bemin.api.category.service.CategoryService;
import run.bemin.api.general.response.ApiResponse;

@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/category")
@RestController
public class AdminCategoryController { // TODO: 권한 설정 필요하다.

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<ApiResponse<CategoryDto>> createCategory(
      @RequestBody CreateCategoryRequestDto requestDto) {
    CategoryDto categoryDto = categoryService.createCategory(requestDto);
    return ResponseEntity
        .status(CATEGORY_CREATED.getStatus())
        .body(ApiResponse.from(CATEGORY_CREATED.getStatus(), CATEGORY_CREATED.getMessage(), categoryDto));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<CategoryDto>>> getAllCategories(
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "isDeleted", required = false) Boolean isDeleted,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size,
      @RequestParam(value = "sortBy", defaultValue = "createdBy") String sortBy,
      @RequestParam(value = "isAsc", defaultValue = "true") Boolean isAsc
  ) {
    Page<CategoryDto> categories = categoryService.getAllCategories(
        name,
        isDeleted,
        page,
        size,
        sortBy,
        isAsc,
        true);

    return ResponseEntity.ok(
        ApiResponse.from(CATEGORIES_FETCHED.getStatus(), CATEGORIES_FETCHED.getMessage(), categories));
  }

  @PatchMapping
  public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(@RequestBody UpdateCategoryRequestDto requestDto) {
    CategoryDto categoryDto = categoryService.updatedCategory(requestDto);

    return ResponseEntity
        .status(CATEGORY_UPDATED.getStatus())
        .body(ApiResponse.from(CATEGORY_UPDATED.getStatus(), CATEGORY_UPDATED.getMessage(), categoryDto));
  }

  @DeleteMapping
  public ResponseEntity<ApiResponse<CategoryDto>> softDeleteCategory(@RequestBody SoftDeleteCategoryRequestDto requestDto) {
    CategoryDto categoryDto = categoryService.softDeleteCategory(requestDto);

    return ResponseEntity
        .status(CATEGORY_DELETED.getStatus())
        .body(ApiResponse.from(CATEGORY_DELETED.getStatus(), CATEGORY_DELETED.getMessage(), categoryDto));
  }
}
