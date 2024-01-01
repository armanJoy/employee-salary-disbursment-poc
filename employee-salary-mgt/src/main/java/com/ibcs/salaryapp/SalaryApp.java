package com.ibcs.salaryapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
public class SalaryApp extends SpringBootServletInitializer {

    private static final Logger log = LogManager.getLogger(SalaryApp.class);

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(SalaryApp.class);
//    }
//
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
    public static void main(String[] args) {

        log.info("SalaryApp started successfully");
        SpringApplication.run(SalaryApp.class, args);
        System.out.println("SalaryApp started...");
    }

}
