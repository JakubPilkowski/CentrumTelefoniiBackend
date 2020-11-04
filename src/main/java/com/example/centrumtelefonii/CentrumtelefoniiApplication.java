package com.example.centrumtelefonii;

import com.example.centrumtelefonii.dao.StorageService;
import com.example.centrumtelefonii.services.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(StorageProperties.class)
public class CentrumtelefoniiApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(CentrumtelefoniiApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
