package com.taiwanlife.ikash.backend.configuration;
import javax.servlet.Servlet ;

import org.springframework.boot.autoconfigure.AutoConfigureBefore ;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean ;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass ;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean ;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication ;
import org.springframework.boot.autoconfigure.condition.SearchStrategy ;
import org.springframework.boot.autoconfigure.web.WebProperties ;
import org.springframework.boot.autoconfigure.web.ServerProperties ;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration ;
import org.springframework.boot.context.properties.EnableConfigurationProperties ;
import org.springframework.boot.web.servlet.error.ErrorAttributes ;
import org.springframework.context.annotation.Bean ;
import org.springframework.context.annotation.Configuration ;
import org.springframework.context.annotation.DependsOn ;
import org.springframework.context.annotation.Lazy ;
import org.springframework.context.annotation.Primary ;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.DispatcherServlet;
import com.taiwanlife.ikash.backend.aop.exception.ExceptionErrorAttributes;


import com.taiwanlife.ikash.backend.aop.exception.ExceptionErrorAttributesHandler;

import lombok.extern.slf4j.Slf4j;


@Configuration ( proxyBeanMethods = false )
@ConditionalOnWebApplication ( type = ConditionalOnWebApplication.Type.SERVLET )
@ConditionalOnClass( { Servlet.class, DispatcherServlet.class } )
@AutoConfigureBefore ( WebMvcAutoConfiguration.class )
@EnableConfigurationProperties ( { ServerProperties.class, WebProperties.class } )
@Slf4j
public class ErrorHandlerConfig {
	private final ServerProperties serverProperties ;
	/**
	 * Constructor
	 * @param serverProperties
	 */
	public ErrorHandlerConfig ( ServerProperties serverProperties )
	{
        this.serverProperties = serverProperties ;
		log.info ( "**** ErrorHandlerConfig : {}", this.getClass ().getName () ) ;
	}
	
	/**
	 * @return
	 */
	@ConditionalOnMissingBean ( value = ErrorAttributes.class, search = SearchStrategy.CURRENT )
	@Primary
	@Bean
	@Order ( Ordered.HIGHEST_PRECEDENCE )
	public ErrorAttributes errorAttributes ()
	{
		return new ExceptionErrorAttributes ( this.serverProperties.getError ().isIncludeException () ) ;
	}
	
	/**s
	 * @param errorAttributes
	 * @return
	 */
	@ConditionalOnBean ( value = ErrorAttributes.class, search = SearchStrategy.CURRENT )
	@DependsOn ( "errorAttributes" )
	@Lazy
	@Bean
	public ExceptionErrorAttributesHandler exceptionErrorAttributesHandler ( ErrorAttributes errorAttributes )
	{
		return new ExceptionErrorAttributesHandler ( errorAttributes, this.serverProperties.getError () ) ;
	}
}
