package com.newland.mall.recommend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.newland.mall")
@EnableFeignClients("com.newland.mall")
@EnableDiscoveryClient
@EnableTransactionManagement
public class MallRecommendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallRecommendApplication.class, args);
    }

}
