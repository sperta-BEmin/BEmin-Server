package run.bemin.api.product.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import run.bemin.api.product.entity.Product;
import run.bemin.api.product.exception.UnauthorizedStoreAccessException;
import run.bemin.api.security.UserDetailsImpl;
import run.bemin.api.store.repository.StoreRepository;

@RequiredArgsConstructor
@Component
public class ProductValidator {
  private final StoreRepository storeRepository;

//  public Store isStoreOwner(UUID storeId) {
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    String email = ((UserDetailsImpl) auth.getPrincipal()).getUsername();
//    UserRoleEnum role = ((UserDetailsImpl) auth.getPrincipal()).getUser().getRole();
//
//    Optional<Store> store = storeRepository.findById(email);
//    if (store.isEmpty()) {
//      throw new StoreNotFoundException(ProductValidationMessages.STORE_NOT_FOUND.getMessage());
//    }
//    if (!role.equals(UserRoleEnum.OWNER) || !store.get().getUser.getId.equals(email)) {
//      throw new UnauthorizedStoreAccessException(ProductValidationMessages.UNAUTHORIZED_ACCESS.getMessage());
//    }
//    return store.get();
//  }

  public String isDeletedProduct(Product product) {
    if (!product.isActivated()) throw new UnauthorizedStoreAccessException();
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return ((UserDetailsImpl) auth.getPrincipal()).getUsername();
  }

}
