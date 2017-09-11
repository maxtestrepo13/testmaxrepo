package com.db.xxii_century_school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class XxiiCenturySchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(XxiiCenturySchoolApplication.class, args);

	}
}
