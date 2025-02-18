package run.bemin.api.product.entity;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import run.bemin.api.product.repository.ProductRepository;
import run.bemin.api.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductEntityService {
  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  @PostConstruct
  private void saveTestUser(){
//    User user = User.builder()
//        .userEmail("jsdho1@naver.com")
//        .password("1234")
//        .name("김형주")
//        .nickname("1234")
//        .phone("010-1234-1234")
//        .address("1234")
//        .role(UserRoleEnum.CUSTOMER)
//        .build();
//
//    userRepository.save(user);
//
//    Product product = Product.builder()
//        .user(user)
//        .price(1000)
//        .title("백반")
//        .comment("맛있는 백반맛있는 백반맛있는 백반맛있는 백반맛있는 백반맛있는 백반맛있는 백반맛있는 백반맛있는 백반맛있는 백반맛있는 백반맛있는 백반")
//        .imageUrl("www.naver.com")
//        .build();
//    productRepository.save(product);

  }

}
