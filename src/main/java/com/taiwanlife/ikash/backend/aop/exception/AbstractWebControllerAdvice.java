package com.taiwanlife.ikash.backend.aop.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.AbstractController;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils ;
import org.springframework.http.HttpHeaders ;
import org.springframework.http.HttpStatus ;
import org.springframework.http.ResponseEntity ;
import org.springframework.lang.Nullable ;
import org.springframework.web.bind.annotation.ResponseStatus ;
import org.springframework.web.context.request.WebRequest ;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler ;
import org.springframework.web.util.WebUtils ;

@ControllerAdvice ( assignableTypes = AbstractController.class )
@Slf4j
public abstract class AbstractWebControllerAdvice extends ResponseEntityExceptionHandler {
	private final ExceptionErrorAttributesHandler exceptionErrorAttributesHandler ;
	/**
	 * Constructor
	 * 
	 * HTTP status codes : informational (100-199) successful (200-299)
	 * redirection (300-399) client error (400-499) server error (500-599)
	 * 
	 * View Page : https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
	 * 
	 * @param exceptionErrorAttributesHandler
	 */
	protected AbstractWebControllerAdvice ( ExceptionErrorAttributesHandler exceptionErrorAttributesHandler )
	{
		super () ;
		this.exceptionErrorAttributesHandler = exceptionErrorAttributesHandler ;
		log.info ( "**** AbstractWebControllerAdvice : {}", this.getClass ().getName () ) ;
	}
	
	/**
	 * A single place to customize the response body of all exception types.
	 * <p>The default implementation sets the {@link WebUtils#ERROR_EXCEPTION_ATTRIBUTE}
	 * request attribute and creates a {@link ResponseEntity} from the given
	 * body, headers, and status.
	 * @param ex the exception
	 * @param body the body for the response
	 * @param headers the headers for the response
	 * @param status the response status
	 * @param request the current request
	 * @return ResponseEntity < Object >
	 */
	@Override
	protected ResponseEntity <Object> handleExceptionInternal (
			Exception ex,
			@Nullable Object body,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request )
	{
		return handleExceptionInternalAdapter ( request, headers, status, ex, body, null ) ;
	}
	
	/**
	 * @param <T>
	 * @param <U>
	 * @param webRequest
	 * @param httpHeaders
	 * @param httpStatus
	 * @param throwable
	 * @param body
	 * @param extraMessage
	 * @return
	 */
	protected abstract < T, U > T handleExceptionInternalAdapter ( WebRequest webRequest, HttpHeaders httpHeaders, HttpStatus httpStatus, Throwable throwable, @Nullable U body, @Nullable Object extraMessage ) ;
	
	/**
	 * @param request
	 * @param httpStatus
	 * @param extraMessage
	 * @return Map < String, Object >
	 */
	protected Map <String, Object> getExceptionErrorAttributesHandler ( HttpServletRequest request, @Nullable HttpStatus httpStatus, @Nullable Object extraMessage )
	{
		
	    Map <String,Object> responseMap = exceptionErrorAttributesHandler.getErrorAttributes ( request ) ;
	    
	    if ( httpStatus != null )
	    {
	    	responseMap.put ( "code", httpStatus.value () ) ;
	    }
	    
	    responseMap.putIfAbsent ( "extraMessage", extraMessage ) ;
	    
	    return responseMap ;
	    
	}
	
	/**
	 * @param throwable
	 * @return HttpStatus
	 */
	protected HttpStatus handleHttpStatus ( Throwable throwable )
	{
		return handleHttpStatus ( throwable, null ) ;
	}
	
	/**
	 * @param throwable
	 * @param httpStatus
	 * @return HttpStatus
	 */
	protected HttpStatus handleHttpStatus ( Throwable throwable, HttpStatus httpStatus )
	{
		
		if ( httpStatus != null )
		{
			return HttpStatus.valueOf ( httpStatus.value () ) ;
		}
		else
		{
			ResponseStatus repStatusAnnotation = AnnotationUtils.findAnnotation ( throwable.getClass (), ResponseStatus.class ) ;
			return repStatusAnnotation != null ? repStatusAnnotation.value () : HttpStatus.INTERNAL_SERVER_ERROR ;
		}
		
	}
	
	/**
	 * @param throwable
	 * @return StringWriter
	 */
	protected StringWriter printBesideException ( Throwable throwable )
	{
		
		StringWriter sw = new StringWriter () ;
		
		PrintWriter pw = new PrintWriter ( sw, true ) ;
		
		throwable.printStackTrace ( pw ) ;
				
		return sw ;
		
	}
	
	/**
	 * @param throwable
	 * @return String
	 */
	protected String getStackTraceMessage ( Throwable throwable )
	{
        return printBesideException ( throwable ).getBuffer ().toString () ;
	}
	
}
