package com.imprenta.sistemaventa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.imprenta.sistemaventa")
public class SistemaventaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaventaApplication.class, args);
	}
}