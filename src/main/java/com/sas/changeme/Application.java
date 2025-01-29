package com.sas.changeme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.web.client.RestTemplate;

// The SpringBootApplication annotation applies common app-level annotations,
// such as @EnableAutoConfiguration, @ComponentScan and @Configuration
@SpringBootApplication
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}
