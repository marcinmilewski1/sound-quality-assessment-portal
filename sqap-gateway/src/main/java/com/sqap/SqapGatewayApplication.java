package com.sqap;

import com.netflix.zuul.ZuulFilter;
import com.sqap.gateway.config.CustomErrorFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;


@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
//@EnableRequestCorrelation
public class SqapGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqapGatewayApplication.class, args);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return (container -> {
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/");
            container.addErrorPages(error404Page);
        });
    }

    @Bean
    public ZuulFilter customFilter() {
        return new CustomErrorFilter();
    }

}
