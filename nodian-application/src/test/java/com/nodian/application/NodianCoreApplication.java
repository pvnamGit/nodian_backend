package com.nodian.application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class }, scanBasePackages = "com.nodian")
public class NodianCoreApplication {
	public static void main(String[] args) {
		System.out.println("START APPLICATION");
		SpringApplication.run(NodianCoreApplication.class, args);
	}
}
