package run.bemin.api.comment.dto;

import java.util.List;

public record GPTRequest(
    String model,
    List<Message> messages,
    int max_tokens
) {
  public GPTRequest(String model, String devPrompt, String userPrompt, int maxToken){
    this(
        model,
        List.of(
            new Message("developer", devPrompt),
            new Message("user", userPrompt)),
        maxToken
    );
  }
}
