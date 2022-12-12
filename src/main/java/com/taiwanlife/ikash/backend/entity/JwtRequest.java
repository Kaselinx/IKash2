package com.taiwanlife.ikash.backend.entity;
import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtRequest  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5480232794954853946L;
	private String username;
	private String password;
}
