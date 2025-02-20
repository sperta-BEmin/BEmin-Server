package run.bemin.api.review.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.review.dto.ReviewCreateRequestDto;
import run.bemin.api.review.dto.ReviewCreateResponseDto;
import run.bemin.api.review.dto.ReviewDeleteResponseDto;
import run.bemin.api.review.dto.ReviewUpdateRequestDto;
import run.bemin.api.review.dto.ReviewUpdateResponseDto;
import run.bemin.api.review.service.ReviewService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewService reviewService;

  // 리뷰 생성하기
  @PostMapping("/reviews")
  public ResponseEntity<ReviewCreateResponseDto> createReview(
      HttpServletRequest request,
      @RequestBody ReviewCreateRequestDto requestDto) {

    String authToken = request.getHeader("Authorization");

    ReviewCreateResponseDto responseDto = reviewService.createReview(authToken, requestDto);

    return ResponseEntity.ok(responseDto);
  }

  // 리뷰 수정하기
  @PatchMapping("/reviews/{reviewId}")
  public ResponseEntity<ReviewUpdateResponseDto> updateReview(
      HttpServletRequest request,
      @PathVariable UUID reviewId,
      @RequestBody ReviewUpdateRequestDto reviewUpdateRequest
  ) {
    String authToken = request.getHeader("Authorization");

    ReviewUpdateResponseDto updatedReview = reviewService.updateReview(authToken, reviewId, reviewUpdateRequest);

    return ResponseEntity.ok(updatedReview);
  }

  // 리뷰 삭제하기
  @DeleteMapping("/reviews/{reviewId}")
  public ResponseEntity<ReviewDeleteResponseDto> deleteReview(HttpServletRequest request, @PathVariable UUID reviewId) {
    String authToken = request.getHeader("Authorization");

    ReviewDeleteResponseDto deleteReview = reviewService.deleteReview(authToken, reviewId);

    return ResponseEntity.ok(deleteReview);
  }

  // 리뷰 평점
  @GetMapping("/review/rating")
  public double getAvgRating(@PathVariable UUID storeId) {
    double avg = reviewService.getAvgRatingByStore(storeId);
    return avg;
  }
}
