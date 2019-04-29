package com.haolarn.serviceauth;

import com.haolarn.serviceauth.Filter.ResponseFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableEurekaClient
public class ServiceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean responseFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new ResponseFilter());
        registration.addUrlPatterns("/*");
        registration.setName("responseFilter");
        registration.setOrder(-999999999);
        return registration;
    }

}
