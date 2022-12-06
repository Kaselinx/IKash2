package com.taiwanlife.ikash.backend.entity.acs;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ACT_FYC_ACCEPTFEATSKEY implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4688648181810273274L;

	private Date APPL_RECE_DATE;	

	private String USER_CODE;	
}
