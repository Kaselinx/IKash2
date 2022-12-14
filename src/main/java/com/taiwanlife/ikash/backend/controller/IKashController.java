package com.taiwanlife.ikash.backend.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taiwanlife.ikash.backend.entity.acs.ADM_MENU;
import com.taiwanlife.ikash.backend.entity.ikash.BatchJob;
import com.taiwanlife.ikash.backend.service.BatchJobService;
import com.taiwanlife.ikash.backend.service.UserRoleService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/IKash")
public class IKashController {

	private UserRoleService userRoleService;
	
	private BatchJobService batchJobService;

	@Autowired
	public IKashController(UserRoleService TheUserRoleService,BatchJobService ThebatchJobService ) {
		userRoleService = TheUserRoleService;
		batchJobService = ThebatchJobService;
	}
	
	@GetMapping("/Index")
	public String HelloTeam() {
		log.info("Enter Hello Sunny");
		return "Welcome to IKash API!";
	}
	
	@GetMapping("/Menu")
	public Optional<List<ADM_MENU>> GetAllMenu() {
		Optional<List<ADM_MENU>> tList = Optional.ofNullable(userRoleService.getAll());
		return tList;
	}
	
	@GetMapping("/Batch")
	public Optional<List<BatchJob>> GetAllBatch() {
		Optional<List<BatchJob>> tList = Optional.ofNullable(batchJobService.getAllBatchItem());
		return tList;
	}

}
