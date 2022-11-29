package com.taiwanlife.ikash.backend.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taiwanlife.ikash.backend.entity.acs.ADM_MENU;
import com.taiwanlife.ikash.backend.entity.ikash.BatchJob;
import com.taiwanlife.ikash.backend.service.BatchJobService;
import com.taiwanlife.ikash.backend.service.UserRoleService;
import com.taiwanlife.ikash.backend.utility.JsonUtil;
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
	public String HelloSunny() {
		log.info("Enter Hello Sunny");
		return "Welcome to IKash API!";
	}
	
	@GetMapping("/Menu")
	public Optional<List<ADM_MENU>> GetAllMenu() {
		Optional<List<ADM_MENU>> tList = Optional.ofNullable(userRoleService.getAllMenuItem());
		return tList;
	}
	
	@GetMapping("/Batch")
	public String GetAllBatch() {
		List<BatchJob> mList =  new ArrayList<BatchJob>();
		mList.addAll(batchJobService.getAllBatchItem());
		return JsonUtil.convertObjectToJson(mList);
	}

}
