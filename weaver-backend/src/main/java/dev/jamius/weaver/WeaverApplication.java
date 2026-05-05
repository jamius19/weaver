package dev.jamius.weaver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class WeaverApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeaverApplication.class, args);
	}
}
