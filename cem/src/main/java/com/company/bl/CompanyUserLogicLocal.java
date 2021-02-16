/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.bl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.company.common.SBLException;
import com.company.models.CompanyUserModel;
import com.company.models.DivInfo;
import com.company.models.UserData;

public interface CompanyUserLogicLocal {
    public Map<String, CompanyUserModel> getCompanyUsers(String tableType, List<String> ids, String depCode) throws Exception;
    
    public boolean saveCompanyUser(List<CompanyUserModel> CompanyUserModelList,List<String> IdsAryList, UserData userData) throws Exception;

    public boolean rejectCompanyUser(List<CompanyUserModel> CompanyUserModelList, List<String> empIds,UserData userData) throws Exception;

    public boolean deleteCompanyUser( List<CompanyUserModel> CompanyUserModels) throws Exception;

    public boolean verifyCompanyUser(List<CompanyUserModel> CompanyUserModelList , List<String> empIds) throws Exception;
    
    public HashMap<String, UserData> getCOM_SERVICEEmployeesMap(String depCode, String empId) throws SBLException;
    
    public HashMap<String, DivInfo> getAllDepInfo() throws SBLException ;
}
