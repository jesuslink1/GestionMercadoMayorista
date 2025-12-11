package com.sise.GestionMercadoMayorista;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestionMercadoMayoristaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionMercadoMayoristaApplication.class, args);
	}

}
