package run.bemin.api.product.controller;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.product.dto.MessageResponseDto;
import run.bemin.api.product.dto.UpdateProductDetailDto;
import run.bemin.api.product.service.ProductService;
import run.bemin.api.product.validator.ProductValidator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
  private final ProductService productService;
  private final ProductValidator validator;

//  @PostMapping("/{storeId}/products")
//  public ApiResponse<MessageResponseDto> addProduct(@PathVariable String storeId,
//                                                    @RequestBody ProductRequestDto productRequestDto) {
//    UUID storeUUID = UUID.fromString(storeId);
////    Store store = validator.isStoreOwner(storeUUID);
//    productService.createProduct(
//        store,
//        productRequestDto.price(),
//        productRequestDto.title(),
//        productRequestDto.comment(),
//        productRequestDto.imageUrl()
//    );
//    return ApiResponse.from(HttpStatus.CREATED, "성공", new MessageResponseDto("상품 추가가 완료되었습니다."))
//  }

//  @GetMapping("/{store_id}/products")
//  public ApiResponse<Page<ProductSearchDto>> getProductsByStoreId(@PathVariable String storeId,
//                                                                @RequestParam("page") int page,
//                                                                @RequestParam("size") int size)
//  {
//    UUID storeUUID = UUID.fromString(storeId);
//    Store store = validator.isStoreOwner(storeUUID);
//    Page<ProductSearchDto> products = productService.getProducts(store, page, size);
//    return ApiResponse.from(HttpStatus.OK,"성공",products);
//  }

  @PostMapping("/{store_id}/{product_id}")
  public ApiResponse<MessageResponseDto> updateProductDetails(@PathVariable String store_id,
                                                              @PathVariable String product_id,
                                                              @RequestBody UpdateProductDetailDto requestDto)
  {
//    UUID storeUUID = UUID.fromString(storeId);
//    Store store = validator.isStoreOwner(storeUUID);
    productService.updateProductDetails(product_id,requestDto);
    return ApiResponse.from(HttpStatus.OK,"성공", new MessageResponseDto("상품 상세 설정 변경이 완료되었습니다."));
  }

  @DeleteMapping("/{store_id}/{product_id}")
  public ApiResponse<MessageResponseDto> deleteProduct(@PathVariable String store_id,
                                                       @PathVariable String product_id){
//    UUID storeUUID = UUID.fromString(storeId);
//    Store store = validator.isStoreOwner(storeUUID);
    LocalDateTime time = LocalDateTime.now();
    productService.deleteProduct(product_id,time);
    return ApiResponse.from(HttpStatus.OK,"성공", new MessageResponseDto("상품 삭제가 완료되었습니다."));
  }




}
