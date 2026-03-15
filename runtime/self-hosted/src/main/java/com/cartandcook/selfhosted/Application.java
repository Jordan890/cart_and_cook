package com.cartandcook.selfhosted;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.cartandcook.core",
        "com.cartandcook.adapters.persistencejpa",
        "com.cartandcook.selfhosted",
        "com.cartandcook.adapters.aiollama",
        "com.cartandcook.adapters.aiopenai",
        "com.cartandcook.adapters.aibedrock",
        "com.cartandcook.adapters.aihuggingface"
})
@EnableJpaRepositories(basePackages = "com.cartandcook.adapters.persistencejpa")
@EntityScan(basePackages = "com.cartandcook.adapters.persistencejpa")
@ConfigurationPropertiesScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}