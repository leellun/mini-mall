package com.newland.mall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author leell
 */
@SpringBootApplication(scanBasePackages = "com.newland.mall")
@EnableFeignClients(basePackages = "com.newland.mall")
@EnableDiscoveryClient
public class AuthCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthCenterApplication.class, args);
    }

}
