/* 
    Author     : lahiru priyankara
*/

package com.company.bl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.company.dao.FDUserMasterFacadeLocal;
import com.company.dao.FDUserTmpFacadeLocal;
import com.company.dao.SeqNumberGeneratorFacadeLocal;
import com.company.dto.FdUserMaster;
import com.company.models.FdUserModel;
import com.company.models.UserData;
import com.company.common.APPUtills;
import com.company.common.ApplicationConstants;
import com.company.common.SBLException;

@Service
@ComponentScan(basePackages = {"com.company.dao"})
public class FDUserLogic implements FDUserLogicLocal {
	private static Logger Log = LogManager.getLogger(FDUserLogic.class);
	
	@Autowired
	FDUserMasterFacadeLocal fDUserMasterFacade;
	
	@Autowired
	FDUserTmpFacadeLocal fDUserTmpFacade;
	
	@Autowired
	SeqNumberGeneratorFacadeLocal seqNumberGeneratorFacade;
	
	@Override
    public Map<Integer, FdUserModel> getFdUsers(String tableType, List<Integer> ids) throws Exception {
        System.out.println("ENTERED | FDUserLogic.getFdUsers()");
        Map<Integer, FdUserModel> dfumsMap = new HashMap();
        try {
            if (tableType.equalsIgnoreCase(ApplicationConstants.MASTER_DATA)) {
                System.out.println("MESSAGE | Get Existing..");
                dfumsMap = fDUserMasterFacade.getAllFdUsersByUserIds(ids);
            } else if (tableType.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)) {
                System.out.println("MESSAGE | Get Pending..");
                dfumsMap = fDUserTmpFacade.getTempFdUsers(ids, ApplicationConstants.TEMP_DATA);
            }

        } catch (SBLException ex) {
            Log.error("ERROR   | Unable to fetch data." + ex.getMessage(), ex);
            throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            Log.error("ERROR   | Unable to fetch data." + ex.getMessage(), ex);
            throw new SBLException("Unable to fetch data.Please try again.");
        }
        System.out.println("LEFT    | FDUserLogic.getFdUsers()");
        return dfumsMap;
    }

    @Override
    public FdUserModel getFdUserByUserName(List<String> getFdUserByUserNames) throws Exception {
        System.out.println("ENTERED | FDUserLogic.getFdUserByUserName()");
        Map<Integer, FdUserModel> dfumsMap = new HashMap();
        List<FdUserModel> dfums = new ArrayList<>();
        FdUserModel dfum = null;
        try {
            dfumsMap = fDUserMasterFacade.getFdUserByUserName(getFdUserByUserNames);
            dfums = new ArrayList<>(dfumsMap.values());
            if (dfums.size() > 0) {
                dfum = dfums.get(0);
            } else {
                Log.error("ERROR   | Can not found desk user for user ids : " + getFdUserByUserNames);
                //throw new SBLException("Can not found desk user.");
            }

        } catch (SBLException ex) {
            Log.error("ERROR   | Unable to fetch data." + ex.getMessage(), ex);
            throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            Log.error("ERROR   | Unable to fetch data." + ex.getMessage(), ex);
            throw new SBLException("Unable to fetch data.Please try again.");
        }
        System.out.println("LEFT    | FDUserLogic.getFdUserByUserName()");
        return dfum;
    }

    @Override
    public boolean setPasswordFdUser(String userName, String userPassword) throws SBLException {
        System.out.println("ENTERED | FDUserLogic.setPasswordirectryUser()");
        boolean isSuccess = false;
        HttpURLConnection conn = null;
        URL url;
        String serviceUrl;
        BufferedReader br;
        String response = "",responseFromBR;
       
       try { 
    	   
    	   serviceUrl = ApplicationConstants.SET_PASSWD_NON_COMPANY_USER + "/" + userName + "/" +userPassword;
           System.out.println("MESSAGE | UpmRestService URL : " + serviceUrl);
           url = new URL(serviceUrl);
           conn = (HttpURLConnection) url.openConnection();
           conn.setDoOutput(true);
           conn.setRequestMethod("GET");
           conn.setRequestProperty("Content-Type", "application/json");
           if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
           	System.out.println("ERROR   | Failed : HTTP error code : " + conn.getResponseCode());
               throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
           }
           br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
           response = "";
           responseFromBR="";
           while ((responseFromBR = br.readLine()) != null) {
           	System.out.println("MESSAGE | Response : " + responseFromBR);
           	response = responseFromBR;
           }
           
           if(!response.equalsIgnoreCase("true")){
           	throw new SBLException("Can not set the password.Please try again later.");
           }	
           
           fDUserTmpFacade.getTempFdUsers(null,null);
           FdUserModel fdUserModel = getFdUserByUserName(Arrays.asList(userName));
           Map<Integer, FdUserModel> userMap = fDUserTmpFacade.getTempFdUsers(Arrays.asList(fdUserModel.getFdUserMasterId()), ApplicationConstants.MASTER_DATA);

            //fdUserModel.setFdUserTmpId(seqNumberGeneratorBean.getNextFDUserTempId());
            fdUserModel.setActionType(ApplicationConstants.ACTION_TYPE_MODIFY);
            fdUserModel.setSecurepassUserStatus(ApplicationConstants.SECUREPASS_USER_SET_PASSWORD);

            System.out.println("MESSAGE | fdUserModel.toString() " + fdUserModel.toString());
            fDUserTmpFacade.modifyUser(fdUserModel,1);

            //SETTING USER PASSWORD SUCCESSFULLY
            isSuccess = true;
            System.out.println("MESSAGE |  User Password Set Successfull");
        } catch (SBLException e) {
            Log.error("ERROR   | " + e.getMessage());
            throw new SBLException(e.getMessage());
        } catch (Exception e) {
            Log.error("ERROR   | " + e.getMessage());
            throw new SBLException("User Password Set fail.");
        }
        System.out.println("LEFT    | FDUserLogic.setPasswordirectryUser()");
        return isSuccess;
    }

    @Override
    public boolean saveFdUser(FdUserModel fdUserModel, UserData userData, boolean isSecurepassProcess) throws Exception {
        System.out.println("ENTERED | FDUserLogic.saveFdUser()");
        boolean isSuccess = true;
      try {
            fdUserModel.setModifiedBy(Integer.parseInt(userData.getUSER_ID()));
            fdUserModel.setModifiedDate(APPUtills.getCurrentDate());
// ---------------------------------------------------------------------------------------
            System.out.println("MESSAGE |  isSecurepassProcess : " + isSecurepassProcess);
            if (isSecurepassProcess) {
                System.out.println("MESSAGE |  Going to update temp for secure password.");
                Map<Integer, FdUserModel> userMap = new HashMap();
                userMap = fDUserTmpFacade.getTempFdUsers(Arrays.asList(fdUserModel.getFdUserMasterId()), ApplicationConstants.MASTER_DATA);

                if (userMap.size() > 0) {
                    Log.error("ERROR   | Bellow record is already pending.");
                    throw new SBLException("Bellow record is already pending.");
                }
                fdUserModel.setRecStatus(ApplicationConstants.RECORD_STATUS_PENDING);
                System.out.println("MESSAGE | fdUserModel.toString() " + fdUserModel.toString());
                fDUserTmpFacade.modifyUser(fdUserModel,2);

                return isSuccess;
            }

// ---------------------------------------------------------------------------------------
            if (fdUserModel.getRecStatus().equalsIgnoreCase(ApplicationConstants.RECORD_STATUS_REJECT)) {
                System.out.println("MESSAGE |  Going to reject temp record.");
                fdUserModel.setRecStatus(ApplicationConstants.RECORD_STATUS_PENDING);
                System.out.println("MESSAGE | fdUserModel.toString() " + fdUserModel.toString());
                fDUserTmpFacade.modifyUser(fdUserModel,2);
            } else {
                //fdUserModel.setFdUserTmpId(seqNumberGeneratorBean.getNextFDUserTempId());
                fdUserModel.setRecStatus(ApplicationConstants.RECORD_STATUS_PENDING);
                if (fdUserModel.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_NEW)) {
                    System.out.println("MESSAGE |  Going to save new record.");
                    fdUserModel.setCreatedBy(Integer.parseInt(userData.getUSER_ID()));
                    fdUserModel.setCreatedDate(APPUtills.getCurrentDate());
                } else {
                    System.out.println("MESSAGE |  Going to save master table record.");
                    Map<Integer, FdUserModel> userMap = new HashMap();
                    userMap = fDUserTmpFacade.getTempFdUsers(Arrays.asList(fdUserModel.getFdUserMasterId()), ApplicationConstants.MASTER_DATA);

                    if (userMap.size() > 0) {
                        Log.error("ERROR   | Bellow record is already pending.");
                        throw new SBLException("Bellow record is already pending.");
                    }
                }
                System.out.println("MESSAGE | fdUserModel.toString() " + fdUserModel.toString());
                fDUserTmpFacade.modifyUser(fdUserModel,1);
            }

        } catch (SBLException ex) {
            Log.error("ERROR   | Unable to save front desak user in temp table." + ex.getMessage(), ex);
            throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            Log.error("ERROR   | Unable to save front desak user in temp table." + ex.getMessage(), ex);
            throw new SBLException("Unable to save front desak user in temp table..");
        }
        System.out.println("LEFT    | FDUserLogic.saveFdUser()");
        return isSuccess;
    }
    
    @Override
    public boolean saveFdUserResetPassword(FdUserModel fdUserModel, UserData userData) throws Exception {
        System.out.println("ENTERED | FDUserLogic.saveFdUserResetPassword()");
        boolean isSuccess = true;
      try {
            fdUserModel.setModifiedBy(Integer.parseInt(userData.getUSER_ID()));
            fdUserModel.setModifiedDate(APPUtills.getCurrentDate());
            fDUserTmpFacade.modifyUser(fdUserModel,2);            

        } catch (Exception ex) {
            Log.error("ERROR   | Unable to save front desak user in temp table." + ex.getMessage(), ex);
            throw new SBLException("Unable to save front desak user in temp table..");
        }
        System.out.println("LEFT    | FDUserLogic.saveFdUserResetPassword()");
        return isSuccess;
    }
    
    @Override
    public boolean verifyFdUserResetPassword(FdUserModel fdUserModel, UserData userData) throws Exception {
        System.out.println("ENTERED | FDUserLogic.verifyFdUserResetPassword()");
        boolean isSuccess = true;
        HttpURLConnection conn = null;
        URL url;
        String serviceUrl;
        BufferedReader br;
        String response = "",responseFromBR;
      try {
            Map<Integer, FdUserModel> userMap = fDUserTmpFacade.getTempFdUsers(Arrays.asList(fdUserModel.getFdUserTmpId()), ApplicationConstants.TEMP_DATA);

            if (userMap.isEmpty()) {
                Log.error("ERROR   | Bellow record is already verified.");
                throw new SBLException("Bellow record is already verified.");
            }
            
            serviceUrl = ApplicationConstants.UNLOCK_PASSWD_NON_COMPANY_USER + "/" + fdUserModel.getFdUserName();
            System.out.println("MESSAGE | UpmRestService URL : " + serviceUrl);
            url = new URL(serviceUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            	System.out.println("ERROR   | Failed : HTTP error code : " + conn.getResponseCode());
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            response = "";
            responseFromBR="";
            while ((responseFromBR = br.readLine()) != null) {
            	System.out.println("MESSAGE | Response : " + responseFromBR);
            	response = responseFromBR;
            }
            
            if(!response.equalsIgnoreCase("true")){
            	throw new SBLException("Can not active the password.Please try again later.");
            }	
            
            fdUserModel.setVerifiedBy(Integer.parseInt(userData.getUSER_ID()));
            fdUserModel.setVerifiedDate(APPUtills.getCurrentDate());
            fdUserModel.setRecStatus(ApplicationConstants.RECORD_STATUS_VERIFY);
            fdUserModel.setSecurepassUserStatus(ApplicationConstants.SECUREPASS_USER_SET_PASSWORD_AUTH);
            
            System.out.println("MESSAGE | fdUserModel.toString() " + fdUserModel.toString());
            
            //Update Master Table
        	fDUserMasterFacade.modifyUser(fdUserModel,2);
            
            //Update Temp Table
            fDUserTmpFacade.modifyUser(fdUserModel,2);
            

        } catch (SBLException ex) {
            Log.error("ERROR   | Unable to save front desak user in temp table." + ex.getMessage(), ex);
            throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            Log.error("ERROR   | Unable to save front desak user in temp table." + ex.getMessage(), ex);
            throw new SBLException("Unable to save front desak user in temp table..");
        }
        System.out.println("LEFT    | FDUserLogic.verifyFdUserResetPassword()");
        return isSuccess;
    }

    @Override
    public boolean rejectFdUser(FdUserModel fdUserModel, UserData userData) throws Exception {
        System.out.println("ENTERED | FDUserLogic.rejectFdUser()");
        boolean isSuccess = true;
        try {
            fdUserModel.setModifiedBy(Integer.parseInt(userData.getUSER_ID()));
            fdUserModel.setModifiedDate(APPUtills.getCurrentDate());
            fdUserModel.setRecStatus(ApplicationConstants.RECORD_STATUS_REJECT);
            System.out.println("MESSAGE | fdUserModel.toString() " + fdUserModel.toString());
            fDUserTmpFacade.modifyUser(fdUserModel,2);
        } catch (Exception ex) {
            Log.error("ERROR   | Unable to reject." + ex.getMessage(), ex);
            isSuccess = false;
            //throw new SBLException("Unable to reject.Please try again.");
        }
        System.out.println("LEFT    | FDUserLogic.rejectFdUser()");
        return isSuccess;
    }

    @Override
    public boolean deleteFdUser(FdUserModel fdUserModel) throws Exception {
        System.out.println("ENTERED | FDUserLogic.deleteFdUser()");
        boolean isSuccess = true;
        try {
            System.out.println("MESSAGE | fdUserModel.toString() " + fdUserModel.toString());
            fDUserTmpFacade.modifyUser(fdUserModel,3);
        } catch (Exception ex) {
            Log.error("ERROR   | Unable to reject." + ex.getMessage(), ex);
            isSuccess = false;
            //throw new SBLException("Unable to dalete.Please try again.");
        }
        System.out.println("LEFT    | FDUserLogic.deleteFdUser()");
        return isSuccess;
    }

    @Override
    public boolean verifyUser(FdUserModel fdUserModel, UserData userData, boolean isSecurepassProcess) throws Exception {
        System.out.println("ENTERED | FDUserLogic.verifyUser()");
        boolean isSuccess = false, isRegister = false, isAuthorize = false, iActivateUser = false;
        HttpURLConnection conn = null;
        URL url;
        String serviceUrl;
        BufferedReader br;
        String response = "",responseFromBR;
        try {
            System.out.println("MESSAGE | isSecurepassProcess : " + isSecurepassProcess);
            Map<Integer, FdUserModel> userMap;
            userMap = fDUserTmpFacade.getTempFdUsers(Arrays.asList(fdUserModel.getFdUserTmpId()), ApplicationConstants.TEMP_DATA);

            if (userMap.isEmpty()) {
                Log.error("ERROR   | Bellow record is already verified.");
                throw new SBLException("Bellow record is already verified.");
            }           	
            
            fdUserModel.setVerifiedBy(Integer.parseInt(userData.getUSER_ID()));
            fdUserModel.setVerifiedDate(APPUtills.getCurrentDate());
            fdUserModel.setRecStatus(ApplicationConstants.RECORD_STATUS_VERIFY);
            
            
            System.out.println("MESSAGE | fdUserModel.toString() " + fdUserModel.toString());
            if (fdUserModel.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_NEW)) {
            	
            	fdUserModel.setSecurepassUserStatus(ApplicationConstants.SECUREPASS_USER_REGISTER);
            	
            	// ------------------------- CREATE USER IN COMPANY SERVICE -------------------------------------
                serviceUrl = ApplicationConstants.CREATE_NON_COMPANY_USER + "/" + fdUserModel.getFdUserName();
                System.out.println("MESSAGE | UpmRestService URL : " + serviceUrl);
                url = new URL(serviceUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                	System.out.println("ERROR   | Failed : HTTP error code : " + conn.getResponseCode());
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                response = "";
                responseFromBR="";
                while ((responseFromBR = br.readLine()) != null) {
                	System.out.println("MESSAGE | Response : " + responseFromBR);
                	response = responseFromBR;
                }
                
                if(response.equalsIgnoreCase(ApplicationConstants.USER_CREATION_ERROR)){
                	throw new SBLException("Can not create a user in company service.try again later.");
                }
                
                // ------------------------- ACTIVE THE PASSWORD OF NON COMPANY USER IN COMPANY SERVICE -------------------------------------
                serviceUrl = ApplicationConstants.VERIFY_NON__COMPANY_USER + "/" + fdUserModel.getFdUserName();
                System.out.println("MESSAGE | UpmRestService URL : " + serviceUrl);
                url = new URL(serviceUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                	System.out.println("ERROR   | Failed : HTTP error code : " + conn.getResponseCode());
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                response = "";
                responseFromBR="";
                while ((responseFromBR = br.readLine()) != null) {
                	System.out.println("MESSAGE | Response : " + responseFromBR);
                	response = responseFromBR;
                }
                
                if(!response.equalsIgnoreCase("true")){
                	throw new SBLException("Can not active the password in company service.Please try again later.");
                }
                fdUserModel.setFdUserMasterId(seqNumberGeneratorFacade.getSequenceNumber(ApplicationConstants.FD_USER_MASTER_ID));
            }else{
            	if(isSecurepassProcess){
            		// ------------------------- ACTIVE THE PASSWORD OF NON COMPANY USER IN COMPANY SERVICE -------------------------------------
                    serviceUrl = ApplicationConstants.ACTIVE_PASSWD_NON_COMPANY_USER + "/" + fdUserModel.getFdUserName();
                    System.out.println("MESSAGE | UpmRestService URL : " + serviceUrl);
                    url = new URL(serviceUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json");
                    if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    	System.out.println("ERROR   | Failed : HTTP error code : " + conn.getResponseCode());
                        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                    }
                    br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    response = "";
                    responseFromBR="";
                    while ((responseFromBR = br.readLine()) != null) {
                    	System.out.println("MESSAGE | Response : " + responseFromBR);
                    	response = responseFromBR;
                    }
                    
                    if(!response.equalsIgnoreCase("true")){
                    	throw new SBLException("Can not active the password in company service.Please try again later.");
                    }
                    
                    fdUserModel.setSecurepassUserStatus(ApplicationConstants.SECUREPASS_USER_ACTIVE);
            	}
            	
            }
            int actionType = fdUserModel.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_NEW)?1:2;
            System.out.println("ACTION TYPE FOR MASTER TBL : "+actionType);
            //Update Temp Table
            fDUserTmpFacade.modifyUser(fdUserModel,2);
            //Update Master Table
        	fDUserMasterFacade.modifyUser(fdUserModel,actionType);
            
            isSuccess = true;
        } catch (Exception ex) {
            Log.error("ERROR   | Unable to verify." + ex.getMessage(), ex);
            //isSuccess = false;
            throw new SBLException(ex.getMessage());
        }
        System.out.println("LEFT    | FDUserLogic.verifyUser()");
        return isSuccess;
    }
    
    
    @Override
    public boolean setPassword(String userName, String userPassword) throws SBLException {
       System.out.println("ENTERED | FDUserLogic.setPassword()");
        boolean isSuccess = false;
        HttpURLConnection conn = null;
        URL url;
        String serviceUrl;
        BufferedReader br;
        String response = "",responseFromBR;
        try {        	
        	serviceUrl = ApplicationConstants.SET_PASSWD_NON_COMPANY_USER + "/" + userName+"/"+userPassword;
            System.out.println("MESSAGE | UpmRestService URL : " + serviceUrl);
            url = new URL(serviceUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            	System.out.println("ERROR   | Failed : HTTP error code : " + conn.getResponseCode());
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            response = "";
            responseFromBR="";
            while ((responseFromBR = br.readLine()) != null) {
            	System.out.println("MESSAGE | Response : " + responseFromBR);
            	response = responseFromBR;
            }
            
            if(!response.equalsIgnoreCase("true")){
            	throw new SBLException("Can not active the password.Please try again later.");
            }
            
            FdUserModel fdUserModel = getFdUserByUserName(Arrays.asList(userName));
            Map<Integer, FdUserModel> userMap = fDUserTmpFacade.getTempFdUsers(Arrays.asList(fdUserModel.getFdUserMasterId()), ApplicationConstants.MASTER_DATA);

            if (userMap.size() > 0) {
                System.out.println("ERROR   | Password is already sent for authorization.");
                 throw new SBLException("Password is already sent for authorization.");
             }
             
             System.out.println("MESSAGE | Successfully set the password.");

             fdUserModel.setFdUserTmpId(seqNumberGeneratorFacade.getSequenceNumber(ApplicationConstants.FD_USER_TMP_ID));
             fdUserModel.setActionType(ApplicationConstants.ACTION_TYPE_MODIFY);
             fdUserModel.setSecurepassUserStatus(ApplicationConstants.SECUREPASS_USER_SET_PASSWORD);

            System.out.println("MESSAGE | fdUserModel.toString() " + fdUserModel.toString());
            fDUserTmpFacade.modifyUser(fdUserModel,1);
            
            //SETTING USER PASSWORD SUCCESSFULLY
            isSuccess = true;
           System.out.println("MESSAGE |  User Password Set Successfull");
        } catch (SBLException e) {
           System.out.println("ERROR   | " + e.getMessage());
            throw new SBLException(e.getMessage());
        } catch (Exception e) {
           System.out.println("ERROR   | " + e.getMessage());
            throw new SBLException("User Password Set fail.");
        }
       System.out.println("LEFT    | FDUserLogic.setPassword()");
        return isSuccess;
    }
    
    
    @Override
    public boolean updateFdUserPasswordLock(FdUserModel fdUserModel) throws Exception {
        System.out.println("ENTERED | FDUserLogic.updateFdUserPasswordLock()");
        boolean isSuccess = true;
        try {
            System.out.println("MESSAGE | fdUserModel.toString() " + fdUserModel.toString());
            fDUserMasterFacade.modifyUser(fdUserModel,2);
        } catch (Exception ex) {
            Log.error("ERROR   | Unable to lock password." + ex.getMessage(), ex);
            isSuccess = false;
            //throw new SBLException("Unable to dalete.Please try again.");
        }
        System.out.println("LEFT    | FDUserLogic.updateFdUserPasswordLock()");
        return isSuccess;
    }

}
