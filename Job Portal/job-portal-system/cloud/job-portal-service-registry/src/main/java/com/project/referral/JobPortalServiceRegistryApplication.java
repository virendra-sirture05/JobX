package com.project.referral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class JobPortalServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalServiceRegistryApplication.class, args);
	}

}
