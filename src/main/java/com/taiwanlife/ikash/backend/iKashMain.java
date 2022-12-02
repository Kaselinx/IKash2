package com.taiwanlife.ikash.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication()
@OpenAPIDefinition(info = @Info(title = "IKASH2 API", version = "2.0", description = "IKash backend Information"))
@ComponentScan("com.taiwanlife.ikash.backend")
public class iKashMain {

	public static void main(String[] args) {
		log.info("Program started.....");
		SpringApplication.run(iKashMain.class, args);
	}
}
