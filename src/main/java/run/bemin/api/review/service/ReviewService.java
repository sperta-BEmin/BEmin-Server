package run.bemin.api.review.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.order.entity.Order;
import run.bemin.api.order.repo.OrderRepository;
import run.bemin.api.review.domain.ReviewRating;
import run.bemin.api.review.dto.ReviewCreateRequestDto;
import run.bemin.api.review.dto.ReviewCreateResponseDto;
import run.bemin.api.review.dto.ReviewUpdateResponseDto;
import run.bemin.api.review.entity.Review;
import run.bemin.api.review.repository.ReviewRepository;
import run.bemin.api.user.entity.User;
import run.bemin.api.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final OrderRepository orderRepository;
  private final UserRepository userRepository;

  // 리뷰 생성
  @Transactional
  public ReviewCreateResponseDto createReview(ReviewCreateRequestDto requestDto) {
    Order order = orderRepository.findById(requestDto.getOrderId())
        .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

    User user = userRepository.findByUserEmail(requestDto.getUserEmail())
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

    Review review = Review.builder()
        .order(order)
        .user(user)
        .reviewRating(ReviewRating.fromValue(requestDto.getReviewRating()))
        .description(requestDto.getDescription())
        .build();

    Review saveReview = reviewRepository.save(review);

    return new ReviewCreateResponseDto(saveReview);
  }

  // 리뷰 수정하기
  @Transactional
  public ReviewUpdateResponseDto updateReview(UUID reviewId, int newRate, String newDescription) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));

    review.updateReview(ReviewRating.fromValue(newRate), newDescription);

    Review updatedReview = reviewRepository.save(review);

    return new ReviewUpdateResponseDto(updatedReview);
  }

}
