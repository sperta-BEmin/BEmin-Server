package run.bemin.api.store.controller;

import static run.bemin.api.store.dto.StoreResponseCode.STORE_CREATED;
import static run.bemin.api.store.dto.StoreResponseCode.STORE_DELETED;
import static run.bemin.api.store.dto.StoreResponseCode.STORE_FETCHED;
import static run.bemin.api.store.dto.StoreResponseCode.STORE_UPDATED;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.category.dto.request.UpdateMinimumPriceRequestDto;
import run.bemin.api.category.dto.request.UpdateStoreNameRequestDto;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.security.UserDetailsImpl;
import run.bemin.api.store.dto.StoreDto;
import run.bemin.api.store.dto.request.CreateStoreRequestDto;
import run.bemin.api.store.dto.request.UpdateAddressInStoreRequestDto;
import run.bemin.api.store.dto.request.UpdateAllStoreRequestDto;
import run.bemin.api.store.dto.request.UpdateCategoriesInStoreRequestDto;
import run.bemin.api.store.dto.request.UpdateStoreActivationRequestDto;
import run.bemin.api.store.dto.request.UpdateStoreOwnerRequestDto;
import run.bemin.api.store.dto.request.UpdateStorePhoneRequestDto;
import run.bemin.api.store.dto.request.UpdateStoresActivationStatusRequestDto;
import run.bemin.api.store.service.StoreService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/stores")
@RestController
public class AdminStoreController {

  private final StoreService storeService;

  // 메일로 단건 가게 목록을 조회하기
  @PreAuthorize("hasRole('MASTER')")
  @GetMapping("/by-user")
  public ResponseEntity<ApiResponse<StoreDto>> getStoresByUserEmail(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    StoreDto storeDto = storeService.getStoreByUserEmail(userDetails.getUsername());

    return ResponseEntity
        .status(STORE_FETCHED.getStatus())
        .body(ApiResponse.from(STORE_FETCHED.getStatus(), STORE_FETCHED.getMessage(), storeDto));
  }

  // 새로운 가게 등록하기
  @PreAuthorize("hasRole('MASTER')")
  @PostMapping
  public ResponseEntity<ApiResponse<StoreDto>> createStore(
      @RequestBody @Valid CreateStoreRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    StoreDto storeDto = storeService.createStore(requestDto, userDetails);

    return ResponseEntity
        .status(STORE_CREATED.getStatus())
        .body(ApiResponse.from(STORE_CREATED.getStatus(), STORE_CREATED.getMessage(), storeDto));
  }

  // 특정 가게 정보를 수정하기
  @PreAuthorize("hasRole('MASTER')")
  @PatchMapping("/{storeId}")
  public ResponseEntity<ApiResponse<StoreDto>> updateAllStore(
      @PathVariable("storeId") UUID storeId,
      @RequestBody @Valid UpdateAllStoreRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    StoreDto storeDto = storeService.updateAllStore(storeId, requestDto, userDetails);

    return ResponseEntity
        .status(STORE_UPDATED.getStatus())
        .body(ApiResponse.from(STORE_UPDATED.getStatus(), STORE_UPDATED.getMessage(), storeDto));
  }

  // 특정 가게를 삭제하기
  @PreAuthorize("hasRole('MASTER')")
  @DeleteMapping("/{storeId}")
  public ResponseEntity<ApiResponse<StoreDto>> softDeleteStore(
      @PathVariable("storeId") UUID storeId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    StoreDto storeDto = storeService.softDeleteStore(storeId, userDetails);

    return ResponseEntity
        .status(STORE_DELETED.getStatus())
        .body(ApiResponse.from(STORE_DELETED.getStatus(), STORE_DELETED.getMessage(), storeDto));
  }

  // 모든 가게 정보를 조회하기
  @PreAuthorize("hasRole('MASTER')")
  @GetMapping
  public ResponseEntity<ApiResponse<Page<StoreDto>>> getAdminStores(
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size
  ) {
    Page<StoreDto> adminStores = storeService.getAdminAllStores(
        name,
        page,
        size,
        "createdAt",
        true);
    return ResponseEntity
        .status(STORE_FETCHED.getStatus())
        .body(ApiResponse.from(STORE_FETCHED.getStatus(), STORE_FETCHED.getMessage(), adminStores));
  }

  // 가게 카테고리 수정하기
  @PreAuthorize("hasRole('MASTER')")
  @PatchMapping("/{storeId}/categories")
  public ResponseEntity<ApiResponse<StoreDto>> updateCategoriesInStore(
      @PathVariable("storeId") UUID storeId,
      @RequestBody @Valid UpdateCategoriesInStoreRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    StoreDto storeDto = storeService.updateCategoriesInStore(storeId, requestDto, userDetails);

    return ResponseEntity
        .status(STORE_UPDATED.getStatus())
        .body(ApiResponse.from(STORE_UPDATED.getStatus(), STORE_UPDATED.getMessage(), storeDto));
  }

