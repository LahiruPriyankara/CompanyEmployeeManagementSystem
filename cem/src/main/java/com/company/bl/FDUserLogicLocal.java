package com.company.bl;

import java.util.List;
import java.util.Map;

import com.company.common.SBLException;
import com.company.models.FdUserModel;
import com.company.models.UserData;


public interface FDUserLogicLocal {

	public Map<Integer, FdUserModel> getFdUsers(String tableType, List<Integer> ids) throws Exception;

	public FdUserModel getFdUserByUserName(List<String> getFdUserByUserNames) throws Exception;

	public boolean setPasswordFdUser(String userName, String userPassword) throws SBLException;

	public boolean saveFdUser(FdUserModel fdUserModel, UserData userData, boolean isSecurepassProcess) throws Exception;

	public boolean rejectFdUser(FdUserModel fdUserModel, UserData userData) throws Exception;

	public boolean deleteFdUser(FdUserModel fdUserModel) throws Exception;

	public boolean verifyUser(FdUserModel fdUserModel, UserData userData, boolean isSecurepassProcess) throws Exception;
}