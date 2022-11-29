package com.taiwanlife.ikash.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class DatasourceConfiguration {

    final MainDatasourceProperties mainDatasourceProperties;
    final DynamicDatasourceProperties dynamicDatasourceProperties;

    @Bean
    public DataSource datasource(){
        Map<Object,Object> datasourceMap = new HashMap<>();
        DatasourceChooser datasourceChooser = new DatasourceChooser();
        /**
         * main database
         */
        DataSourceBuilder<?> db = DataSourceBuilder.create();
        db.driverClassName(mainDatasourceProperties.getDriverClassName());
        db.url(mainDatasourceProperties.getUrl());
        db.username(mainDatasourceProperties.getUsername());
        db.password(mainDatasourceProperties.getPassword());
        
        DataSource mainDataSource = db.build();
        datasourceMap.put("main", db.build());
        /**
         * other database
         */
        Map<String, Map<String, String>> sourceMap = dynamicDatasourceProperties.getDatasource();
        sourceMap.forEach((datasourceName,datasourceMaps) -> {
            //DruidDataSource dataSource = new DruidDataSource();
            datasourceMaps.forEach((K,V) -> {
                String setField = "set" + K.substring(0, 1).toUpperCase() + K.substring(1);
                String[] strings = setField.split("");
                StringBuilder newStr = new StringBuilder();
                for (int i = 0; i < strings.length; i++) {
                    if (strings[i].equals("-")) strings[i + 1] = strings[i + 1].toUpperCase();
                    if (!strings[i].equals("-")) newStr.append(strings[i]);
                }
                try {
//                	 System.out.println("DatasourceName:"+ datasourceName);
//                     System.out.println("Field:" + setField);	
//                     System.out.println("Value:" + V);
                    //DataSource.class.getMethod(newStr.toString(),String.class).invoke(dataSource,V);
                     // start building target dataSource
                     // user name;
                     if (setField == "setUsername") {
                    	 db.username(V);
                     }
                     
                     // password;
                     if (setField == "setPassword") {
                    	 db.password(V);
                    	 
                     }
                     
                     // Uri
                     if (setField == "setUrl") {
                    	 db.url(V);
                     }
                     
                     // class
                     if (setField == "setDriver-class-name") {
                    	 db.driverClassName(V);
                    	 
                     }
                    
                     datasourceMap.put(datasourceName, db.build());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        // set target database
        datasourceChooser.setTargetDataSources(datasourceMap);
        // set main database
        datasourceChooser.setDefaultTargetDataSource(mainDataSource);
        return datasourceChooser;
    }
}
