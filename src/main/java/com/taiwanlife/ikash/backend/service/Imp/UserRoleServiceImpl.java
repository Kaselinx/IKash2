package com.taiwanlife.ikash.backend.service.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taiwanlife.ikash.backend.entity.acs.ADM_MENU;
import com.taiwanlife.ikash.backend.repository.UserRoleRepository;
import com.taiwanlife.ikash.backend.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	
	private UserRoleRepository userRoleRepository;
    //private MenuRoleRepository menuRepo;
	
	@Autowired
	public UserRoleServiceImpl (UserRoleRepository _userRoleRepository
			//, MenuRoleRepository _menuRepo 
			) {
		this.userRoleRepository = _userRoleRepository;
		//this.menuRepo  = _menuRepo;
	}

	@Override
	public List<ADM_MENU> getAllMenuItem() {
		//return menuRepo.findAll();
		return userRoleRepository.getAllMenuItem();
	}

}
