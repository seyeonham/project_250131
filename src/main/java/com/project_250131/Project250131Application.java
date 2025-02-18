package com.project_250131;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.project_250131.store.batch")
public class Project250131Application {

	public static void main(String[] args) {
		SpringApplication.run(Project250131Application.class, args);
	}

}
