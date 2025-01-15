package com.ai_offshore.tools.wbs.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ai_offshore.tools.wbs.web.mapper")
public class WbsWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WbsWebApplication.class, args);
    }
} 