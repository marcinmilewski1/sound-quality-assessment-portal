package com.sqap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;



@SpringBootApplication
@EnableCaching
public class SqapCommonTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqapCommonTestApplication.class, args);
    }

}
