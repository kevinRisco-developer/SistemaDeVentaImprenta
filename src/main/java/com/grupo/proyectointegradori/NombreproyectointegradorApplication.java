package com.grupo.proyectointegradori;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.grupo.proyectointegradori")
public class NombreproyectointegradorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NombreproyectointegradorApplication.class, args);
	}
	
}

