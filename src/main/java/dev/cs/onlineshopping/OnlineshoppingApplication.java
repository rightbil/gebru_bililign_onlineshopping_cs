package dev.cs.onlineshopping;
import dev.cs.onlineshopping.utility.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
@Slf4j
@SpringBootApplication
public class OnlineshoppingApplication {
    public static void main(String[] args) {
        System.out.println("[Spare Part online shopping App has started....] " + Util.orderDate());
        log.error("Error Log for spare part online shopping application");
        SpringApplication.run(OnlineshoppingApplication.class, args);
    }
}