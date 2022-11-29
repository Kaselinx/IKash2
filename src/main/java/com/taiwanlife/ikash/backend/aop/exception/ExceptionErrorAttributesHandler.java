package com.taiwanlife.ikash.backend.aop.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes ;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.ServletWebRequest;


import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ExceptionErrorAttributesHandler {
	private final static String PARAMETER_TEACE = "trace";
	private final static String PARAMETER_ERRORS = "errors";

	private final ErrorAttributes errorAttributes ;
	private final ErrorProperties errorProperties ;
	
	/**
	 * Constructor
	 * @param errorAttributes
	 * @param errorProperties
	 */

	public ExceptionErrorAttributesHandler ( ErrorAttributes errorAttributes, ErrorProperties errorProperties )
	{
		super () ;
		
		this.errorAttributes = errorAttributes ;
		
		this.errorProperties = errorProperties ;
		
		log.info ( "**** Start Exception Error Attributes Handler : {}", this.getClass ().getName () ) ;
		
	}
	
	/**
	 * @param httpServletRequest
	 * @return
	 */

	public Map<String, Object> getErrorAttributes ( HttpServletRequest httpServletRequest )
	{
		WebRequest webRequest = new ServletWebRequest ( httpServletRequest ) ;
		return errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
	}
	
	/**
	 * Determine if the stacktrace attribute should be included.
	 * @param request the source request
	 * @param produces the media type produced (or {@code MediaType.ALL})
	 * @return if the stacktrace attribute should be included
	 */
	@SuppressWarnings("unused")
	private boolean isIncludeStackTrace ( HttpServletRequest request, MediaType produces )
	{
		
		switch ( errorProperties.getIncludeStacktrace () )
		{
			
			case ALWAYS :
			{
				return true ;
			}
			case ON_PARAM :
			{
				return isTraceEnabled ( request ) ;
			}
			default :
			{
				return false ;
			}
			
		}
		
	}
	
	/**
	 * Determine if the errors attribute should be included.
	 * @param request the source request
	 * @param produces the media type produced (or {@code MediaType.ALL})
	 * @return if the errors attribute should be included
	 */
	protected boolean isIncludeBindingErrors ( HttpServletRequest request, MediaType produces )
	{
		switch ( errorProperties.getIncludeBindingErrors () )
		{
			
			case ALWAYS :
			{
				return true ;
			}
			case ON_PARAM :
			{
				return isErrorsEnabled ( request ) ;
			}
			default :
			{
				return false ;
			}
			
		}
		
	}
	
	/**
	 * Check whether the trace attribute has been set on the given request.
	 * @param request the source request
	 * @return {@code true} if the error trace has been requested, {@code false} otherwise
	 */
	private boolean isTraceEnabled ( HttpServletRequest request ){
		String parameter = request.getParameter ( PARAMETER_TEACE );

		if(parameter == null) {
			return false;
		} else if(!parameter.equalsIgnoreCase("false")){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Check whether the errors attribute has been set on the given request.
	 * @param request the source request
	 * @return {@code true} if the error errors has been requested, {@code false} otherwise
	 */
	private boolean isErrorsEnabled ( HttpServletRequest request ){
		//String parameter = Optional.ofNullable ( request.getParameter ( PARAMETER_ERRORS ) ).orElse ( "false" ) ;

		String parameter = request.getParameter ( PARAMETER_ERRORS );

		if(parameter == null) {
			return false;
		}

		return !"false".equalsIgnoreCase ( parameter ) ;
	}

}
