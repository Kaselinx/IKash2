package com.taiwanlife.ikash.backend.aop.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.core.Ordered ;
import org.springframework.core.annotation.Order ;
import org.springframework.http.HttpHeaders ;
import org.springframework.http.HttpStatus ;
import org.springframework.stereotype.Controller ;
import org.springframework.web.bind.annotation.RestController ;
import org.springframework.web.context.request.ServletWebRequest ;
import org.springframework.web.server.ResponseStatusException ;
import org.springframework.web.servlet.mvc.AbstractController ;

import java.util.Map;

import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;


import lombok.extern.slf4j.Slf4j;

@ControllerAdvice ( assignableTypes = AbstractController.class, annotations = { Controller.class, RestController.class } )
@Order ( Ordered.HIGHEST_PRECEDENCE )
@Slf4j
public class BaseWebControllerAdvice extends AbstractWebControllerAdvice {
	/**
	 * Constructor
	 * @param exceptionErrorAttributesHandler
	 */
	public BaseWebControllerAdvice ( ExceptionErrorAttributesHandler exceptionErrorAttributesHandler )
	{
		super ( exceptionErrorAttributesHandler ) ;
		log.info ( "**** BaseWebControllerAdvice : {}", this.getClass ().getName () ) ;
	}
	
	/**
	 * @param webRequest
	 * @param exception
	 * @return Object
	 */
	@ExceptionHandler ( ResponseStatusException.class )
	protected Object handleResponseStatusExceptionValid ( WebRequest webRequest, ResponseStatusException exception )
	{
		return handleExceptionInternalAdapter ( webRequest, null, null, exception, null, null ) ;
	}
	
	
	@SuppressWarnings ( "unchecked" )
	@Override
	protected <T, U> T handleExceptionInternalAdapter (
			WebRequest webRequest,
			HttpHeaders httpHeaders,
			HttpStatus httpStatus,
			Throwable throwable,
			U body,
			Object extraMessage )
	{
		
		ServletWebRequest servletWebRequest = ( ServletWebRequest ) webRequest;
	      
	    HttpServletRequest request = servletWebRequest.getRequest () ;
	    HttpServletResponse response = servletWebRequest.getResponse () ;
		
	    HttpHeaders headers = ( httpHeaders != null ? httpHeaders : new HttpHeaders () ) ;
	    	    
		Map <String,Object> bodyMap = getExceptionErrorAttributesHandler ( request, httpStatus, extraMessage ) ;
		
	    HttpStatus status = HttpStatus.valueOf ( ( int ) bodyMap.get ( "code" ) ) ;
	    
	    Object responseBody = ( body != null ? body : bodyMap ) ;
			    
		return (T) new JsonErrorResponseImpl ().buildErrorResponse ( request, response, headers, status, responseBody ) ;
		
	}
}
