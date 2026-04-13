package com.samsamgyeesam.studyingvally.global.Config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.samsamgyeesam.studyingvally")
@EntityScan(basePackages = "com.samsamgyeesam.studyingvally")
@Configuration
public class JpaConfig {
}
