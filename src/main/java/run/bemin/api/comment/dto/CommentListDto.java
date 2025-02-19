package run.bemin.api.comment.dto;

import java.util.List;
import java.util.Objects;

public record CommentListDto(
    List<String> comments
) {
  public CommentListDto {
    Objects.requireNonNull(comments);
  }
}
