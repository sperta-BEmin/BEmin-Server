package run.bemin.api.comment.dto;

import java.util.List;

public record GPTResponse(
    List<Choice> choices
) {
  public record Choice(int index, Message message) {
  }
}

