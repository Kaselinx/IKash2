package com.taiwanlife.ikash.backend.repository.Imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.taiwanlife.ikash.backend.entity.acs.ADM_MENU;
import com.taiwanlife.ikash.backend.repository.UserRoleRepository;

@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {

	private EntityManager entityManager;
	
	@Autowired
	public UserRoleRepositoryImpl(EntityManager _entityManager) {
		this.entityManager = _entityManager;
	}
	@Override
	public List<ADM_MENU> getAllMenuItem() {
		// get current hibernate session
		//Session currentSession = entityManager.unwrap(Session.class);
		// create a query
		Query theQuery = entityManager.createQuery("from admmenus" , ADM_MENU.class);
		List<ADM_MENU> menus = theQuery.getResultList();
		return menus;
	}

}
