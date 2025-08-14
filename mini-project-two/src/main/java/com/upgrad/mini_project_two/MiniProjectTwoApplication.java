package com.upgrad.mini_project_two;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MiniProjectTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniProjectTwoApplication.class, args);
	}

}
