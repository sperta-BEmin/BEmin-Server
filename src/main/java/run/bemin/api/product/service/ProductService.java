package run.bemin.api.product.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.product.dto.ProductRequestDto;
import run.bemin.api.product.dto.ProductSearchDto;
import run.bemin.api.product.dto.UpdateProductDetailDto;
import run.bemin.api.product.entity.Product;
import run.bemin.api.product.exception.ProductNotFoundException;
import run.bemin.api.product.repository.ProductRepository;
import run.bemin.api.product.validator.ProductValidator;
import run.bemin.api.store.entity.Store;
import run.bemin.api.store.repository.StoreRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {

  private final ProductValidator validator;
  private final ProductRepository productRepository;
  private final StoreRepository storeRepository;

  @Transactional
  public void createProduct(Store store, int price, String title, String comment, String imageUrl) {
    Product product = Product.builder()
        .store(store)
        .price(price)
        .title(title)
        .comment(comment)
        .imageUrl(imageUrl)
        .build();
    productRepository.save(product);
  }

  public Page<ProductSearchDto> getProducts(Store store, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return productRepository.findByStoreId(store.getId(), pageable);
  }

  @Transactional
  public void updateProductDetails(String product_id, UpdateProductDetailDto requestDto) {
    Product product = productRepository.findById(UUID.fromString(product_id))
        .orElseThrow(ProductNotFoundException::new);

    requestDto.getPrice().ifPresent(product::updatePrice);
    requestDto.getTitle().ifPresent(product::updateTitle);
    requestDto.getImageUrl().ifPresent(product::updateImageUrl);
    requestDto.getIsHidden().ifPresent(product::updateIsHidden);
  }

  @Transactional
  public void deleteProduct(String product_id, LocalDateTime time) {
    Product product = productRepository.findById(UUID.fromString(product_id))
        .orElseThrow(ProductNotFoundException::new);
    String deletedBy = validator.isDeletedProduct(product);
    product.deleteProduct(deletedBy, time);
  }

}
