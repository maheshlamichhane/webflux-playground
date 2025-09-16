package com.vinsguru.webflux_palyground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.vinsguru.webflux_playground.${sec}")
@EnableR2dbcRepositories(basePackages = "com.vinsguru.webflux_playground.${sec}")
public class WebfluxPalygroundApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebfluxPalygroundApplication.class, args);
	}
}
