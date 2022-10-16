package pl.kostrowski.locking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LockingApplication {

	public static void main(String[] args) {
		SpringApplication.run(LockingApplication.class, args);
	}

}
