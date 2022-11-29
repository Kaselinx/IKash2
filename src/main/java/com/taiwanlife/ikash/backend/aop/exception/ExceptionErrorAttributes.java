package com.taiwanlife.ikash.backend.aop.exception;

import java.io.PrintWriter ;
import java.io.StringWriter ;
import java.time.LocalDateTime ;
import java.util.LinkedHashMap ;
import java.util.Map ;
import java.util.Optional ;

import javax.servlet.RequestDispatcher ;
import javax.servlet.ServletException ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes ;
import org.springframework.core.Ordered ;
import org.springframework.core.annotation.MergedAnnotation ;
import org.springframework.core.annotation.MergedAnnotations ;
import org.springframework.core.annotation.Order ;
import org.springframework.http.HttpStatus ;
import org.springframework.util.StringUtils ;
import org.springframework.validation.BindingResult ;
import org.springframework.web.bind.MethodArgumentNotValidException ;
import org.springframework.web.bind.annotation.ResponseStatus ;
import org.springframework.web.context.request.NativeWebRequest ;
import org.springframework.web.context.request.RequestAttributes ;
import org.springframework.web.context.request.WebRequest ;
import org.springframework.web.server.ResponseStatusException ;
import org.springframework.web.servlet.ModelAndView ;
import org.springframework.web.util.WebUtils ;

import lombok.extern.slf4j.Slf4j ;


/**
 * @author Seimo.Zhan
 * @version : 1
 *
 * @date 2020/08/17
 */
@Order ( Ordered.HIGHEST_PRECEDENCE )
@Slf4j
public class ExceptionErrorAttributes extends DefaultErrorAttributes
{
	
	private static final String ERROR_ATTRIBUTE = ExceptionErrorAttributes.class.getName () + ".ERROR";
	
	private final boolean includeException ;
	
	/**
	 * Constructor
	 */
	public ExceptionErrorAttributes ()
	{
		this ( false ) ;
		log.info ( "**** ExceptionErrorAttributes : {}", this.getClass ().getName () ) ;
	}
	
	/**
	 * Constructor
	 * @param includeException
	 */
	public ExceptionErrorAttributes ( boolean includeException )
	{
		this.includeException = includeException ;
	}
	

