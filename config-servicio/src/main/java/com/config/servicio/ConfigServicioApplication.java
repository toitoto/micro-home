package com.config.servicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigServicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServicioApplication.class, args);
	}

}
