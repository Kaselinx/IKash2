package com.taiwanlife.ikash.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taiwanlife.ikash.backend.entity.acs.ADM_MENU;

@Repository
public interface MenuRoleRepository extends JpaRepository<ADM_MENU, String> {

}
