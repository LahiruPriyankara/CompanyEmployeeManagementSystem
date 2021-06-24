package com.company.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.service.models.CompanyDepartment;

public interface CompanyDepartmentRepository extends JpaRepository<CompanyDepartment, Integer>{

}
