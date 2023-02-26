package com.newland.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.newland.**.mapper","com.newland.**.dao"})
public class MybatisConfig {
}
