package run.bemin.api.store.controller;

import static run.bemin.api.store.dto.StoreResponseCode.STORE_FETCHED;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.store.dto.StoreSimpleDto;
import run.bemin.api.store.dto.response.GetStoreResponseDto;
import run.bemin.api.store.entity.Store;
import run.bemin.api.store.service.StoreService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
@RestController
public class StoreController {

  private final StoreService storeService;

  @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANANGER') or hasRole('MASTER') or hasRole('OWNER')")
  @GetMapping("/{storeId}")
  public ResponseEntity<ApiResponse<GetStoreResponseDto>> getStore(
      @PathVariable UUID storeId) {
    GetStoreResponseDto getStoreResponseDto = storeService.getStore(storeId);

    return ResponseEntity
        .status(STORE_FETCHED.getStatus())
        .body(ApiResponse.from(STORE_FETCHED.getStatus(), STORE_FETCHED.getMessage(), getStoreResponseDto));
  }

  @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANANGER') or hasRole('MASTER') or hasRole('OWNER')")
  @GetMapping
  public ResponseEntity<ApiResponse<Page<GetStoreResponseDto>>> getAllStores(
      @RequestParam(value = "storeName", required = false) String storeName,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size
  ) {
    Page<GetStoreResponseDto> adminStores = storeService.getAllStores(
        storeName,
        false,
        page,
        size,
        "createdAt",
        true);
    return ResponseEntity
        .status(STORE_FETCHED.getStatus())
        .body(ApiResponse.from(STORE_FETCHED.getStatus(), STORE_FETCHED.getMessage(), adminStores));
  }

  @PreAuthorize("hasRole('CUSTOMER') or hasRole('MANANGER') or hasRole('MASTER') or hasRole('OWNER')")
  @GetMapping("/search")
  public ResponseEntity<Page<StoreSimpleDto>> searchStores(
      @RequestParam(required = false) String categoryName,
      @RequestParam(required = false) String storeName,
      Pageable pageable) {

    Page<Store> result = storeService.searchStores(categoryName, storeName, pageable);
    Page<StoreSimpleDto> dtoPage = result.map(StoreSimpleDto::fromEntity);
    return ResponseEntity.ok(dtoPage);
  }

}
