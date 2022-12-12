package com.taiwanlife.ikash.backend.configuration.persistence;

import java.sql.SQLException ;
import java.util.Collections ;
import java.util.HashMap;
import java.util.Map;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator ;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager ;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean ;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter ;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect ;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter ;
import org.springframework.transaction.PlatformTransactionManager ;
import org.springframework.transaction.annotation.EnableTransactionManagement ;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j ;
 


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.taiwanlife.ikash.backend.repository",
        entityManagerFactoryRef = "multiEntityManager",
        transactionManagerRef = "multiTransactionManager"
)
public class JpaConfig {
    private final String PACKAGE_SCAN = "com.taiwanlife.ikash.backend.entity";
    
    //main properties
	@Value("${spring.datasource.tle.username}")
	private String maindatabaseUsername;

	@Value("${spring.datasource.tle.password}")
	private String maindatabaseMima;

	@Value("${spring.datasource.tle.driver-class-name}")
	private String maindatabaseDriver;

	@Value("${spring.datasource.tle.url}")
	private String maindatabaseUrl;
	
    
    //main data source
    @Primary
    @Bean(name = "mainDataSource")
    public DataSource mainDataSource() {
    	DataSourceBuilder<?> dsBuilder = DataSourceBuilder.create();
		dsBuilder.driverClassName(maindatabaseDriver);
		dsBuilder.url(maindatabaseUrl);
		dsBuilder.username(maindatabaseUsername);
		dsBuilder.password(maindatabaseMima);
		return dsBuilder.build();
        //return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
    
    
    @Value("${dynamic.datasource.ikash.username}")
	private String ikashdatabaseUsername;

	@Value("${dynamic.datasource.ikash.password}")
	private String ikashdatabaseMima;

	@Value("${dynamic.datasource.ikash.driver-class-name}")
	private String ikashdatabaseDriver;

	@Value("${dynamic.datasource.ikash.url}")
	private String ikashdatabaseUrl;
	
    @Bean(name = "ikashDataSource")
    public DataSource ikashDataSource() {
    	DataSourceBuilder<?> dsBuilder = DataSourceBuilder.create();
		dsBuilder.driverClassName(ikashdatabaseDriver);
		dsBuilder.url(ikashdatabaseUrl);
		dsBuilder.username(ikashdatabaseUsername);
		dsBuilder.password(ikashdatabaseMima);
		return dsBuilder.build();
        //return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
    
    @Value("${dynamic.datasource.ikash1.username}")
	private String ikash1databaseUsername;

	@Value("${dynamic.datasource.ikash1.password}")
	private String ikash1databaseMima;

	@Value("${dynamic.datasource.ikash1.driver-class-name}")
	private String ikash1databaseDriver;

	@Value("${dynamic.datasource.ikash1.url}")
	private String ikash1databaseUrl;
	
	
    @Bean(name = "ikash1DataSource")
    public DataSource ikash1DataSource() {
    	DataSourceBuilder<?> dsBuilder = DataSourceBuilder.create();
		dsBuilder.driverClassName(ikash1databaseDriver);
		dsBuilder.url(ikash1databaseUrl);
		dsBuilder.username(ikash1databaseUsername);
		dsBuilder.password(ikash1databaseMima);
		return dsBuilder.build();
        //return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
    

    @Value("${dynamic.datasource.ikash2.username}")
	private String ikash2databaseUsername;

	@Value("${dynamic.datasource.ikash2.password}")
	private String ikash2databaseMima;

	@Value("${dynamic.datasource.ikash2.driver-class-name}")
	private String ikash2databaseDriver;

	@Value("${dynamic.datasource.ikash2.url}")
	private String ikash2databaseUrl;
	
    @Bean(name = "ikash2DataSource")
    public DataSource ikash2DataSource() {
      	DataSourceBuilder<?> dsBuilder = DataSourceBuilder.create();
    		dsBuilder.driverClassName(ikash2databaseDriver);
    		dsBuilder.url(ikash2databaseUrl);
    		dsBuilder.username(ikash2databaseUsername);
    		dsBuilder.password(ikash2databaseMima);
    		return dsBuilder.build();
        //return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
    
    
    @Value("${dynamic.datasource.ikash3.username}")
	private String ikash3databaseUsername;

	@Value("${dynamic.datasource.ikash3.password}")
	private String ikash3databaseMima;

	@Value("${dynamic.datasource.ikash3.driver-class-name}")
	private String ikash3databaseDriver;

	@Value("${dynamic.datasource.ikash3.url}")
	private String ikash3databaseUrl;
	
    @Bean(name = "ikash3DataSource")
    public DataSource ikash3DataSource() {
    	
      	DataSourceBuilder<?> dsBuilder = DataSourceBuilder.create();
    		dsBuilder.driverClassName(ikash2databaseDriver);
    		dsBuilder.url(ikash2databaseUrl);
    		dsBuilder.username(ikash2databaseUsername);
    		dsBuilder.password(ikash2databaseMima);
    		return dsBuilder.build();
        //return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
    
    @Bean
    public JdbcTemplate tleJdbcTemplate(@Qualifier("mainDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    
    @Bean
    public JdbcTemplate ikashJdbcTemplate(@Qualifier("ikashDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    
    @Bean
    public JdbcTemplate ikash1JdbcTemplate(@Qualifier("ikash1DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    
    @Bean
    public JdbcTemplate ikash2JdbcTemplate(@Qualifier("ikash2DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
    @Bean
    public JdbcTemplate ikash3JdbcTemplate(@Qualifier("ikash3DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    
    @Bean(name = "multiRoutingDataSource")
    public DataSource multiRoutingDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.MAIN, mainDataSource());
        targetDataSources.put(DBTypeEnum.IKASH, ikashDataSource());
        targetDataSources.put(DBTypeEnum.IKASH1, ikash1DataSource());
        targetDataSources.put(DBTypeEnum.IKASH2, ikash2DataSource());
        targetDataSources.put(DBTypeEnum.IKASH3, ikash3DataSource());
        MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();
        multiRoutingDataSource.setDefaultTargetDataSource(mainDataSource());
        multiRoutingDataSource.setTargetDataSources(targetDataSources);
        return multiRoutingDataSource;
    }
    
    
    @Bean(name = "multiEntityManager")
    public LocalContainerEntityManagerFactoryBean multiEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(multiRoutingDataSource());
        em.setPackagesToScan(PACKAGE_SCAN);
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        return em;
    }
    @Bean(name = "multiTransactionManager")
    public PlatformTransactionManager multiTransactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                multiEntityManager().getObject());
        return transactionManager;
    }
    @Primary
    @Bean(name = "dbSessionFactory")
    public LocalSessionFactoryBean dbSessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(multiRoutingDataSource());
        sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        return properties;
    }
    
    public enum DBTypeEnum {
        MAIN, IKASH, IKASH1, IKASH2, IKASH3;
    }
}
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories ( entityManagerFactoryRef = "entityManagerFactory",
//		transactionManagerRef = "transactionManager",
//		basePackages = { "com.taiwanlife.ikash.backend.repository" } )
//@EnableJpaAuditing
//@Slf4j
//public class JpaConfig
//{
//	
//	@Resource
//	private Environment env ;
//	
//	private DataSource dataSource ;
//	
//	@Resource
//	private JpaProperties jpaProperties ;
//
//	@Value("${spring.datasource.tle.username}")
//	private String databaseUsername;
//
//	@Value("${spring.datasource.tle.password}")
//	private String databaseMima;
//
//	@Value("${spring.datasource.tle.driver-class-name}")
//	private String databaseDriver;
//
//	@Value("${spring.datasource.tle.url}")
//	private String databaseUrl;
//	
//	
//	//set entity auto mapping property. 
//	private static final String ENTITY_PACKAGE = "com.taiwanlife.ikash.backend.entity.acs" ;
//	
//	/**
//	 * Constructor
//	 */
//	public JpaConfig ()
//	{
//		super () ;
//		log.info ( "**** JpaConfig : {}", this.getClass ().getName () ) ;
//	}
//
//	@Bean (name = "dataSourceMSSQL")
//	@Primary
//	@ConfigurationProperties("spring.datasource.hikari")
//	public DataSource dataSource() {
//		if(log.isDebugEnabled()){
//			log.debug("Connecting to MSSQL... ");
//			log.debug("** Driver: " + databaseDriver);
//			log.debug("** URL: " + databaseUrl);
//			log.debug("** Username: " + databaseUsername);
//			log.debug("** Password: " + databaseMima);
//		}
//
//		DataSourceBuilder<?> dsBuilder = DataSourceBuilder.create();
//		dsBuilder.driverClassName(databaseDriver);
//		dsBuilder.url(databaseUrl);
//		dsBuilder.username(databaseUsername);
//		dsBuilder.password(databaseMima);
//
//		return dsBuilder.build();
//	}
//
//	/**
//	 * @return LocalContainerEntityManagerFactoryBean
//	 * @throws SQLException 
//	 */
//	@Primary
//	@Bean(name = "entityManagerFactory")
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory (@Qualifier("dataSourceMSSQL") DataSource ds)
//	{
//		dataSource = ds;
//
//		final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean () ;
//		factory.setDataSource ( dataSource ) ;
//		factory.setJpaDialect ( new HibernateJpaDialect () ) ;
//	    factory.setJpaPropertyMap ( Collections.singletonMap ( "javax.persistence.validation.mode", "none" ) ) ;
//	    factory.setPersistenceProvider ( new HibernatePersistenceProvider () ) ;
//	    factory.setPackagesToScan ( new String [] { ENTITY_PACKAGE } ) ;
//
//	    
//	    factory.setJpaVendorAdapter ( jpaVendorAdapter () ) ;
//	    factory.setJpaProperties ( hibernateProperties () ) ;
//
//	    factory.afterPropertiesSet () ;
//	    
//	    factory.setLoadTimeWeaver ( new InstrumentationLoadTimeWeaver () ) ;
//	    
//	    return factory ;
//	}
//	
//	/**
//	 * @return PlatformTransactionManager
//	 */
//	@Primary
//	@Bean(name = "transactionManager")
//	public PlatformTransactionManager transactionManager (@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory)
//	{
//		return new JpaTransactionManager(entityManagerFactory);
//    }
//	
//	/**
//	 * @return PersistenceExceptionTranslationPostProcessor
//	 */
//	@Bean
//	public PersistenceExceptionTranslationPostProcessor exceptionTranslation ()
//	{
//		return new PersistenceExceptionTranslationPostProcessor () ;
//	}
//	
//	/**
//	 * @return SQLErrorCodeSQLExceptionTranslator
//	 */
//	@Bean
//	public SQLErrorCodeSQLExceptionTranslator jdbcExceptionTranslator ()
//	{
//		SQLErrorCodeSQLExceptionTranslator sqlExceptionTranslator = new SQLErrorCodeSQLExceptionTranslator () ;
//		sqlExceptionTranslator.setDataSource ( dataSource ) ;
//		return sqlExceptionTranslator ;
//	}
//	
//	/**
//	 * @return AbstractJpaVendorAdapter
//	 */
//	private AbstractJpaVendorAdapter jpaVendorAdapter ()
//	{
//		
//		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter () ;
//
//		if(log.isDebugEnabled()) {
//			log.debug("** spring.jpa.database-platform: " + env.getProperty("spring.jpa.database-platform"));
//			log.debug("** spring.jpa.generate-ddl: " + env.getProperty("spring.jpa.generate-ddl", Boolean.class));
//			log.debug("** spring.jpa.show-sql: " + env.getProperty("spring.jpa.show-sql", Boolean.class));
//		}
//
//		jpaVendorAdapter.setDatabasePlatform ( env.getProperty ( "spring.jpa.database-platform" ) ) ;
//		jpaVendorAdapter.setGenerateDdl ( env.getProperty ( "spring.jpa.generate-ddl", Boolean.class ) ) ;
//		jpaVendorAdapter.setShowSql ( env.getProperty ( "spring.jpa.show-sql", Boolean.class ) ) ;
//		
//		return jpaVendorAdapter ;
//		
//	}
//	
//	/**
//	 * @return Properties
//	 */
//	private Properties hibernateProperties ()
//	{
//		
//		Properties properties = new Properties () ;
//		
//		properties.setProperty ( "javax.persistence.provider", "org.hibernate.jpa.HibernatePersistenceProvider" ) ;
//		properties.setProperty ( "hibernate.dialect", env.getProperty ( "spring.jpa.properties.hibernate.dialect" ) ) ;
//		properties.setProperty ( "hibernate.hbm2ddl.auto", env.getProperty ( "spring.jpa.hibernate.ddl-auto" ) ) ;
//		properties.setProperty ( "hibernate.show_sql", env.getProperty ( "spring.jpa.show-sql" ) ) ;
//		properties.setProperty ( "hibernate.format_sql", env.getProperty ( "spring.jpa.properties.hibernate.format_sql" ) ) ;
//		properties.setProperty ( "hibernate.current_session_context_class", env.getProperty ( "spring.jpa.properties.hibernate.current_session_context_class" ) ) ;
//		properties.setProperty ( "hibernate.jdbc.lob.non_contextual_creation", env.getProperty ( "spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation" ) ) ;
//
//		if(log.isDebugEnabled()) {
//			log.debug("** jpaProperties: " + jpaProperties.getProperties ());
//		}
//
//		properties.putAll ( jpaProperties.getProperties () ) ;
//		
//		return properties ;
//		
//	}
//	
//}

