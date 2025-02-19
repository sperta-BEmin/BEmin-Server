package run.bemin.api.comment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
public class ChatGPTConfig {
  @Value("${openai.key}")
  private String key;

  @Bean
  public RestClient restClient(){
    return RestClient.builder()
        .defaultHeaders(headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + key))
        .build();
  }
}
