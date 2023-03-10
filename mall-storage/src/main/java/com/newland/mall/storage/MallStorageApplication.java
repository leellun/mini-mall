package com.newland.mall.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MallStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallStorageApplication.class, args);
	}

}
