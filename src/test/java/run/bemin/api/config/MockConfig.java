package run.bemin.api.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import run.bemin.api.auth.service.AuthService;

@TestConfiguration
public class MockConfig {
  @Bean
  public AuthService authService() {
    return Mockito.mock(AuthService.class);
  }
}
