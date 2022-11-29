package com.taiwanlife.ikash.backend.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor
     * @param message
     */
    public TokenException ( String message ) {
        super ( message ) ;
    }
}
