package com.taiwanlife.ikash.backend.entity;
import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class JwtResponse implements Serializable{
	private final String jwttoken;
	public JwtResponse(String token) {
		this.jwttoken = token;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 7923558909972689444L;
}
