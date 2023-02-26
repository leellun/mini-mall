package com.mybatis.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.newland.**.mapper","com.newland.**.dao"})
public class MybatisXmlGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisXmlGeneratorApplication.class, args);
    }

}
