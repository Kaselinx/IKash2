package com.taiwanlife.ikash.backend.configuration.persistence;

import java.sql.SQLException ;
import java.util.Collections ;
import java.util.Properties ;

import javax.annotation.Resource ;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource ;

import org.hibernate.jpa.HibernatePersistenceProvider ;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties ;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean ;
import org.springframework.context.annotation.Configuration ;
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
import org.springframework.transaction.annotation.EnableTransactionManagement ;

import lombok.extern.slf4j.Slf4j ;
  
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories ( entityManagerFactoryRef = "entityManagerFactory",
		transactionManagerRef = "transactionManager",
		basePackages = { "com.taiwanlife.ikash.backend.repository" } )
@EnableJpaAuditing
@Slf4j
public class JpaConfig
{
	
	@Resource
	private Environment env ;
	
	private DataSource dataSource ;
	
	@Resource
	private JpaProperties jpaProperties ;

	@Value("${spring.datasource.tle.username}")
	private String databaseUsername;

	@Value("${spring.datasource.tle.password}")
	private String databaseMima;

	@Value("${spring.datasource.tle.driver-class-name}")
	private String databaseDriver;

	@Value("${spring.datasource.tle.url}")
	private String databaseUrl;
	
	
	//set entity auto mapping property. 
	private static final String ENTITY_PACKAGE = "com.taiwanlife.ikash.backend.entity.acs" ;
	
	/**
	 * Constructor
	 */
	public JpaConfig ()
	{
		super () ;
		log.info ( "**** JpaConfig : {}", this.getClass ().getName () ) ;
	}

	@Bean (name = "dataSourceMSSQL")
	@Primary
	@ConfigurationProperties("spring.datasource.hikari")
	public DataSource dataSource() {
		if(log.isDebugEnabled()){
			log.debug("Connecting to MSSQL... ");
			log.debug("** Driver: " + databaseDriver);
			log.debug("** URL: " + databaseUrl);
			log.debug("** Username: " + databaseUsername);
			log.debug("** Password: " + databaseMima);
		}

		DataSourceBuilder<?> dsBuilder = DataSourceBuilder.create();
		dsBuilder.driverClassName(databaseDriver);
		dsBuilder.url(databaseUrl);
		dsBuilder.username(databaseUsername);
		dsBuilder.password(databaseMima);

		return dsBuilder.build();
	}

	/**
	 * @return LocalContainerEntityManagerFactoryBean
	 * @throws SQLException 
	 */
	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory (@Qualifier("dataSourceMSSQL") DataSource ds)
	{
		dataSource = ds;

		final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean () ;
		factory.setDataSource ( dataSource ) ;
		factory.setJpaDialect ( new HibernateJpaDialect () ) ;
	    factory.setJpaPropertyMap ( Collections.singletonMap ( "javax.persistence.validation.mode", "none" ) ) ;
	    factory.setPersistenceProvider ( new HibernatePersistenceProvider () ) ;
	    factory.setPackagesToScan ( new String [] { ENTITY_PACKAGE } ) ;

	    
	    factory.setJpaVendorAdapter ( jpaVendorAdapter () ) ;
	    factory.setJpaProperties ( hibernateProperties () ) ;

	    factory.afterPropertiesSet () ;
	    
	    factory.setLoadTimeWeaver ( new InstrumentationLoadTimeWeaver () ) ;
	    
	    return factory ;
	}
	
	/**
	 * @return PlatformTransactionManager
	 */
	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager (@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory)
	{
		return new JpaTransactionManager(entityManagerFactory);
    }
	
	/**
	 * @return PersistenceExceptionTranslationPostProcessor
	 */
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation ()
	{
		return new PersistenceExceptionTranslationPostProcessor () ;
	}
	
	/**
	 * @return SQLErrorCodeSQLExceptionTranslator
	 */
	@Bean
	public SQLErrorCodeSQLExceptionTranslator jdbcExceptionTranslator ()
	{
		SQLErrorCodeSQLExceptionTranslator sqlExceptionTranslator = new SQLErrorCodeSQLExceptionTranslator () ;
		sqlExceptionTranslator.setDataSource ( dataSource ) ;
		return sqlExceptionTranslator ;
	}
	
	/**
	 * @return AbstractJpaVendorAdapter
	 */
	private AbstractJpaVendorAdapter jpaVendorAdapter ()
	{
		
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter () ;

		if(log.isDebugEnabled()) {
			log.debug("** spring.jpa.database-platform: " + env.getProperty("spring.jpa.database-platform"));
			log.debug("** spring.jpa.generate-ddl: " + env.getProperty("spring.jpa.generate-ddl", Boolean.class));
			log.debug("** spring.jpa.show-sql: " + env.getProperty("spring.jpa.show-sql", Boolean.class));
		}

		jpaVendorAdapter.setDatabasePlatform ( env.getProperty ( "spring.jpa.database-platform" ) ) ;
		jpaVendorAdapter.setGenerateDdl ( env.getProperty ( "spring.jpa.generate-ddl", Boolean.class ) ) ;
		jpaVendorAdapter.setShowSql ( env.getProperty ( "spring.jpa.show-sql", Boolean.class ) ) ;
		
		return jpaVendorAdapter ;
		
	}
	
	/**
	 * @return Properties
	 */
	private Properties hibernateProperties ()
	{
		
		Properties properties = new Properties () ;
		
		properties.setProperty ( "javax.persistence.provider", "org.hibernate.jpa.HibernatePersistenceProvider" ) ;
		properties.setProperty ( "hibernate.dialect", env.getProperty ( "spring.jpa.properties.hibernate.dialect" ) ) ;
		properties.setProperty ( "hibernate.hbm2ddl.auto", env.getProperty ( "spring.jpa.hibernate.ddl-auto" ) ) ;
		properties.setProperty ( "hibernate.show_sql", env.getProperty ( "spring.jpa.show-sql" ) ) ;
		properties.setProperty ( "hibernate.format_sql", env.getProperty ( "spring.jpa.properties.hibernate.format_sql" ) ) ;
		
//		properties.setProperty ( "hibernate.use_nationalized_character_data", env.getProperty ( "spring.jpa.properties.hibernate.use_nationalized_character_data" ) ) ;
		properties.setProperty ( "hibernate.current_session_context_class", env.getProperty ( "spring.jpa.properties.hibernate.current_session_context_class" ) ) ;
		properties.setProperty ( "hibernate.jdbc.lob.non_contextual_creation", env.getProperty ( "spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation" ) ) ;
		
//		properties.setProperty ( "hibernate.physical_naming_strategy", "spring.jpa.hibernate.naming.physical-strategy");
//		properties.setProperty ( "hibernate.implicit_naming_strategy", "spring.jpa.hibernate.naming.implicit-strategy");

		if(log.isDebugEnabled()) {
			log.debug("** jpaProperties: " + jpaProperties.getProperties ());
		}

		properties.putAll ( jpaProperties.getProperties () ) ;
		
		return properties ;
		
	}
	
}

