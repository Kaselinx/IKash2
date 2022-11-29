package com.taiwanlife.ikash.backend.aop.exception;

import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;

import org.springframework.http.HttpHeaders ;
import org.springframework.http.HttpStatus ;


public interface WebErrorResponse <T>
{
	
	/**
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param httpHeaders
	 * @param httpStatus
	 * @param body
	 * @return
	 */
	T buildErrorResponse ( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpHeaders httpHeaders, HttpStatus httpStatus, Object body ) ;
	
}
