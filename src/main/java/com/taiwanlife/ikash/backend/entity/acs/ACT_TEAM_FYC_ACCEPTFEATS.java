package com.taiwanlife.ikash.backend.entity.acs;

import java.util.Date;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="ACT_TEAM_FYC_ACCEPTFEATS")
@IdClass(value=ACT_TEAM_FYC_ACCEPTFEATSKEY.class)
public class ACT_TEAM_FYC_ACCEPTFEATS implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8675828788566410422L;
	@Id
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
	private Date APPL_RECE_DATE;	
	@Id
	private String DIRECTOR_CODE;	
	private double TEAM_FYC;	
	private double TEAM_AVG;	
	private int DIRECTOR_RANK;	
	private int DIRECTOR_COUNT;	
	private Date CREATE_DT;	
	private String CREATE_BY;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
	private Date MODIFY_DT;	
	private String MODIFY_BY;	
	private int COMM_LINE_CODE;
}
