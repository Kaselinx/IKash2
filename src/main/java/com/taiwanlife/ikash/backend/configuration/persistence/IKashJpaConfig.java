package com.taiwanlife.ikash.backend.configuration.persistence;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.core.env.Environment;
import org.hibernate.jpa.HibernatePersistenceProvider ;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties ;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean ;
import org.springframework.context.annotation.Configuration ;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary ;
import org.springframework.core.env.Environment ;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor ;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing ;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories ;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver ;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator ;
import org.springframework.orm.jpa.JpaTransactionManager ;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean ;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter ;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect ;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter ;
import org.springframework.transaction.PlatformTransactionManager ;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableJpaRepositories ( entityManagerFactoryRef = "entityManagerFactory",
							transactionManagerRef = "transactionManager",
							basePackages = { "com.taiwanlife.ikash.backend.repository" } )

public class IKashJpaConfig {
	
	@Resource
	private Environment env ;
	
	//private DatasourceChooser datasourceChooser ;
	

	
	@Resource
	private JpaProperties jpaProperties ;

	@Value("${dynamic.datasource.ikash.username}")
	private String databaseUsername;

	@Value("${dynamic.datasource.ikash.password}")
	private String databaseMima;

	@Value("${dynamic.datasource.ikash.driver-class-name}")
	private String databaseDriver;

	@Value("${dynamic.datasource.ikash.url}")
	private String databaseUrl;
	
//	@DatasourceScope(scope="IKash")
//	public DataSource IKashDataSource() {
//
//		 return null;
//	}


}
