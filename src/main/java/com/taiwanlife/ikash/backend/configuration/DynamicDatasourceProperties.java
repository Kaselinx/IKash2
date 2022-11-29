package com.taiwanlife.ikash.backend.configuration;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
@Component
@ConfigurationProperties(prefix = "dynamic")
@Data
public class DynamicDatasourceProperties {
    private Map<String,Map<String,String>> datasource;
}
