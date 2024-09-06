package com.wekeepinmid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.wekeepinmind"})
public class WeKeepInMindApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeKeepInMindApplication.class, args);
    }
}