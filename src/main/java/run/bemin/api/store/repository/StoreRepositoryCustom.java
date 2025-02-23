package run.bemin.api.store.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import run.bemin.api.store.entity.Store;

public interface StoreRepositoryCustom {
  Page<Store> searchStores(String categoryName, String storeName, Pageable pageable);
}
