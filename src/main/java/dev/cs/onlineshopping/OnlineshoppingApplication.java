package dev.cs.onlineshopping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class OnlineshoppingApplication {
    public static void main(String[] args) {
        log.error("Logging started here ....");
        SpringApplication.run(OnlineshoppingApplication.class, args);
    }
}