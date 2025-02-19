package run.bemin.api.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.review.dto.ReviewCreateRequestDto;
import run.bemin.api.review.dto.ReviewCreateResponseDto;
import run.bemin.api.review.service.ReviewService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewService reviewService;

  // 리뷰 생성하기
  @PostMapping("/reviews")
  public ResponseEntity<ReviewCreateResponseDto> createReview(@Valid @RequestBody ReviewCreateRequestDto requestDto) {
    ReviewCreateResponseDto responseDto = reviewService.createReview(requestDto);
    return ResponseEntity.ok(responseDto);
  }
}
