package pl.kostrowski.nplusone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NPlusOneExamples {

	public static void main(String[] args) {
		SpringApplication.run(NPlusOneExamples.class, args);
	}

}
