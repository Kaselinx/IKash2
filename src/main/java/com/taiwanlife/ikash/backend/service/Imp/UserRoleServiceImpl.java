package com.taiwanlife.ikash.backend.service.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taiwanlife.ikash.backend.configuration.persistence.DBContextHolder;
import com.taiwanlife.ikash.backend.configuration.persistence.JpaConfig.DBTypeEnum;
import com.taiwanlife.ikash.backend.entity.acs.ADM_MENU;
import com.taiwanlife.ikash.backend.repository.MenuRoleRepository;
import com.taiwanlife.ikash.backend.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	
	//private UserRoleRepository userRoleRepository;
	@Autowired
    private MenuRoleRepository menuRepo;
	
	@Autowired
	public UserRoleServiceImpl (//UserRoleRepository _userRoleRepository
			 //MenuRoleRepository _menuRepo 
			) {
		//this.userRoleRepository = _userRoleRepository;
		//this.menuRepo  = _menuRepo;
	}

//	@Override
//	public List<ADM_MENU> getAllMenuItem() {
//		//return menuRepo.findAll();
//		return userRoleRepository.getAllMenuItem();
//	}
//	
	public List<ADM_MENU> getAll(){
		DBContextHolder.setCurrentDb(DBTypeEnum.MAIN);
		return menuRepo.findAll();
	}

}
