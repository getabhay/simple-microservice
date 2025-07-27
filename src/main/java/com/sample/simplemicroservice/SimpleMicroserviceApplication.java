package com.sample.simplemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SimpleMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleMicroserviceApplication.class, args);
	}

}
