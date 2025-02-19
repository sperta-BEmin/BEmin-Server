package run.bemin.api.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import run.bemin.api.comment.dto.GPTRequest;
import run.bemin.api.comment.dto.GPTResponse;

@Service
@RequiredArgsConstructor
public class ChatGPTService {
  @Value("${openai.model}")
  private String model;

  @Value("${openai.url}")
  private String apiURL;

  @Value("${openai.prompt}")
  private String devPrompt;

  @Value("${openai.maxToken}")
  private int maxToken;

  private final RestClient restClient;

  public String getChatGPTResponse(String userPrompt) {
    GPTRequest request = new GPTRequest(model, devPrompt, userPrompt, maxToken);
    GPTResponse response = restClient.post()
        .uri(apiURL)
        .body(request)
        .retrieve()
        .body(GPTResponse.class);
    return response.choices().get(0).message().content();
  }

}
