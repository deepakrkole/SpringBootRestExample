package com.spring.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class SpringRestBoot1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestBoot1Application.class, args);
    }
}
