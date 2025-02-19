package com.project_250131;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableBatchProcessing
@SpringBootApplication
public class Project250131Application {

	public static void main(String[] args) {
		SpringApplication.run(Project250131Application.class, args);
	}

}
