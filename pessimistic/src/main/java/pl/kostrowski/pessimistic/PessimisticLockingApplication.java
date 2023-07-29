package pl.kostrowski.pessimistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PessimisticLockingApplication {

    public static final Integer SLEEP_TIME_MS = 100;


    public static void main(String[] args) {
        SpringApplication.run(PessimisticLockingApplication.class, args);
    }

}
