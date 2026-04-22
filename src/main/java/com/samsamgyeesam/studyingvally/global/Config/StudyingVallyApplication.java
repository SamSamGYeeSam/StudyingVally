package com.samsamgyeesam.studyingvally.global.Config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication

public class    StudyingVallyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyingVallyApplication.class, args);
    }

}
