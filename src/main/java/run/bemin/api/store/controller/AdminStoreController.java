package run.bemin.api.store.controller;

import static run.bemin.api.store.dto.StoreResponseCode.STORE_CREATED;
import static run.bemin.api.store.dto.StoreResponseCode.STORE_FETCHED;
import static run.bemin.api.store.dto.StoreResponseCode.STORE_UPDATED;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.security.UserDetailsImpl;
import run.bemin.api.store.dto.StoreDto;
import run.bemin.api.store.dto.request.CreateStoreRequestDto;
import run.bemin.api.store.dto.request.UpdateAllStoreRequestDto;
import run.bemin.api.store.service.StoreService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/store")
@RestController
public class AdminStoreController {

  private final StoreService storeService;


  @PreAuthorize("not hasRole('CUSTOMER')")
  @GetMapping("/{storeName}")
  public ResponseEntity<ApiResponse<Boolean>> existsStoreName(@PathVariable String storeName) {
    Boolean existsStoreByName = storeService.existsStoreByName(storeName);

    return ResponseEntity
        .status(STORE_FETCHED.getStatus())
        .body(ApiResponse.from(STORE_FETCHED.getStatus(), STORE_FETCHED.getMessage(), existsStoreByName));
  }

  @PreAuthorize("not hasRole('CUSTOMER')")
  @PostMapping
  public ResponseEntity<ApiResponse<StoreDto>> createStore(
      @RequestBody CreateStoreRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    StoreDto storeDto = storeService.createStore(requestDto, userDetails);

    return ResponseEntity
        .status(STORE_CREATED.getStatus())
        .body(ApiResponse.from(STORE_CREATED.getStatus(), STORE_CREATED.getMessage(), storeDto));
  }


  @PutMapping("/{storeId}")
  public ResponseEntity<ApiResponse<StoreDto>> updateAllStore(
      @PathVariable("storeId") UUID storeId,
      @RequestBody @Valid UpdateAllStoreRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    StoreDto storeDto = storeService.updateAllStore(storeId, requestDto, userDetails);

    return ResponseEntity
        .status(STORE_UPDATED.getStatus())
        .body(ApiResponse.from(STORE_UPDATED.getStatus(), STORE_UPDATED.getMessage(), storeDto));
  }

}
