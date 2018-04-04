package com.sqap;

import com.netflix.zuul.ZuulFilter;
import com.sqap.ui.config.CustomErrorFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class SqapUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqapUiApplication.class, args);
    }

    @Bean
    public ZuulFilter customFilter() {
        return new CustomErrorFilter();
    }
}
