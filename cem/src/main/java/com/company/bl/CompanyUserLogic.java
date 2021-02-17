/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.bl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.company.common.ApplicationConstants;
import com.company.dao.CompanyUserMasterFacade;
import com.company.dao.CompanyUserMasterFacadeLocal;
import com.company.dao.CompanyUserTmpFacade;
import com.company.dao.CompanyUserTmpFacadeLocal;
import com.company.dao.SeqNumberGeneratorFacadeLocal;
import com.company.models.CompanyUserModel;
import com.company.models.DivInfo;
import com.company.models.UserData;
import com.company.common.APPUtills;
import com.company.common.SBLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@ComponentScan(basePackages = {"com.company.dao"})
public class CompanyUserLogic implements CompanyUserLogicLocal {

    private static Logger dedLog = LogManager.getLogger(CompanyUserLogic.class);
    
    @Autowired
    private CompanyUserTmpFacadeLocal companyUserTmpFacade;
    @Autowired
    private SeqNumberGeneratorFacadeLocal seqNumberGeneratorFacade;
    @Autowired
    private CompanyUserMasterFacadeLocal companyUserMasterFacade;

    @Override
    public Map<String, CompanyUserModel> getCompanyUsers(String tableType, List<String> ids, String depCode) throws Exception {
       System.out.println("ENTERED | CompanyUserLogic.getCompanyUsers()");
        Map<String, CompanyUserModel> dfumsMap = new HashMap();
        try {
            if (tableType.equalsIgnoreCase(ApplicationConstants.MASTER_DATA)) {
               System.out.println("MESSAGE | Get from master..");
                dfumsMap = companyUserMasterFacade.getMasterCompanyUsers(ids, depCode);
            } else if (tableType.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)) {
               System.out.println("MESSAGE | Get from temp..");
                dfumsMap = companyUserTmpFacade.getTempCompanyUsers(ids, depCode);
            }

        } catch (SBLException ex) {
            dedLog.error("ERROR   | Unable to fetch data." + ex.getMessage(), ex);
            throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            dedLog.error("ERROR   | Unable to fetch data." + ex.getMessage(), ex);
            throw new SBLException("Unable to fetch data.Please try again.");
        }
       System.out.println("LEFT | CompanyUserLogic.getCompanyUsers()");
        return dfumsMap;
    }    

    @Override
    public boolean saveCompanyUser(List<CompanyUserModel> bankUserModelList,List<String> ids, UserData userData) throws Exception {
       System.out.println("ENTERED | CompanyUserLogic.saveCompanyUser()");
        boolean isSuccess = false;
        String errorCode = "";
        try {
            Map<String, CompanyUserModel> userMap = companyUserTmpFacade.getTempCompanyUsers(ids, userData.getDIV_CODE());//Pending OR Reject

           /* for (CompanyUserModel bankUserModel : bankUserModelList) {
                if (userMap.get(bankUserModel.getBankUserEmpId()) != null && userMap.get(bankUserModel.getBankUserEmpId()).getRecStatus().equalsIgnoreCase(ApplicationConstants.RECORD_STATUS_PENDING) ) {
                    errorCode = errorCode.equalsIgnoreCase("") ? bankUserModel.getBankUserEmpId() : errorCode + "," + bankUserModel.getBankUserEmpId();
                }
            }*/
            if (APPUtills.isThisStringValid(errorCode)) {
                dedLog.error("ERROR   | Bellow records are already pending for this department." + errorCode);
                throw new SBLException("Bellow records are already pending for this department.<br>(" + errorCode+")");
            }
                        
            isSuccess = companyUserTmpFacade.saveEmp(bankUserModelList,userData);

        } catch (SBLException ex) {
            dedLog.error("ERROR   | Unable to save bank user in temp table." + ex.getMessage(), ex);
            throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            dedLog.error("ERROR   | Unable to save bank user in temp table." + ex.getMessage(), ex);
            throw new SBLException("Unable to save bank user in temp table.");
        }
       System.out.println("LEFT | CompanyUserLogic.saveCompanyUser()");
        return isSuccess;
    }

    @Override
    public boolean rejectCompanyUser(List<CompanyUserModel> bankUserModelList, List<String> empIds,UserData userData) throws Exception {
       System.out.println("ENTERED | CompanyUserLogic.rejectCompanyUser()");
        boolean isSuccess = false;
        String errorCode = "";
        try {
          Map<String, CompanyUserModel> userMap = companyUserTmpFacade.getTempCompanyUsers(empIds, userData.getDIV_CODE());//Pending OR Reject

            for (String empId : empIds) {
                if (userMap.get(empId) != null && userMap.get(empId).getRecStatus().equalsIgnoreCase(ApplicationConstants.RECORD_STATUS_REJECT)) {
                    errorCode = errorCode.equalsIgnoreCase("") ? empId : errorCode + "," + empId;
                }
            }

            if (APPUtills.isThisStringValid(errorCode)) {
                dedLog.error("ERROR   | Bellow record is already rejected in this department." + errorCode);
                throw new SBLException("Bellow record is already rejected in this department.<br>(" + errorCode+")");
            }
            //Update Temp
            isSuccess = companyUserTmpFacade.rejectEmp(bankUserModelList);            

        } catch (Exception ex) {
            dedLog.error("ERROR   | Unable to verify." + ex.getMessage(), ex);
            throw new SBLException(ex.getMessage());
        }
       System.out.println("LEFT | CompanyUserLogic.rejectCompanyUser()");
        return isSuccess;
    }

    @Override
    public boolean verifyCompanyUser(List<CompanyUserModel> bankUserModelList, List<String> empIds) throws Exception {
       System.out.println("ENTERED | CompanyUserLogic.verifyCompanyUser()");
        boolean isSuccess = false;
        String errorCode = "";
        try {
            Map<String, CompanyUserModel> userMap = companyUserTmpFacade.getTempCompanyUsers(empIds, null);//Pending OR Reject

            for (String empId : empIds) {
                if (userMap.get(empId) == null) {
                    errorCode = errorCode.equalsIgnoreCase("") ? empId : errorCode + "," + empId;
                }
            }

            if (APPUtills.isThisStringValid(errorCode)) {
                dedLog.error("ERROR   | Bellow record is already verified." + errorCode);
                throw new SBLException("Bellow record is already verified.<br>(" + errorCode+")");
            }
            
          /*  for (CompanyUserModel bankUserModel : bankUserModelList) {
                if (bankUserModel.getBankUserMasterId() == null) {
                    bankUserModel.setBankUserMasterId(seqNumberGeneratorBean.getNextBankUserMasterId());
                } 
            }
           */
            //Update Temp
            isSuccess = companyUserTmpFacade.verifyEmp(bankUserModelList);
            //Update Master
            isSuccess = companyUserMasterFacade.verifyEmp(bankUserModelList);

        } catch (Exception ex) {
            dedLog.error("ERROR   | Unable to verify." + ex.getMessage(), ex);
            throw new SBLException(ex.getMessage());
        }
       System.out.println("LEFT | CompanyUserLogic.verifyCompanyUser()");
        return isSuccess;
    }

    @Override
    public boolean deleteCompanyUser( List<CompanyUserModel> bankUserModels) throws Exception {
       System.out.println("ENTERED | CompanyUserLogic.deleteCompanyUser()");
        boolean isSuccess = true;
        try {
            companyUserTmpFacade.deleteEmp(bankUserModels);
        } catch (Exception ex) {
            dedLog.error("ERROR   | Unable to delete." + ex.getMessage(), ex);
            isSuccess = false;
        }
       System.out.println("LEFT | CompanyUserLogic.deleteCompanyUser()");
        return isSuccess;
    }   
    
    @Override
    public HashMap<String, UserData> getCOM_SERVICEEmployeesMap(String depCode, String empId) throws SBLException {
        System.out.println("ENTERED | CompanyUserLogic.getCOM_SERVICEEmployeesMap()");
        HttpURLConnection conn = null;
		URL url;
        String serviceUrl;
        BufferedReader br;
        String response = "";
        String responseFromBR;
        UserData uData;
        HashMap<String, UserData> COM_SERVICEEmployeesMap = new HashMap();
        
        try {
            /*
             Getting Users from COM_SERVICE for given DEP ID and put it to COM_SERVICEEmployeesMap based on given employee id.
             return COM_SERVICEEmployeesMap
             */

            System.out.println("MESSAGE | getCOM_SERVICEEmployeesMap  depCode : " + depCode);
            if (!APPUtills.isThisStringValid(depCode)) {
                System.out.println("ERROR   | Empty department code.");
                throw new SBLException("Empty department code.");
            }         
                       
          //getting logged user details from company service.	---------------------------------------------
            serviceUrl = ApplicationConstants.GET_ALL_COMPANY_USER_BY_DEP_ID + "/" + depCode;
            System.out.println("MESSAGE | Service URL : " + serviceUrl);
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

            while ((responseFromBR = br.readLine()) != null) {
            	System.out.println("MESSAGE | Response : " + responseFromBR);
            	response = responseFromBR;
            }
            
            
            JSONArray jsonArray = new JSONArray(response);
        	System.out.println("MESSAGE | jsonArray.length() : " + jsonArray.length());
        	for(int i = 0;i<jsonArray.length();i++){
        		JSONObject jSONObject = jsonArray.getJSONObject(i);
                
        		uData = new UserData();
            	uData.setUSER_TYPE(ApplicationConstants.BANK_DEP_USER);
            	
                uData.setUSER_ID(jSONObject.get("id") != null ? jSONObject.get("id").toString() : "");
                uData.setFIRST_NAME(jSONObject.get("user_first_name") != null ? jSONObject.get("user_first_name").toString() : "");
                uData.setLAST_NAME(jSONObject.get("user_last_name") != null ? jSONObject.get("user_last_name").toString() : "");
                uData.setDIV_CODE(jSONObject.get("user_dep_id") != null ? jSONObject.get("user_dep_id").toString() : "");
                uData.setDIV_NAME(jSONObject.get("user_dep_name") != null ? jSONObject.get("user_dep_name").toString() : "");
                uData.setSECURITY_CLASS(jSONObject.get("userRole") != null ? jSONObject.get("userRole").toString() : "");
                uData.setAD_USER_ID(jSONObject.get("user_id") != null ? jSONObject.get("user_id").toString() : "");
                
                COM_SERVICEEmployeesMap.put(uData.getAD_USER_ID(), uData);
        	}
            
        } catch (SBLException ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            throw new SBLException(ex.getMessage());
            //return UPMEmployeesMap;
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            throw new SBLException("Unable to get employee from company service");
            //return UPMEmployeesMap;
        }

        System.out.println("LEFT    | CompanyUserLogic.getCOM_SERVICEEmployeesMap()");
        return COM_SERVICEEmployeesMap;
    }
    
    
    @Override
    public HashMap<String, DivInfo> getAllDepInfo() throws SBLException {
        System.out.println("ENTERED | CompanyUserLogic.getAllDepInfo()");
        HttpURLConnection conn = null;
		URL url;
        String serviceUrl;
        BufferedReader br;
        String response,responseFromBR;
        DivInfo info;
        HashMap<String, DivInfo> divInfoMap = new HashMap();//to store sol infomation
        
        try {                          
            //getting logged user details from company service.	---------------------------------------------
            serviceUrl = ApplicationConstants.GET_ALL_DEPARTMRNTS;
            System.out.println("MESSAGE | Service URL : " + serviceUrl);
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
            while ((responseFromBR = br.readLine()) != null) {
            	response = responseFromBR;
            	System.out.println("MESSAGE | Response : " + response);            	
            }
            
            
        	JSONArray jsonArray = new JSONArray(response);
        	System.out.println("MESSAGE | jsonArray.length() : " + jsonArray.length());
        	for(int i = 0;i<jsonArray.length();i++){
        		JSONObject jSONObject = jsonArray.getJSONObject(i);
                
                info = new DivInfo();
                info.setDivId(jSONObject.get("depId") != null ? jSONObject.get("depId").toString() : "");
                info.setName(jSONObject.get("dep_name") != null ? jSONObject.get("dep_name").toString() : "");
                
                divInfoMap.put(info.getDivId(), info);
        	}
            
        }catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            throw new SBLException("Unable to get employee from company service");
            //return UPMEmployeesMap;
        }

        System.out.println("LEFT    | CompanyUserLogic.getAllDepInfo()");
        return divInfoMap;
    }
}
