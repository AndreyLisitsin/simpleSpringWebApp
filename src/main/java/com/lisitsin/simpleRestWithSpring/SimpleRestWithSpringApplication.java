package com.lisitsin.simpleRestWithSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class SimpleRestWithSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleRestWithSpringApplication.class, args);
	}

}
