package com.example.umcspringbootstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UmcSpringbootStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmcSpringbootStudyApplication.class, args);
    }

}
