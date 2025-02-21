package run.bemin.api.product.validator;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.product.entity.Product;
import run.bemin.api.product.exception.UnauthorizedStoreAccessException;
import run.bemin.api.security.UserDetailsImpl;
import run.bemin.api.store.entity.Store;
import run.bemin.api.store.exception.StoreNotFoundException;
import run.bemin.api.store.repository.StoreRepository;
import run.bemin.api.user.entity.UserRoleEnum;

@RequiredArgsConstructor
@Component
public class ProductValidator {
  private final StoreRepository storeRepository;

  @Transactional(readOnly = true)
  public Store isStoreOwner(UUID storeId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String email = ((UserDetailsImpl) auth.getPrincipal()).getUsername();
    UserRoleEnum role = ((UserDetailsImpl) auth.getPrincipal()).getUser().getRole();

    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new StoreNotFoundException(ProductValidationMessages.STORE_NOT_FOUND.getMessage()));

    if (!store.getOwner().getUserEmail().equals(email) || !role.equals(UserRoleEnum.OWNER)) {
      throw new UnauthorizedStoreAccessException(ProductValidationMessages.UNAUTHORIZED_ACCESS.getMessage());
    }

    return store;
  }

  public String isDeletedProduct(Product product) {
    if (!product.isActivated()) {
      throw new UnauthorizedStoreAccessException();
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return ((UserDetailsImpl) auth.getPrincipal()).getUsername();
  }

}
