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
public class ACT_TEAM_FYC_ACCEPTFEATSKEY implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4998615499106507053L;

	private Date APPL_RECE_DATE;	

	private String DIRECTOR_CODE;	
}
