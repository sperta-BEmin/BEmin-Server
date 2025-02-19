package run.bemin.api.review.domain;

public enum ReviewRating {
  ONE(1),
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5);

  private final int value;

  ReviewRating(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public static ReviewRating fromValue(int value) {
    for (ReviewRating rating : values()) {
      if (rating.value == value) {
        return rating;
      }
    }

    throw new IllegalArgumentException("Invalid rating value : " + value);
  }
}
