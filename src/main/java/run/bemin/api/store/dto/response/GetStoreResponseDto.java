package run.bemin.api.store.dto.response;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import run.bemin.api.category.dto.response.GetStoreCategoryResponseDto;
import run.bemin.api.store.entity.Store;

public record GetStoreResponseDto(
    UUID storeId,
    String name,
    String phone,
    Integer minimumPrice,
    Float rating,
    GetStoreAddressResponseDto storeAddress,
    List<GetStoreCategoryResponseDto> categories
) {
  public static GetStoreResponseDto fromEntity(Store store) {
    GetStoreAddressResponseDto storeAddressResponse = store.getStoreAddress() != null
        ? GetStoreAddressResponseDto.fromEntity(store.getStoreAddress())
        : null;

    // 소프트 삭제되지 않은 카테고리만 변환
    List<GetStoreCategoryResponseDto> categoryResponses = store.getStoreCategories().stream()
        .filter(sc -> !sc.getIsDeleted())
        .map(sc -> GetStoreCategoryResponseDto.fromEntity(sc.getCategory()))
        .collect(Collectors.toList());

    return new GetStoreResponseDto(
        store.getId(),
        store.getName(),
        store.getPhone(),
        store.getMinimumPrice(),
        store.getRating(),
        storeAddressResponse,
        categoryResponses
    );
  }

  public static List<GetStoreResponseDto> fromEntities(Iterable<Store> stores) {
    return StreamSupport.stream(stores.spliterator(), false)
        .map(GetStoreResponseDto::fromEntity)
        .collect(Collectors.toList());
  }

}
