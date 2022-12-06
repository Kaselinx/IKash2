package com.taiwanlife.ikash.backend.entity.acs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="ACT_FYC_ACCEPTFEATS")
@IdClass(value=ACT_FYC_ACCEPTFEATSKEY.class)
public class ACT_FYC_ACCEPTFEATS implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
	private Date APPL_RECE_DATE;
	private String DIRECTOR_ID;	
	private String DIRECTOR_CODE;	
	@Id
	private String USER_CODE;	
	private double FYC;	
	private double AVG;	
	private int  RANK;
	private int LEVEL_COUNT	;
	private String DIRECTOR_YN;	
	private String AGENT_NAME;
	private Date CREATE_DT;	
	private String CREATE_BY;	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
	private Date MODIFY_DT;	
	private String MODIFY_BY;	
	private String COMM_LINE_CODE;	
	private int COMMLINE_LEVEL_COUNT;
	
//	@ManyToOne
//	@JoinColumns({
//		  @JoinColumn(name = "DIRECTOR_CODE", insertable = false, updatable = false),
//		  @JoinColumn(name = "APPL_RECE_DATE", insertable = false, updatable = false)
//		})
// 
//	private ACT_TEAM_FYC_ACCEPTFEATS ACT_TEAM_FYC_ACCEPTFEATS;
}
