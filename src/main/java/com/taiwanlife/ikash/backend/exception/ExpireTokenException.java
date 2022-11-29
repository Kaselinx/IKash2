package com.taiwanlife.ikash.backend.exception;

import org.springframework.security.core.AuthenticationException;

public class ExpireTokenException extends AuthenticationException {

	/**
	 * default id
	 */
	private static final long serialVersionUID = 6171059653240102958L;

	public ExpireTokenException(String msg) {
		super( ( "Token has expired !" ) );
		// TODO Auto-generated constructor stub
	}

	 public ExpireTokenException ( String message, Throwable cause ){
	        super ( message, cause ) ;   
	 }
}
