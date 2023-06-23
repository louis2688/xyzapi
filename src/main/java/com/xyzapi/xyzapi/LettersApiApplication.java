package com.xyzapi.xyzapi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class LettersApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LettersApiApplication.class, args);
    }
}

