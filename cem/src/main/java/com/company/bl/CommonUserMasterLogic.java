package com.company.bl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.company.common.APPUtills;
import com.company.common.ApplicationConstants;
import com.company.common.SBLException;
import com.company.dao.CommonUserMasterFacadeLocal;
import com.company.dao.CommonUserTmpFacadeLocal;
import com.company.dao.SeqNumberGeneratorFacadeLocal;
import com.company.models.CommonUserModel;
import com.company.models.FdUserModel;
import com.company.models.CommonUserModel;

@Service
@ComponentScan(basePackages = {"com.company.dao"})
public class CommonUserMasterLogic implements CommonUserMasterLogicLocal{

	@Autowired
	CommonUserTmpFacadeLocal commonUserTmpFacade;
	@Autowired
	CommonUserMasterFacadeLocal commonUserMasterFacade;
	@Autowired
	SeqNumberGeneratorFacadeLocal seqNumberGeneratorFacade;
	
	@Override
	public Map<Integer, CommonUserModel> getAllUsers(String tableType) throws Exception {
		System.out.println("ENTERED | CommonUserMasterLogic.getAllUsers()");
        Map<Integer, CommonUserModel> dfumsMap = new HashMap();
        try {
            if (tableType.equalsIgnoreCase(ApplicationConstants.MASTER_DATA)) {
                System.out.println("MESSAGE | Get Existing..");
                dfumsMap = commonUserMasterFacade.getAllUsers();
            } else if (tableType.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)) {
                System.out.println("MESSAGE | Get Pending..");
                dfumsMap = commonUserTmpFacade.getAllUsers();
            }

        } catch (SBLException ex) {
            System.out.println("ERROR   | Unable to fetch data." + ex.getMessage());
            throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            System.out.println("ERROR   | Unable to fetch data." + ex.getMessage());
            throw new SBLException("Unable to fetch data.Please try again.");
        }
        System.out.println("LEFT    | CommonUserMasterLogic.getAllUsers()");
        return dfumsMap;
	}

	@Override
	public boolean modifyUser(CommonUserModel model, String actionType) throws Exception {
		 System.out.println("ENTERED | CommonUserMasterLogic.rejectFdUser()");
	        boolean isSuccess = true;
	        try {
	            if(actionType.equalsIgnoreCase(ApplicationConstants.PERFORMING_ACTCION_SAVE)){
	            	Map<Integer, CommonUserModel> userMap = commonUserTmpFacade.getAllUserByUserId(model.getCmnUserId());

	            	if (!userMap.isEmpty()) {
	                    System.out.println("ERROR   | This User name is already pendin for authorization.");
	                    throw new SBLException("This User name is already pendin for authorization.");
	                }
	            	isSuccess = commonUserTmpFacade.modifyUser(model, 1);
	            }else if(actionType.equalsIgnoreCase(ApplicationConstants.PERFORMING_ACTCION_REJECT)){
	            	isSuccess = commonUserTmpFacade.modifyUser(model, 2);
	            }else if(actionType.equalsIgnoreCase(ApplicationConstants.PERFORMING_ACTCION_DELETE)){
	            	isSuccess = commonUserTmpFacade.modifyUser(model, 3);
	            }else if(actionType.equalsIgnoreCase(ApplicationConstants.PERFORMING_ACTCION_VERIFY)){
	            	isSuccess = verifyUser(model);
	            }
	            
	        } catch (Exception ex) {
	            System.out.println("ERROR   | Unable to reject." + ex.getMessage());
	            isSuccess = false;
	            //throw new SBLException("Unable to reject.Please try again.");
	        }
	        System.out.println("LEFT    | CommonUserMasterLogic.rejectFdUser()");
	        return isSuccess;
	}

	public boolean verifyUser(CommonUserModel model) throws Exception {
		System.out.println("ENTERED | CommonUserMasterLogic.verifyUser()");
        boolean isSuccess = true;
        HttpURLConnection conn = null;
        URL url;
        String serviceUrl;
        BufferedReader br;
        String response = "",responseFromBR;
        Map<Integer, CommonUserModel> userMap;
      try {
            userMap = commonUserTmpFacade.getAllUsers();

            if (userMap.isEmpty() || (userMap.get(model.getCmnUserTmpId())==null)) {
                System.out.println("ERROR   | Record is already verified.");
                throw new SBLException("Record is already verified.");
            }
            /*
            userMap = commonUserMasterFacade.getAllUserByUserId(model.getCmnUserId());
            
            if (!userMap.isEmpty()) {
                System.out.println("ERROR   | This User name is already exist.");
                throw new SBLException("This User name is already exist.");
            }
            */
            serviceUrl = ApplicationConstants.COM_ADMIN_ENTERER_ASSIGN + "/" + model.getCmnUserId()+"/"+model.getCmnUserRole();
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
            
            int masterId = 0;
            if(model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_NEW)){
            	masterId = seqNumberGeneratorFacade.getSequenceNumber(ApplicationConstants.COMMON_USER_MASTER_ID);
            	model.setCmnUserMasterId(masterId);
            	
            	//Update Master Table
                commonUserTmpFacade.modifyUser(model,2);            
                //Update Temp Table
                commonUserMasterFacade.modifyUser(model,1);
            }else{
            	//Update Master Table
                commonUserTmpFacade.modifyUser(model,2);            
                //Update Temp Table
                commonUserMasterFacade.modifyUser(model,2);
            }
        } catch (SBLException ex) {
            System.out.println("ERROR   | Unable to save front desak user in temp table." + ex.getMessage());
            throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            System.out.println("ERROR   | Unable to save front desak user in temp table." + ex.getMessage());
            throw new SBLException("Unable to save front desak user in temp table..");
        }
        System.out.println("LEFT    | CommonUserMasterLogic.verifyUser()");
        return isSuccess;
	}

}
