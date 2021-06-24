/* 
    Author     : lahiru priyankara
*/
package com.company.dao;

import java.util.List;
import java.util.Map;

import com.company.models.CompanyUserModel;


/**
 *
 * @author sits_lahirupr
 */

public interface CompanyUserMasterFacadeLocal {


    public Map<String, CompanyUserModel> getMasterCompanyUsers(List<String> ids, String depCode) throws Exception;

    public boolean verifyEmp(List<CompanyUserModel> CompanyUserModelList) throws Exception;
}
