package run.bemin.api.config;

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

  @Bean
  public Hibernate6Module hibernate6Module() {
    Hibernate6Module module = new Hibernate6Module();
    // 필요에 따라 lazy 로딩된 속성을 직렬화하지 않도록 설정
    module.disable(Hibernate6Module.Feature.FORCE_LAZY_LOADING);
    return module;
  }
}
