package run.bemin.api.review.exception;

import lombok.Getter;
import run.bemin.api.general.exception.ErrorCode;

@Getter
public class ReviewException extends RuntimeException {
  private final ErrorCode errorCode;

  public ReviewException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
