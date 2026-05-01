package com.sample.simplemicroservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SimpleMicroserviceApplication {

	@Value("${server.port:8080}")
	private String port;

	public static void main(String[] args) {
		SpringApplication.run(SimpleMicroserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner startupMessage() {
		return args -> {
			System.out.println("\n" + "=".repeat(50));
			System.out.println("🚀 MCart - Nova Project is now ONLINE");
			System.out.println("🌐 Access URL: http://localhost:" + port);
			System.out.println("=".repeat(50) + "\n");
		};
	}
}
