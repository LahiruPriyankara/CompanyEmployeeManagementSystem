package com.company.dao;

import java.util.Map;

import com.company.models.CommonUserModel;

public interface CommonUserMasterFacadeLocal {
	public Map<Integer, CommonUserModel> getAllUsers() throws Exception;
	//public Map<Integer, CommonUserModel> getAllUserByUserId(String cmnUserId) throws Exception;
	public boolean modifyUser(CommonUserModel model, int actionType);
}
