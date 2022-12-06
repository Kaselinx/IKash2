package com.taiwanlife.ikash.backend.service;

import java.util.Date;
import java.util.List;

import com.taiwanlife.ikash.backend.entity.acs.ACT_FYC_ACCEPTFEATS;

public interface SalesAchievementService {
	List<ACT_FYC_ACCEPTFEATS> findAll();
	
	List<ACT_FYC_ACCEPTFEATS> findByUSER_CODE(String USER_CODE);
	
	List<ACT_FYC_ACCEPTFEATS> findByAGENT_NAMELike(String AGENT_NAME);
	
	List<ACT_FYC_ACCEPTFEATS> findMonthlyKYC(String USER_CODE,  Date RECE_DATE );
}
