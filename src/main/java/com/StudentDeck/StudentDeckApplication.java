package com.StudentDeck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.controller.AuthenticationController;

@SpringBootApplication
@ComponentScan(basePackageClasses = AuthenticationController.class)
public class StudentDeckApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentDeckApplication.class, args);
	}

}