  // 가게 주소 수정하기
  @PreAuthorize("hasRole('MASTER')")
  @PatchMapping("/{storeId}/address")
  public ResponseEntity<ApiResponse<StoreDto>> updateAddressInStore(
      @PathVariable("storeId") UUID storeId,
      @RequestBody @Valid UpdateAddressInStoreRequestDto requestDto) {
    StoreDto storeDto = storeService.updateAddressInStore(storeId, requestDto);

    return ResponseEntity
        .status(STORE_UPDATED.getStatus())
        .body(ApiResponse.from(STORE_UPDATED.getStatus(), STORE_UPDATED.getMessage(), storeDto));
  }

  // 특정 가게 활성화 여부 설정하기
  @PreAuthorize("hasRole('MASTER')")
  @PatchMapping("/stores/{storeId}/active")
  public ResponseEntity<ApiResponse<StoreDto>> updateIsActiveInStore(
      @PathVariable("storeId") UUID storeId,
      @RequestBody @Valid UpdateStoreActivationRequestDto requestDto) {

    StoreDto storeDto = storeService.updateIsActiveInStore(storeId, requestDto.isActive());

    return ResponseEntity
        .status(STORE_UPDATED.getStatus())
        .body(ApiResponse.from(STORE_UPDATED.getStatus(), STORE_UPDATED.getMessage(), storeDto));
  }


  // 여러 가게 활성화 여부 설정하기
  @PreAuthorize("hasRole('MASTER')")
  @PatchMapping("/active")
  public ResponseEntity<ApiResponse<List<StoreDto>>> updateStoresActivationStatus(
      @RequestBody @Valid UpdateStoresActivationStatusRequestDto requestDto) {
    List<StoreDto> updatedStores = storeService.updateStoresActivationStatus(
        requestDto.storeActivationStatusList());

    return ResponseEntity
        .status(STORE_UPDATED.getStatus())
        .body(ApiResponse.from(STORE_UPDATED.getStatus(), STORE_UPDATED.getMessage(), updatedStores));
  }

  // 특정 가게 최소주문금액 수정하기
  @PreAuthorize("hasRole('MASTER')")
  @PatchMapping("/{storeId}/minimum-price")
  public ResponseEntity<ApiResponse<StoreDto>> updateMinimumPriceInStore(
      @PathVariable("storeId") UUID storeId,
      @RequestBody @Valid UpdateMinimumPriceRequestDto requestDto) {
    StoreDto storeDto = storeService.updateMinimumPriceInStore(storeId, requestDto);

    return ResponseEntity
        .status(STORE_UPDATED.getStatus())
        .body(ApiResponse.from(STORE_UPDATED.getStatus(), STORE_UPDATED.getMessage(), storeDto));
  }

  // 특정 가게 이름 수정하기
  @PreAuthorize("hasRole('MASTER')")
  @PatchMapping("/{storeId}/name")
  public ResponseEntity<ApiResponse<StoreDto>> updateNameInStore(
      @PathVariable("storeId") UUID storeId,
      @RequestBody @Valid UpdateStoreNameRequestDto requestDto) {
    StoreDto storeDto = storeService.updateNameInStore(storeId, requestDto);

    return ResponseEntity
        .status(STORE_UPDATED.getStatus())
        .body(ApiResponse.from(STORE_UPDATED.getStatus(), STORE_UPDATED.getMessage(), storeDto));
  }

  // 특정 가게 주인 변경하기
  @PreAuthorize("hasRole('MASTER')")
  @PatchMapping("/{storeId}/owner")
  public ResponseEntity<ApiResponse<StoreDto>> updateStoreOwner(
      @PathVariable("storeId") UUID storeId,
      @RequestBody @Valid UpdateStoreOwnerRequestDto requestDto) {
    StoreDto storeDto = storeService.updateStoreOwner(storeId, requestDto.userEmail());

    return ResponseEntity
        .status(STORE_UPDATED.getStatus())
        .body(ApiResponse.from(STORE_UPDATED.getStatus(), STORE_UPDATED.getMessage(), storeDto));
  }

  // 특정 가게 전화번호 변경하기
  @PreAuthorize("hasRole('MASTER')")
  @PatchMapping("/{storeId}/phone")
  public ResponseEntity<ApiResponse<StoreDto>> updatePhoneInStore(
      @PathVariable("storeId") UUID storeId,
      @RequestBody @Valid UpdateStorePhoneRequestDto requestDto) {

    StoreDto storeDto = storeService.updatePhoneInStore(storeId, requestDto.phone());

    return ResponseEntity
        .status(STORE_UPDATED.getStatus())
        .body(ApiResponse.from(STORE_UPDATED.getStatus(), STORE_UPDATED.getMessage(), storeDto));
  }
}
