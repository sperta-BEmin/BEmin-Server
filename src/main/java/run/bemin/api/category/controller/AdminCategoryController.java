package run.bemin.api.category.controller;

import static java.lang.Boolean.*;
import static java.lang.Boolean.FALSE;
import static run.bemin.api.category.dto.CategoryResponseCode.CATEGORIES_FETCHED;
import static run.bemin.api.category.dto.CategoryResponseCode.CATEGORY_CREATED;

import java.net.http.HttpResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.category.dto.CategoryDto;
import run.bemin.api.category.dto.CategoryResponseCode;
import run.bemin.api.category.dto.request.CreateCategoryRequestDto;
import run.bemin.api.category.service.CategoryService;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.security.UserDetailsImpl;

@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/category")
@RestController
public class AdminCategoryController { // TODO: 권한 설정 필요하다.

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<ApiResponse<CategoryDto>> createCategory(
      @RequestBody CreateCategoryRequestDto requestDto) {

    return ResponseEntity
        .status(CATEGORY_CREATED.getStatus())
        .body(ApiResponse.from(CATEGORY_CREATED.getStatus(), CATEGORY_CREATED.getMessage(),
            categoryService.createCategory(requestDto)));
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

}
