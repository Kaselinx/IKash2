package com.taiwanlife.ikash.backend.service.Imp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taiwanlife.ikash.backend.entity.acs.ACT_FYC_ACCEPTFEATS;
import com.taiwanlife.ikash.backend.repository.SalesAchievementRepository;
import com.taiwanlife.ikash.backend.service.SalesAchievementService;

@Service
public class SalesAchievementServiceImpl implements SalesAchievementService{
	
	private SalesAchievementRepository salesAchievementRepository;
	@Autowired
	public SalesAchievementServiceImpl(SalesAchievementRepository ThesalesAchievementRepository) {
		this.salesAchievementRepository = ThesalesAchievementRepository;
	}

	@Override
	public List<ACT_FYC_ACCEPTFEATS> findAll() {
		return salesAchievementRepository.findAll();
	}

	@Override
	public List<ACT_FYC_ACCEPTFEATS> findByUSER_CODE(String USER_CODE) {
		return salesAchievementRepository.findByUSER_CODE(USER_CODE);
	}

	@Override
	public List<ACT_FYC_ACCEPTFEATS> findByAGENT_NAMELike(String AGENT_NAME) {
		return salesAchievementRepository.findByAGENT_NAMELike(AGENT_NAME);
	}

	@Override
	public List<ACT_FYC_ACCEPTFEATS> findMonthlyKYC(String USER_CODE, Date RECE_DATE) {
		return salesAchievementRepository.findMonthlyKYC(USER_CODE,RECE_DATE);
	}

	@Override
	public List<Map<String,Object>>  findJoinKYC() {
		return salesAchievementRepository.findJoinKYC();
	}


}
