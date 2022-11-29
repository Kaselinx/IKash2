//package com.taiwanlife.ikash.backend.configuration;
//
//import javax.sql.DataSource;
//
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactory", basePackages = {"com.taiwanlife.ikash.backend.repository.Imp"})
//public class IKashDatasourceConfiguration  {
//	
//	@Primary
//    @Bean
//    @ConfigurationProperties("spring.datasource")
//    public DataSourceProperties  ACSDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    @ConfigurationProperties("spring.datasource.ikash")
//    public DataSourceProperties IKashDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//    
//    
//    @Bean
//    @ConfigurationProperties("spring.datasource.ikash1")
//    public DataSourceProperties IKash1DataSourceProperties() {
//        return new DataSourceProperties();
//    }
//    
//   
//    
//    @Bean
//    @ConfigurationProperties("spring.datasource.ikash2")
//    public DataSourceProperties IKash2DataSourceProperties() {
//        return new DataSourceProperties();
//    }
//    
//   
//    
//    @Bean
//    @ConfigurationProperties("spring.datasource.ikash3")
//    public DataSourceProperties IKash3DataSourceProperties() {
//        return new DataSourceProperties();
//    }
//    
//   
//   
//    @Bean
//    public DataSource ACSDataSource() {
//        return ACSDataSourceProperties()
//          .initializeDataSourceBuilder()
//          .build();
//    }
//    
//    @Bean
//    public DataSource IKashDataSource() {
//        return IKashDataSourceProperties()
//          .initializeDataSourceBuilder()
//          .build();
//    }
//    
//    @Bean
//    public DataSource IKash1DataSource() {
//        return IKash1DataSourceProperties()
//          .initializeDataSourceBuilder()
//          .build();
//    }
//
//    @Bean
//    public DataSource IKash2DataSource() {
//        return IKash2DataSourceProperties()
//          .initializeDataSourceBuilder()
//          .build();
//    }
//    
//    @Bean
//    public DataSource IKash3DataSource() {
//        return IKash3DataSourceProperties()
//          .initializeDataSourceBuilder()
//          .build();
//    }
//
//}