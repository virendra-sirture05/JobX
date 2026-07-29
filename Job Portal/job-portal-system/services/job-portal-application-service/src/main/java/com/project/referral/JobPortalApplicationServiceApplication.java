package com.project.referral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.project.referral.client")

public class JobPortalApplicationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobPortalApplicationServiceApplication.class, args);
    }

}
