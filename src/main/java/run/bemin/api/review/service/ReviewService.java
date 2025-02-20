package run.bemin.api.review.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.auth.jwt.JwtUtil;
import run.bemin.api.order.entity.Order;
import run.bemin.api.order.repo.OrderRepository;
import run.bemin.api.review.domain.ReviewRating;
import run.bemin.api.review.dto.ReviewCreateRequestDto;
import run.bemin.api.review.dto.ReviewCreateResponseDto;
import run.bemin.api.review.dto.ReviewUpdateRequestDto;
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
  private final JwtUtil jwtUtil;

  // 토큰 추출하기
  public String extractToken(String token) {
    if (token == null || !token.startsWith("Bearer ")) {
      throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
    }

    String extractToken = token.substring(7);
    return jwtUtil.getUserEmailFromToken(extractToken);
  }

  // 리뷰 생성
  @Transactional
  public ReviewCreateResponseDto createReview(String authToken, ReviewCreateRequestDto requestDto) {
    // JWT 토큰에서 사용자 이메일 추출
    String userEmail = extractToken(authToken);

    // error code 수정하기
    Order order = orderRepository.findById(UUID.fromString(requestDto.getOrderId()))
        .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

    User user = userRepository.findByUserEmail(userEmail)
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

    Review review = Review.builder()
        .order(order)
        .user(user)
        .reviewRating(ReviewRating.fromValue(requestDto.getReviewRating()))
        .description(requestDto.getDescription())
        .build();

    reviewRepository.save(review);

    return ReviewCreateResponseDto.from(review);
  }

  // 리뷰 수정하기
  @Transactional
  public ReviewUpdateResponseDto updateReview(String authToken, UUID reviewId, ReviewUpdateRequestDto request) {
    // JWT 토큰에서 사용자 이메일 추출
    String userEmail = extractToken(authToken);

    // error code 수정하기
    // 사용자 조회
    User user = userRepository.findByUserEmail(userEmail)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    // 리뷰 조회
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));

    // 리뷰 작성자와 현재 로그인한 사용자 비교
    if (!review.getUser().equals(user)) {
      throw new IllegalArgumentException("본인이 작성한 리뷰만 수정할 수 있습니다.");
    }

    // 리뷰 수정
    review.updateReview(request.getReviewRating(), request.getDescription());

    return ReviewUpdateResponseDto.from(review);
  }

}
