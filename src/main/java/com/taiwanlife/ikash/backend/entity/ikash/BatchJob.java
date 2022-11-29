package com.taiwanlife.ikash.backend.entity.ikash;

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
@Entity
@NoArgsConstructor
@ToString
@Table(name="BatchJob")
public class BatchJob {
	@Id
	private String JobID;
	@Column
	private String JobName;	
	@Column
	private String CnnVar4Qry;	
	@Column
	private String CnnVar4Batch;	
	@Column
	private int IsRunning;
	@Column
	private Date ModifyTime;	
	@Column
	private String IP;
}
