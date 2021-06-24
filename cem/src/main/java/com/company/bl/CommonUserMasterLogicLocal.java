/* 
    Author     : lahiru priyankara
*/

package com.company.bl;

import java.util.Map;

import com.company.models.CommonUserModel;

public interface CommonUserMasterLogicLocal {
	public Map<Integer, CommonUserModel> getAllUsers(String tableType) throws Exception;
	public Map<Integer, CommonUserModel> getUserByUserId(String cmnUserId) throws Exception;
	public boolean modifyUser(CommonUserModel mode,String actionTypel) throws Exception;
	public boolean verifyUser(CommonUserModel model) throws Exception;
}
