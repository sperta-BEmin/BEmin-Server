package run.bemin.api.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.category.entity.Category;
import run.bemin.api.category.exception.CategoryAlreadyExistsByNameException;
import run.bemin.api.store.dto.StoreDto;
import run.bemin.api.store.dto.request.CreateStoreRequestDto;
import run.bemin.api.store.entity.Store;
import run.bemin.api.store.repository.StoreRepository;

@RequiredArgsConstructor
@Service
public class StoreService {

  private final StoreRepository storeRepository;

  public Boolean existsStoreByName(String name) {
    return storeRepository.existsByName(name);
  }

  @Transactional
  public StoreDto createStore(CreateStoreRequestDto requestDto) {
    Store store = Store.create(
        requestDto.name(),
        requestDto.phone(),
        requestDto.minimumPrice(),
        requestDto.userEmail());

    Store savedStore = storeRepository.save(store);
    return StoreDto.fromEntity(savedStore);
  }
}
