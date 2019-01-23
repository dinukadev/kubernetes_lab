package com.justiceleague.justiceleaguemodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main spring boot application which will start up a web container and wire
 * up all the required beans.
 * 
 * @author dinuka
 *
 */
@SpringBootApplication
public class JusticeLeagueManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(JusticeLeagueManagementApplication.class, args);
	}
}
