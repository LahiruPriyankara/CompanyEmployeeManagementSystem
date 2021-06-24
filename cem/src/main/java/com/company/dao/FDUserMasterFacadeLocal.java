/* 
    Author     : lahiru priyankara
*/
package com.company.dao;

import java.util.List;
import java.util.Map;

import com.company.dto.FdUserMaster;
import com.company.models.FdUserModel;

/**
 *
 * @author sits_lahirupr
 */
public interface FDUserMasterFacadeLocal {
	public Map<Integer, FdUserModel> getAllFdUsersByUserIds(List<Integer> ids) throws Exception;

	public Map<Integer, FdUserModel> getFdUserByUserName(List<String> getFdUserByUserNames) throws Exception;

	public boolean modifyUser(FdUserModel model, int actionType);
}
