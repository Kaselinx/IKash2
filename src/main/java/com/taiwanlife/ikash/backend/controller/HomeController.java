package com.taiwanlife.ikash.backend.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taiwanlife.ikash.backend.entity.acs.ACT_FYC_ACCEPTFEATS;
import com.taiwanlife.ikash.backend.service.SalesAchievementService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/HOME")
public class HomeController {
	private SalesAchievementService salesAchievementRepository;
	
	
	@Autowired
	public HomeController(SalesAchievementService ThesalesAchievementRepository) {
		this.salesAchievementRepository = ThesalesAchievementRepository; 
	}
	
	@GetMapping("/Find_ALL_ACCEPTFEATS")
	public Optional<List<ACT_FYC_ACCEPTFEATS>>  FindAll() {
		Optional<List<ACT_FYC_ACCEPTFEATS>> tList = Optional.ofNullable(salesAchievementRepository.findAll());
		return tList;
	}
	
	@PostMapping("/USER_CODE")
	public Optional<List<ACT_FYC_ACCEPTFEATS>>  findByUSER_CODE(String USER_CODE) {
		Optional<List<ACT_FYC_ACCEPTFEATS>> tList =
				Optional.ofNullable(salesAchievementRepository.findByUSER_CODE(USER_CODE));
		return tList;
	}
	
	@PostMapping("/AGENT_NAME")
	public Optional<List<ACT_FYC_ACCEPTFEATS>>  findByAGENT_NAME(String AGENT_NAME) {
		Optional<List<ACT_FYC_ACCEPTFEATS>> tList =
				Optional.ofNullable(salesAchievementRepository.findByAGENT_NAMELike(AGENT_NAME));
		return tList;
	}
	
	
	@PostMapping("/FYC")
	public Optional<List<ACT_FYC_ACCEPTFEATS>>  findMonthlyKYC(String USER_CODE,  Date RECE_DATE) {
		Optional<List<ACT_FYC_ACCEPTFEATS>> tList =
				Optional.ofNullable(salesAchievementRepository.findMonthlyKYC(USER_CODE, RECE_DATE));
		return tList;
	}
	
}
