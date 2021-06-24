package com.company.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.company.service.models.NonCompanyUser;

public interface NonCompanyUserRepository extends JpaRepository<NonCompanyUser, Integer>{
	
	@Query("from NonCompanyUser where user_id = ?1")
	public List<NonCompanyUser> findByUser_id(String user_id);
	
	@Query("from NonCompanyUser where user_id = ?1 and user_pswd = ?2 and Status ='ACTIVE'")
	public List<NonCompanyUser> findByUseridPaswd(String user_id,String user_pswd);
}
