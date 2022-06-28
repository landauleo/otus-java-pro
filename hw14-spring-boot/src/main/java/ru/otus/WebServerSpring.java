package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    Стартовая страница
    http://localhost:8080/clients
 */
@SpringBootApplication
public class WebServerSpring {

    public static void main(String[] args) {
        SpringApplication.run(WebServerSpring.class, args);
    }

}
