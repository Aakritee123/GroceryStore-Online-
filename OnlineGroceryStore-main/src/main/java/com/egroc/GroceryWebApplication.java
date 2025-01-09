package com.egroc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.egroc","config", "controller","DTO","enums","exception","global","model","repository," +
		"service","Validation"})
@EnableJpaRepositories(basePackages =   {"config","com.egroc", "controller","DTO","enums","exception","global","model","repository," +
		"service","Validation"})
public class GroceryWebApplication {


	public static void main(String[] args) {
		SpringApplication.run(GroceryWebApplication.class, args);
	}

}

