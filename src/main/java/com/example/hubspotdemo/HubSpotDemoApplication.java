package com.example.hubspotdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * HubSpot CRM 集成示例应用主类
 */
@SpringBootApplication
@EnableFeignClients
public class HubSpotDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HubSpotDemoApplication.class, args);
    }

}