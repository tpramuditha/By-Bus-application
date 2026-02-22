package com.bybus.ByBus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ByBusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ByBusApplication.class, args);
	}

}
