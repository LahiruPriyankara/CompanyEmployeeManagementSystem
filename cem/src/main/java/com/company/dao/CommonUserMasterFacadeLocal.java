/* 
    Author     : lahiru priyankara
*/

package com.company.dao;

import java.util.Map;

import com.company.models.CommonUserModel;

public interface CommonUserMasterFacadeLocal {
	public Map<Integer, CommonUserModel> getAllUsers() throws Exception;
	public Map<Integer, CommonUserModel> getUserByUserId(String cmnUserId) throws Exception;
	public boolean modifyUser(CommonUserModel model, int actionType);
	
	//public Map<Integer, CommonUserModel> getUserByUserId(String empId) throws Exception;
}
