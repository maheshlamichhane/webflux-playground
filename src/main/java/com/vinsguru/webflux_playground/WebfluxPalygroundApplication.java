package com.vinsguru.webflux_playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;


@SpringBootApplication(scanBasePackages = "com.vinsguru.webflux_playground.sec04")
@EnableR2dbcRepositories(basePackages = "com.vinsguru.webflux_playground.sec04")
public class WebfluxPalygroundApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebfluxPalygroundApplication.class, args);
	}
}
