/* 
    Author     : lahiru priyankara
*/

package com.company.dao;

import java.util.Map;

import com.company.models.CommonUserModel;

public interface CommonUserTmpFacadeLocal {
	public Map<Integer, CommonUserModel> getAllUsers() throws Exception;
	public boolean modifyUser(CommonUserModel model, int actionType);
	public Map<Integer, CommonUserModel> getAllUserByUserId(String cmnUserId) throws Exception;
}