	@Deprecated
	public Map <String, Object> getErrorAttributes ( WebRequest webRequest, boolean includeStackTrace )
	{
		
		NativeWebRequest nativeWebRequest = ( NativeWebRequest ) webRequest ;
		
        HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest ( HttpServletRequest.class ) ;
        
        Throwable error = getError ( webRequest ) ;
        
        HttpStatus httpStatus = determineHttpStatus ( webRequest, error ) ;
        String message = determineMessage ( webRequest, error ) ;
        
        Throwable exception = determineException ( webRequest, error ) ;
        Map < String, Object > errorAttributes = new LinkedHashMap <> () ;
        
        errorAttributes.put ( "timestamp", LocalDateTime.now () ) ;
        errorAttributes.put ( "code", httpStatus.value () ) ;
        errorAttributes.put ( "status", httpStatus.getReasonPhrase () ) ;
//        errorAttributes.put ( "requestId", httpServletRequest.getRequestedSessionId () ) ;
        errorAttributes.put ( "protocol", httpServletRequest.getProtocol () ) ;
        errorAttributes.put ( "method", httpServletRequest.getMethod () ) ;
        errorAttributes.put ( "url", httpServletRequest.getRequestURL ().toString () ) ;
//        errorAttributes.put ( "uri", getUri ( httpServletRequest ) ) ;
        errorAttributes.put ( "path", getUri ( httpServletRequest ) ) ;
//        errorAttributes.put ( "path", ( getPath ( httpServletRequest ) != null ? getPath ( httpServletRequest ) : getUri ( httpServletRequest ) ) ) ;
        errorAttributes.put ( "errorClass", exception.getClass () ) ;
        errorAttributes.put ( "message", message ) ;
        errorAttributes.put ( "extraMessage", null ) ;
        
        handleException ( errorAttributes, exception, includeStackTrace ) ;
        
        return errorAttributes ;
        
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.boot.web.servlet.error.DefaultErrorAttributes#getError(org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public Throwable getError ( WebRequest webRequest )
	{
		
		Throwable error = ( Throwable ) Optional.ofNullable ( getAttribute ( webRequest, ERROR_ATTRIBUTE ) )
				.orElseGet ( () -> {
					
					Throwable exception = getAttribute ( webRequest, RequestDispatcher.ERROR_EXCEPTION ) ;
					
					if ( exception != null )
					{
						return exception ;
					}
					else
					{
						return new IllegalStateException ( "Missing exception attribute in WebRequest ! Unrecognizable exception class." ) ;
					}
					
		} ) ;
		
		return error ;
		
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public ModelAndView resolveException ( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex )
	{
		storeErrorAttributes ( request, ex ) ;
		return null ;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	@Override
	public int getOrder ()
	{
		// 0x80000000 = -2147483648
		return Ordered.HIGHEST_PRECEDENCE ;
	}
	
	/**
	 * @param webRequest
	 * @param error
	 * @return
	 */
	private Throwable determineException ( WebRequest webRequest, Throwable error )
	{
		
		if ( error instanceof ResponseStatusException )
		{
			return ( error.getCause () != null ) ? error.getCause () : error ;
		}
		
		while ( ( error instanceof ServletException ) && ( error.getCause () != null ) )
		{
			error = error.getCause () ;
		}
				
		return Optional.ofNullable ( error ).orElseThrow ( () -> new IllegalStateException ( "Missing exception attribute in WebRequest" ) ) ;
				
	}
	
	/**
	 * @param webRequest
	 * @param error
	 * @return
	 */
	private HttpStatus determineHttpStatus ( WebRequest webRequest, Throwable error )
	{
		
		Integer statusCode = getAttribute ( webRequest, RequestDispatcher.ERROR_STATUS_CODE ) ;
		
		if ( statusCode != null )
		{
			return HttpStatus.valueOf ( statusCode ) ;
		}
		
		MergedAnnotation < ResponseStatus > responseStatusAnnotation = null ;
		
		if ( error instanceof ResponseStatusException )
		{
			
			Throwable exception = error.getCause () ;
						
			if ( exception != null )
			{
				responseStatusAnnotation = checkResponseStatusAnnotation ( exception ) ;
				return responseStatusAnnotation.getValue ( "code", HttpStatus.class ).orElse ( ( ( ResponseStatusException ) error ).getStatus () ) ;
			}
			else
			{
				return ( ( ResponseStatusException ) error ).getStatus () ;
			}
			
		}
		
		responseStatusAnnotation = checkResponseStatusAnnotation ( error ) ;
		
		return responseStatusAnnotation.getValue ( "code", HttpStatus.class ).orElse ( HttpStatus.INTERNAL_SERVER_ERROR ) ;		
		
	}
	
	/**
	 * @param webRequest
	 * @param error
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private String determineMessage ( WebRequest webRequest, Throwable error )
	{
		
		BindingResult result = extractBindingResult ( error ) ;
		
		if ( result != null )
		{
			return "Validation failed for object = '" + result.getObjectName () + "'. " + "Error count: " + result.getErrorCount () ;
		}
		
		String message = getAttribute ( webRequest, RequestDispatcher.ERROR_MESSAGE ) ;
		
		if ( StringUtils.hasText ( message ) )
		{
			return message ;
		}
		
		MergedAnnotation < ResponseStatus > responseStatusAnnotation = null ;
		
		if ( error instanceof ResponseStatusException )
		{
			
			Throwable exception = error.getCause () ;
			
			if ( exception != null )
			{
				
				responseStatusAnnotation = checkResponseStatusAnnotation ( exception ) ;
				
				Optional < String > reason = responseStatusAnnotation.getValue ( "reason", String.class ) ;
				
				if ( reason.isPresent () )
				{
					return reason.get () ;
				}
				
				if ( ! StringUtils.isEmpty ( exception.getMessage () ) )
				{
					return exception.getMessage () ;
				}
				
			}
			
			String responseReason = ( ( ResponseStatusException ) error ).getReason () ;
			
			if ( StringUtils.isEmpty ( responseReason ) )
			{
				return "ResponseStatusException has no messages available" ;
			}
			
			return responseReason ;
			
		}
		
		// Use @ResponseStatus annotation for all exceptions
		responseStatusAnnotation = checkResponseStatusAnnotation ( error ) ;
		
		return responseStatusAnnotation.getValue ( "reason", String.class ).orElseGet ( () -> {
			
			String reason = error.getMessage () ;
			
			if ( StringUtils.isEmpty ( reason ) )
			{
				return "No message available" ;
			}
			else
			{
				return reason ;
			}
			
		} ) ;
		
	}
	
	/**
	 * @param error
	 * @return
	 */
	private BindingResult extractBindingResult ( Throwable error )
	{
		
		if ( error instanceof BindingResult )
		{
			return ( BindingResult ) error ;
		}
		
		if ( error instanceof MethodArgumentNotValidException )
		{
			return ( ( MethodArgumentNotValidException ) error ).getBindingResult () ;
		}
		
		return null ;
		
	}
	
	/**
	 * @param errorAttributes
	 * @param error
	 * @param includeStackTrace
	 */
	private void handleException ( Map < String, Object > errorAttributes, Throwable error, boolean includeStackTrace )
	{
		
		if ( this.includeException )
		{
			errorAttributes.put ( "exception", error.getClass () ) ;
		}
		
		if ( includeStackTrace )
		{
			errorAttributes.put ( "trace", addStackTrace ( error ).getBuffer ().toString () ) ;
		}
		
		BindingResult result = extractBindingResult ( error ) ;
		
		if ( result != null && result.hasErrors () )
		{
			errorAttributes.put ( "errors", result.getAllErrors () ) ;
		}
		
	}
	
	/**
	 * @param throwable
	 * @return
	 */
	private MergedAnnotation < ResponseStatus > checkResponseStatusAnnotation ( Throwable throwable )
	{
		return MergedAnnotations.from ( throwable.getClass (), MergedAnnotations.SearchStrategy.TYPE_HIERARCHY ).get ( ResponseStatus.class ) ;
	}
	
	/**
	 * @param request
	 * @param ex
	 */
	public void storeErrorAttributes ( HttpServletRequest request, Exception ex )
	{
		request.setAttribute ( ERROR_ATTRIBUTE, ex ) ;
	}
	
	/**
	 * @param request
	 * @return
	 */
	@SuppressWarnings ( "unused" )
	private String getUri ( HttpServletRequest request )
	{
		String uri = ( String ) request.getAttribute ( WebUtils.ERROR_REQUEST_URI_ATTRIBUTE ) ;
		if ( StringUtils.isEmpty ( uri ) ) return request.getRequestURI () ;
		return uri ;
	}
	
	/**
	 * @param request
	 * @return
	 */
	@SuppressWarnings ( "unused" )
	private String getPath ( HttpServletRequest request )
	{
		
		String servletPath = request.getServletPath () ;
//		String path = request.getPathInfo () ;
		
		return servletPath ;
		
	}
	
	/**
	 * @param <T>
	 * @param requestAttributes
	 * @param name
	 * @return
	 */
	@SuppressWarnings ( "unchecked" )
	private < T > T getAttribute ( RequestAttributes requestAttributes, String name )
	{
		return ( T ) requestAttributes.getAttribute ( name, RequestAttributes.SCOPE_REQUEST ) ;
	}
	
	/**
	 * @param error
	 * @return
	 */
	private StringWriter addStackTrace ( Throwable error )
	{
		
		StringWriter stackTrace = new StringWriter () ;
		
		error.printStackTrace ( new PrintWriter ( stackTrace ) ) ;
		
		stackTrace.flush () ;
		
		return stackTrace ;
		
	}
	
}
