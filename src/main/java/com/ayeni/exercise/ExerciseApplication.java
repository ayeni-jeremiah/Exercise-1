package com.ayeni.exercise;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ayeni.exercise.DAO.RunFirst;

@SpringBootApplication
public class ExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExerciseApplication.class, args);
	}
	
	ApplicationRunner applicationRunner(RunFirst first) {
		return args -> {
			first.saveCardScheme();
		};
		
	}

}
