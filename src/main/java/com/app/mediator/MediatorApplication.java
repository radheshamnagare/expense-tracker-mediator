package com.app.mediator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySource("classpath:expensetrackerapi.properties")
public class MediatorApplication {
   final static Logger LOGGER = LogManager.getLogger(MediatorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MediatorApplication.class, args);
	}

}
