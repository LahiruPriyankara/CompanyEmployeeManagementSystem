package com.company.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.company.service.models.CompanyUser;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Integer>{
	
	@Query("from CompanyUser where user_id = ?1")
	public List<CompanyUser> findByUser_id(String user_id);

	@Query("from CompanyUser where user_dep_id = ?1 and Status ='ACTIVE'")
	public List<CompanyUser> findByUserDepId(String user_dep_id);
	
	@Query("from CompanyUser where user_id = ?1 and user_pswd = ?2 and Status ='ACTIVE'")
	public List<CompanyUser> findByUseridPaswd(String user_id,String user_pswd);
}
