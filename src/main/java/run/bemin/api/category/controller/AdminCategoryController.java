package run.bemin.api.category.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.category.dto.CategoryDto;
import run.bemin.api.category.dto.request.CreateCategoryRequestDto;
import run.bemin.api.category.service.CategoryService;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.security.UserDetailsImpl;

@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@RestController
public class AdminCategoryController { // TODO: 권한 설정 필요하다.

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<ApiResponse<CategoryDto>> createCategory(
      @RequestBody CreateCategoryRequestDto requestDto) {

    return ResponseEntity
        .status(HttpStatus.CREATED) // 201 Created 상태 코드
        .body(ApiResponse.from(HttpStatus.CREATED, "카테고리 생성 성공",
            categoryService.createCategory(requestDto)));
  }


}
