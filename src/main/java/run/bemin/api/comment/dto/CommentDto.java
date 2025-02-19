package run.bemin.api.comment.dto;

import java.util.Objects;

public record CommentDto(
    String content
) {
  public CommentDto {
    Objects.requireNonNull(content);
  }
}
