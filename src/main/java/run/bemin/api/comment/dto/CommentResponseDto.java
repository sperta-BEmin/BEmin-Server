package run.bemin.api.comment.dto;

import java.util.Objects;

public record CommentResponseDto(
    String content
) {
  public CommentResponseDto {
    Objects.requireNonNull(content);
  }
}
