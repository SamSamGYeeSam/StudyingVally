package com.wanted.project.global.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.wanted.project")
@EntityScan(basePackages = "com.wanted.project")
public class JpaConfig {
}
