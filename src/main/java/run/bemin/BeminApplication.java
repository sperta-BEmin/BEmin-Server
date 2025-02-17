package run.bemin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BeminApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeminApplication.class, args);
	}

}
