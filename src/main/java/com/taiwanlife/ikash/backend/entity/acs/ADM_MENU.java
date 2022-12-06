package com.taiwanlife.ikash.backend.entity.acs;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity(name="admmenus")
@NoArgsConstructor
@ToString
@Table(name="ADM_MENUS")
public class ADM_MENU {
	@Id
	@Column(name = "MENU_CODE", unique=true, columnDefinition="VARCHAR(10)")
	private String MENU_CODE;
	@Column
	private String PROG_CODE;	
	
	@Column(name = "MENU_NAME", columnDefinition = "NVARCHAR(60)")
	private String MENU_NAME;	
	
	@Column(name = "MENU_ENAME", columnDefinition = "NVARCHAR(120)")
	private String MENU_ENAME;	
	
	@Column(name = "DESCRIPTION", columnDefinition = "NVARCHAR(100)")
	private String DESCRIPTION;	
	
	@Column
	private String PARENT_MENU_CODE;
	@Column
	private int DEPTHS;	
	@Column
	private String STRSET;	
	@Column
	private String SHOW_YN;	
	
	@Column
	private int SHOW_ORDER;	
	
	@Column
	private String TAB_YN;	
	
	@Column
	private String ADM_YN;	
	
	@Column(name = "CREATE_DT", columnDefinition = "DATE")
	//@Temporal(TemporalType.DATE)
	private Date CREATE_DT;	
	
	@Column
	private String CREATE_BY;	
	
	@Column(name = "MODIFY_DT", columnDefinition = "DATE")
	//@Temporal(TemporalType.DATE)
	private Date MODIFY_DT;	
	
	@Column
	private String MODIFY_BY;


}
