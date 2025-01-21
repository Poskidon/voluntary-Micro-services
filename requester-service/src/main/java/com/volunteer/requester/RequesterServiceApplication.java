package com.volunteer.requester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.volunteer.requester.model")
@EnableJpaRepositories("com.volunteer.requester.repository")
public class RequesterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequesterServiceApplication.class, args);
    }
}