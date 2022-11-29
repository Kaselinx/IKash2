package com.taiwanlife.ikash.backend.aop.exception;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;

import org.springframework.http.HttpHeaders ;
import org.springframework.http.HttpStatus ;
import org.springframework.http.MediaType ;
import org.springframework.http.ResponseEntity ;

public class JsonErrorResponseImpl implements WebErrorResponse < ResponseEntity<?>> {
	/**
	 * Constructor
	 */
	public JsonErrorResponseImpl ()
	{
		super  () ;
	}
	
	@Override
	public ResponseEntity <?> buildErrorResponse  (
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			HttpHeaders httpHeaders,
			HttpStatus httpStatus,
			Object body )
	{
		
		return ResponseEntity
	            .status ( httpStatus )
	            .contentType ( MediaType.APPLICATION_JSON )
	            .body ( body ) ;
		
	}

}
