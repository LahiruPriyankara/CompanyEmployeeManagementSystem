/* 
    Author     : lahiru priyankara
*/
package com.company.dao;

import java.util.List;
import java.util.Map;
import com.company.models.FdUserModel;

/**
 *
 * @author sits_lahirupr
 */
public interface FDUserTmpFacadeLocal {

	public Map<Integer, FdUserModel> getTempFdUsers(List<Integer> ids, String tableType) throws Exception;

	public boolean modifyUser(FdUserModel model, int actionType);
}
