package run.bemin.api.store.dto.response;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import run.bemin.api.category.dto.response.GetCategoryResponseDto;
import run.bemin.api.store.entity.Store;

public record GetStoreResponseDto(
    UUID storeId,
    String name,
    String phone,
    Integer minimumPrice,
    Float rating,
    GetStoreAddressResponseDto storeAddress,
    List<GetCategoryResponseDto> categories,
    String ownerEmail
) {

  public static GetStoreResponseDto fromEntity(Store store) {
    GetStoreAddressResponseDto storeAddressResponse = store.getStoreAddress() != null
        ? GetStoreAddressResponseDto.fromEntity(store.getStoreAddress())
        : null;

    // 소프트 삭제되지 않은 카테고리만 변환
    List<GetCategoryResponseDto> categoryResponses = store.getStoreCategories().stream()
        .filter(sc -> !sc.isDeleted())
        .map(sc -> GetCategoryResponseDto.fromEntity(sc.getCategory()))
        .collect(Collectors.toList());

    String ownerEmail = store.getOwner() != null ? store.getOwner().getUserEmail() : null;

    return new GetStoreResponseDto(
        store.getId(),
        store.getName(),
        store.getPhone(),
        store.getMinimumPrice(),
        store.getRating(),
        storeAddressResponse,
        categoryResponses,
        ownerEmail
    );
  }

  public static List<GetStoreResponseDto> fromEntities(Iterable<Store> stores) {
    return StreamSupport.stream(stores.spliterator(), false)
        .map(GetStoreResponseDto::fromEntity)
        .collect(Collectors.toList());
  }
}