package com.taiwanlife.ikash.backend.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "spring.datasource.tle")
@Data
public class MainDatasourceProperties {
    private String username;
    private String password;
    private String url;
    private String driverClassName;

}
