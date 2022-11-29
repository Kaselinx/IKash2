package com.taiwanlife.ikash.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.taiwanlife.ikash.backend.configuration.DatasourceConfiguration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "IKASH2 API", version = "2.0", description = "IKash backend Information"))
@EnableJpaRepositories( basePackages = { "com.taiwanlife.ikash.backend.repository" },
				entityManagerFactoryRef = "")
@ComponentScan("com.taiwanlife.ikash.backend")
@Import({DatasourceConfiguration.class})
public class iKashMain {

	public static void main(String[] args) {
		SpringApplication.run(iKashMain.class, args);
	}
}
