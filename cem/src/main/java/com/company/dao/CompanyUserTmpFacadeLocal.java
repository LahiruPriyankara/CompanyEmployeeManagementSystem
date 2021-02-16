/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao;

import java.util.List;
import java.util.Map;
import com.company.dto.CompanyUserTmp;
import com.company.models.CompanyUserModel;
import com.company.models.UserData;

public interface CompanyUserTmpFacadeLocal {

	public Map<String, CompanyUserModel> getTempCompanyUsers(List<String> ids, String depCode) throws Exception;

	public boolean saveEmp(List<CompanyUserModel> CompanyUserModelList, UserData userData) throws Exception;

	public boolean rejectEmp(List<CompanyUserModel> CompanyUserModelList) throws Exception;

	public boolean deleteEmp(List<CompanyUserModel> CompanyUserModelList) throws Exception;

	public boolean verifyEmp(List<CompanyUserModel> CompanyUserModelList) throws Exception;
}
