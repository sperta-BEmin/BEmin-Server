package run.bemin.api.category.controller;

import static run.bemin.api.category.dto.CategoryResponseCode.CATEGORIES_FETCHED;
import static run.bemin.api.category.dto.CategoryResponseCode.CATEGORY_CREATED;
import static run.bemin.api.category.dto.CategoryResponseCode.CATEGORY_DELETED;
import static run.bemin.api.category.dto.CategoryResponseCode.CATEGORY_UPDATED;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import run.bemin.api.security.UserDetailsImpl;

@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/category")
@RestController
public class AdminCategoryController {

  private final CategoryService categoryService;

  // TODO: 카테고리 추가는 MASTER만 가능하다. 지금은 개발 환경을 위해 임시 설정.
  @PreAuthorize("not hasRole('CUSTOMER')")
  @PostMapping
  public ResponseEntity<ApiResponse<CategoryDto>> createCategory(
      @RequestBody CreateCategoryRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    CategoryDto categoryDto = categoryService.createCategory(requestDto, userDetails);

    return ResponseEntity
        .status(CATEGORY_CREATED.getStatus())
        .body(ApiResponse.from(CATEGORY_CREATED.getStatus(), CATEGORY_CREATED.getMessage(), categoryDto));
  }

  @PreAuthorize("not hasRole('CUSTOMER')")
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


  @PreAuthorize("not hasRole('CUSTOMER')")
  @PatchMapping
  public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(
      @RequestBody UpdateCategoryRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    CategoryDto categoryDto = categoryService.updatedCategory(requestDto, userDetails);

    return ResponseEntity
        .status(CATEGORY_UPDATED.getStatus())
        .body(ApiResponse.from(CATEGORY_UPDATED.getStatus(), CATEGORY_UPDATED.getMessage(), categoryDto));
  }

  @PreAuthorize("not hasRole('CUSTOMER')")
  @DeleteMapping
  public ResponseEntity<ApiResponse<CategoryDto>> softDeleteCategory(
      @RequestBody SoftDeleteCategoryRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    CategoryDto categoryDto = categoryService.softDeleteCategory(requestDto, userDetails);

    return ResponseEntity
        .status(CATEGORY_DELETED.getStatus())
        .body(ApiResponse.from(CATEGORY_DELETED.getStatus(), CATEGORY_DELETED.getMessage(), categoryDto));
  }
}
