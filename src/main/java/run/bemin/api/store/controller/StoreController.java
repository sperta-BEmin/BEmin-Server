package run.bemin.api.store.controller;

import static run.bemin.api.store.dto.StoreResponseCode.STORES_FETCHED;
import static run.bemin.api.store.dto.StoreResponseCode.STORE_FETCHED;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.store.dto.response.GetStoreResponseDto;
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
  public ResponseEntity<ApiResponse<List<GetStoreResponseDto>>> getAllStore() {
    List<GetStoreResponseDto> stores = storeService.getAllStore();

    return ResponseEntity
        .status(STORES_FETCHED.getStatus())
        .body(ApiResponse.from(STORES_FETCHED.getStatus(), STORES_FETCHED.getMessage(), stores));
  }


}
