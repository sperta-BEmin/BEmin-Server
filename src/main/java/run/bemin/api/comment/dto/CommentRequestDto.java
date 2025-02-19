package run.bemin.api.comment.dto;

import java.util.Objects;

public record CommentRequestDto(
    String userPrompt
) {
  public CommentRequestDto {
    Objects.requireNonNull(userPrompt);
  }
}
