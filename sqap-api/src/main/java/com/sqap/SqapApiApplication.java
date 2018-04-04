package com.sqap;

import com.sqap.api.domain.file.FileStorageService;
import com.sqap.api.domain.file.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricExportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@SpringBootApplication(exclude = {MetricExportAutoConfiguration.class})
@EnableResourceServer
@EnableDiscoveryClient
//@EnableCaching
@EnableConfigurationProperties(StorageProperties.class)
public class SqapApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqapApiApplication.class, args);
    }

    @Bean
    CommandLineRunner init(FileStorageService storageService) {
        return (args) -> {
            storageService.removeAll();
            storageService.init();
        };
    }
}
