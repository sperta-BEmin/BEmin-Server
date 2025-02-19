package run.bemin.api.comment.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bemin.api.comment.entity.Comment;
import run.bemin.api.comment.repository.CommentRepository;
import run.bemin.api.product.entity.Product;
import run.bemin.api.product.exception.ProductNotFoundException;
import run.bemin.api.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final ProductRepository productRepository;

  public void saveComment(UUID productId, String content) {
    Product product = productRepository.findById(productId)
        .orElseThrow(ProductNotFoundException::new);

    Comment comment = Comment.builder()
        .content(content)
        .product(product)
        .build();

    commentRepository.save(comment);
  }
}
