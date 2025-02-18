package run.bemin.api.product.dto;

import java.util.Objects;

public record MessageResponseDto(
    String message
) {
  public MessageResponseDto {
    Objects.requireNonNull(message);
  }
}
