package run.bemin.api.review.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.auth.jwt.JwtUtil;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.order.entity.Order;
import run.bemin.api.order.repo.OrderRepository;
import run.bemin.api.review.domain.ReviewRating;
import run.bemin.api.review.dto.ReviewCreateRequestDto;
import run.bemin.api.review.dto.ReviewCreateResponseDto;
import run.bemin.api.review.dto.ReviewDeleteResponseDto;
import run.bemin.api.review.dto.ReviewUpdateRequestDto;
import run.bemin.api.review.dto.ReviewUpdateResponseDto;
import run.bemin.api.review.entity.Review;
import run.bemin.api.review.exception.ReviewException;
import run.bemin.api.review.repository.ReviewRepository;
import run.bemin.api.store.entity.Store;
import run.bemin.api.store.repository.StoreRepository;
import run.bemin.api.user.entity.User;
import run.bemin.api.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final OrderRepository orderRepository;
  private final StoreRepository storeRepository;
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

  // order 찾기
  private Order getOrder(ReviewCreateRequestDto requestDto) {
    Order order = orderRepository.findById(UUID.fromString(requestDto.getOrderId()))
        .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
    return order;
  }

  // user 찾기
  private User getUser(String userEmail) {
    User user = userRepository.findByUserEmail(userEmail)
        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
    return user;
  }

  // store 찾기
  private Store getStore(ReviewCreateRequestDto requestDto) {
    Store store = storeRepository.findById(UUID.fromString(requestDto.getStoreId()))
        .orElseThrow(() -> new IllegalArgumentException("해당 가게를 찾을 수 없습니다."));
    return store;
  }

  // review 찾기
  private Review getReview(UUID reviewId) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));
    return review;
  }

  // 리뷰 생성
  @Transactional
  public ReviewCreateResponseDto createReview(String authToken, ReviewCreateRequestDto requestDto) {
    // JWT 토큰에서 사용자 이메일 추출
    String userEmail = extractToken(authToken);

    // error code 수정하기
    Order order = getOrder(requestDto);

    User user = getUser(userEmail);

    Store store = getStore(requestDto);

    Review review = Review.builder()
        .order(order)
        .store(store)
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
    User user = getUser(userEmail);

    // 리뷰 조회
    Review review = getReview(reviewId);

    // 리뷰 작성자와 현재 로그인한 사용자 비교
    if (!review.getUser().equals(user)) {
      throw new ReviewException(ErrorCode.REVIEW_FORBIDDEN);
    }

    // 리뷰 수정
    review.updateReview(request.getReviewRating(), request.getDescription());

    return ReviewUpdateResponseDto.from(review);
  }

  // 리뷰 삭제하기
  @Transactional
  public ReviewDeleteResponseDto deleteReview(String authToken, UUID reviewId) {
    // JWT 토큰에서 사용자 이메일 추출
    String userEmail = extractToken(authToken);

    // 사용자 조회
    User user = getUser(userEmail);

    // 리뷰 조회
    Review review = getReview(reviewId);

    // 리뷰 작성자와 현재 로그인한 사용자 비교
    if (!review.getUser().equals(user)) {
      throw new ReviewException(ErrorCode.REVIEW_FORBIDDEN);
    }

    review.deletedBy(userEmail);

    return ReviewDeleteResponseDto.from(review);
  }

  // 특정 가게의 평균 평점 계산
  @Transactional
  public double getAvgRatingByStore(UUID storeId) {
    double avg = reviewRepository.findAverageRatingByStore(storeId);
    log.info("가게 평점 : {}", avg);
    return avg;
  }

  // 특정 가게의 리뷰 조회
  @Transactional(readOnly = true)
  public Page<Review> getReviewsByStore(UUID storeId, Pageable pageable) {
    return reviewRepository.findByStore_Id(storeId, pageable);
  }
}
