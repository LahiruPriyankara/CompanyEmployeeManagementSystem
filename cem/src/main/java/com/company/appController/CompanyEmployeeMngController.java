package com.company.appController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company.bl.CompanyUserLogicLocal;
import com.company.common.APPUtills;
import com.company.common.ApplicationConstants;
import com.company.common.ObjectManager;
import com.company.common.SBLException;
import com.company.dto.CompanyUserMaster;
import com.company.dto.CompanyUserTmp;
import com.company.models.CompanyUserModel;
import com.company.models.DivInfo;
import com.company.models.UserData;

@Controller
@ComponentScan(basePackages = {"com.company.bl"})
public class CompanyEmployeeMngController {
	//private static final Log log = LogFactory.getLog(CompanyEmployeeMngController.class);
	@Autowired
    private CompanyUserLogicLocal companyUserLogic;
	private ObjectManager objManager;
	
	@RequestMapping(value = { "/CompanyDepEmployee/ExistingEmp" })
	public ModelAndView getAllEmployeeList(HttpServletRequest req, HttpServletResponse resp,HttpSession session){		
		//UserData uData;
        Map<String, CompanyUserModel> companyEmployeesMap = new HashMap();
        Map<String, CompanyUserModel> COM_SERVICEEmployeesMap = new HashMap();
        Map<String, CompanyUserModel> cemActiveUsers = new HashMap();//to hold CEM Active User AS KEY => employeeID: VALUE => UserData Object.
        Map<String, CompanyUserModel> cemInactiveUsers = new HashMap();//to hold CEM Inactive User AS KEY => employeeID: VALUE => UserData Object.
        Map<String, CompanyUserModel> cemUsersMisMatchWithCOM_SERVICE = new HashMap();//to hold CEM Users who are not match with UPM.
        Map<String, CompanyUserModel> cemNotAvailableUsers = new HashMap();//to hold Users who are not in CEM(They are only in UPM).
        Map<String, DivInfo> divInfoMap = new HashMap();//to store sol infomation
        CompanyUserModel model = null;
        String depCode = "";
        Map<String, CompanyUserModel> cemUsersMatchWithCOM_SERVICE = new HashMap();//to hold DVM Users who are match with UPM.For Authorize view.
        ObjectManager objManager = null;
        UserData userData;
        try {
            /*
            /* 
            --------------- MASTER DATA --------------
            Getting CEM user for logged user DEP id – MAP- bankEmployeesMap
            Getting COM_SERVICE user for logged user DEP id – MAP =>List-UPMEmployeesList
            LOOP UPM ser:
                    IF it is in DVM
                        TRUE:
                            check it and separate to dvmUsersMisMatchWithUPM,dvmActiveUsers,dvmInactiveUsers
                        FALSE:
                            IF it is in DVM with another SOL
                                TRUE:
                                    add to dvmUsersMisMatchWithUPM as MODIFY record
                                FALSE:
                                    add to dvmNotAvailableUsers as NEW record
            END LOOP  
             */
        	objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");
        	
        	
        	objManager.remove("divInfoMap");
            //objManager.remove("upmBankEmployeesMap");
            objManager.remove("cemActiveUsers");
            objManager.remove("cemInactiveUsers");
            objManager.remove("cemNotAvailableUsers");
            objManager.remove("companyEmployeesMap");
            objManager.remove("cemUsersMisMatchWithCOM_SERVICE");
            //objManager.remove("cemUsersMatchWithCOM_SERVICE");

            
            //depCode = userData.getSOL_ID();
            depCode = userData.getDIV_CODE();
            System.out.println("MESSAGE | SOL ID : " + depCode);
            if (!APPUtills.isThisStringValid(depCode)) {
                System.out.println("ERROR | Empty DIV ID recieved.You do not have permission to view this page.");
                throw new SBLException("You do not have permission to view this page.");
            }
            
            System.out.println("MESSAGE | MASTER DATA..");
            if (true) {//Only for common Admin
            	divInfoMap = companyUserLogic.getAllDepInfo();
                objManager.put("divInfoMap", divInfoMap, ApplicationConstants.SCOPE_BANK_USER);
            } 
            
            companyEmployeesMap = companyUserLogic.getCompanyUsers(ApplicationConstants.MASTER_DATA, null, depCode);
            HashMap<String, UserData> UPMEmployeesMap = companyUserLogic.getCOM_SERVICEEmployeesMap(depCode, null);
            List<UserData> UPMEmployeesList = new ArrayList<>(UPMEmployeesMap.values());
            System.out.println("MESSAGE | UPMEmployeesList.size() : " + UPMEmployeesList.size());
            for (UserData uData : UPMEmployeesList) {

                model = CompanyUserModel.userDataToModel(uData);
                model.setActionType(ApplicationConstants.ACTION_TYPE_NEW);
                COM_SERVICEEmployeesMap.put(uData.getAD_USER_ID(), model);

                if (companyEmployeesMap.get(uData.getAD_USER_ID()) != null) {//That user is available in DVM
                    //System.out.println("MESSAGE | That user is available in DVM");
                    model = companyEmployeesMap.get(uData.getAD_USER_ID());
                    model.setActionType(ApplicationConstants.ACTION_TYPE_MODIFY);

                    if (!model.getCompanyUserFirstName().equalsIgnoreCase(uData.getFIRST_NAME()) || !model.getCompanyUserLastName().equalsIgnoreCase(uData.getLAST_NAME()) || !model.getCompanyUserDepName().equalsIgnoreCase(uData.getDIV_NAME())) {
                    	cemUsersMisMatchWithCOM_SERVICE.put(uData.getAD_USER_ID(), model);//dvmUsersMisMatchWithUPM.put(uData.getAD_USER_ID(), CompanyUserModel.userDataToModel(uData));
                    } else if (model.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_ACTIVE)) {
                    	cemActiveUsers.put(uData.getAD_USER_ID(), model);
                    } else if (model.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_INACTIVE)) {
                    	cemInactiveUsers.put(uData.getAD_USER_ID(), model);
                    }
                } else {
                    //System.out.println("MESSAGE | That user is not available in DVM");
                    model = CompanyUserModel.userDataToModel(uData);

                    Map<String, CompanyUserModel> bEMap = companyUserLogic.getCompanyUsers(ApplicationConstants.MASTER_DATA, Arrays.asList(uData.getAD_USER_ID()), null);
                    List<CompanyUserModel> bEObjList = new ArrayList<>(bEMap.values());
                    if (bEObjList.size() > 0) {
                        CompanyUserModel dvmMsterModel = (CompanyUserModel) bEObjList.get(0);
                        cemUsersMisMatchWithCOM_SERVICE.put(dvmMsterModel.getCompanyUserEmpId(), dvmMsterModel);
                        companyEmployeesMap.put(dvmMsterModel.getCompanyUserEmpId(), dvmMsterModel);
                    } else {
                        model.setActionType(ApplicationConstants.ACTION_TYPE_NEW);
                        cemNotAvailableUsers.put(uData.getAD_USER_ID(), model);
                    }

                }
            }
            objManager.put("COM_SERVICEEmployeesMap", COM_SERVICEEmployeesMap, ApplicationConstants.SCOPE_BANK_USER);
            objManager.put("cemActiveUsers", cemActiveUsers, ApplicationConstants.SCOPE_BANK_USER);
            objManager.put("cemInactiveUsers", cemInactiveUsers, ApplicationConstants.SCOPE_BANK_USER);
            objManager.put("cemNotAvailableUsers", cemNotAvailableUsers, ApplicationConstants.SCOPE_BANK_USER);
            objManager.put("companyEmployeesMap", companyEmployeesMap, ApplicationConstants.SCOPE_BANK_USER);
            objManager.put("cemUsersMisMatchWithCOM_SERVICE", cemUsersMisMatchWithCOM_SERVICE, ApplicationConstants.SCOPE_BANK_USER);
            //objManager.put("cemUsersMatchWithCOM_SERVICE", cemUsersMatchWithCOM_SERVICE, ApplicationConstants.SCOPE_BANK_USER);
            
            System.out.println("MESSAGE | COM_SERVICEEmployeesMap.size() : " + COM_SERVICEEmployeesMap.size() + " cemActiveUsers.size() : " + cemActiveUsers.size() + " cemInactiveUsers.size() : " + cemInactiveUsers.size() + " cemNotAvailableUsers.size() : " + cemNotAvailableUsers.size());            
            System.out.println("MESSAGE | bankEmployeesMap.size() : " + companyEmployeesMap.size() + " cemUsersMisMatchWithCOM_SERVICE.size() : " + cemUsersMisMatchWithCOM_SERVICE.size() + " cemUsersMatchWithCOM_SERVICE.size() : " + cemUsersMatchWithCOM_SERVICE.size());
        
        	
        	
        } catch (SBLException ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            return new ModelAndView("employee/existingEmp");
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            return new ModelAndView("employee/existingEmp");
        }

        System.out.println("LEFT    | CompanyEmployeeCommonViewController.getAllEmployeeList()");
        return new ModelAndView("employee/existingEmp");
		
	}
	
	@RequestMapping(value = { "/CompanyEmployee/PendingEmp" })
	public ModelAndView getEmployeeDetailsForCommonView(HttpServletRequest req, HttpServletResponse resp,HttpSession session){
		System.out.println("ENTERED | CompanyEmployeeCommonViewController.getEmployeeDetailsForCommonView()");
		//UserData uData;
        Map<String, HashMap> upmEmployeeMap = new HashMap();
        Map<String, CompanyUserModel> companyEmployeesMap = new HashMap();
        Map<String, CompanyUserModel> cemUsersMisMatchWithCOM_SERVICE = new HashMap();//to hold DVM Users who are not match with UPM.
        String depCode = "";
        Map<String, CompanyUserModel> cemUsersMatchWithCOM_SERVICE = new HashMap();//to hold DVM Users who are match with UPM.For Authorize view.
        ObjectManager objManager = null;
        UserData userData;
        try {
            /*
           ---------------- TEMP DATA ---------------
            Getting DVM bank user for logged user SOL id – MAP- bankEmployeesMap.If SOL id is not given, All temp pending and rejected record will be fetch(This is for common eneterer and authorizer)
            Getting UPM bank user for logged user SOL id – MAP =>List-UPMEmployeesList
            upmEmployeeMap - to hold all the upm emloyee as MAP(sol id,employee map)
            LOOP DVM user:
                    IF sol id is in upmEmployeeMap
                        FALSE:
                            take employee from upm for that sol id and put it to  upmEmployeeMap

                        UPMEmployeesMap(empId,Employee) = take upm map for that sol id from upmEmployeeMap
                        IF DVM user is available or not in UPMEmployeesMap - by passing empId
                            TRUE:
                                add to dvmUsersMatchWithUPM - can be verified or rejected or deleted
                            FALSE:
                                add to dvmUsersMisMatchWithUPM - can be deleted only
            END LOOP            
             */
        	objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");
        	
        	
            //objManager.remove("upmBankEmployeesMap");
            objManager.remove("companyEmployeesMap");            
            objManager.remove("cemUsersMisMatchWithCOM_SERVICE");
            objManager.remove("cemUsersMatchWithCOM_SERVICE");

            //depCode = userData.getSOL_ID();
            depCode = userData.getDIV_CODE();
            System.out.println("MESSAGE | SOL ID : " + depCode);
            if (!APPUtills.isThisStringValid(depCode)) {
                System.out.println("ERROR | Empty DIV ID recieved.You do not have permission to view this page.");
                throw new SBLException("You do not have permission to view this page.");
            }
            
            System.out.println("MESSAGE | TEMP DATA..");
            //depCode = "861";// if common enter - depCode = ""
            companyEmployeesMap = companyUserLogic.getCompanyUsers(ApplicationConstants.TEMP_DATA, null, depCode);
            List<CompanyUserModel> bankEmployeesList = new ArrayList<>(companyEmployeesMap.values());//DVM Temp Employee list
            System.out.println("MESSAGE | bankEmployeesList.size() : " + bankEmployeesList.size());
            for (CompanyUserModel bankUserModel : bankEmployeesList) {
                if (upmEmployeeMap.get(bankUserModel.getCompanyUserDivId()) == null) {
                    upmEmployeeMap.put(bankUserModel.getCompanyUserDivId(), companyUserLogic.getCOM_SERVICEEmployeesMap(bankUserModel.getCompanyUserDivId(), null));
                }
                Map<String, UserData> UPMEmployeesMap = upmEmployeeMap.get(bankUserModel.getCompanyUserDivId());
                if (UPMEmployeesMap.get(bankUserModel.getCompanyUserEmpId()) != null) {
                	cemUsersMatchWithCOM_SERVICE.put(bankUserModel.getCompanyUserEmpId(), bankUserModel);
                } else {
                	cemUsersMisMatchWithCOM_SERVICE.put(bankUserModel.getCompanyUserEmpId(), bankUserModel);
                    //bankEmployeesMap.remove(bankUserModel.getBankUserEmpId());
                }

            }
            System.out.println("MESSAGE | cemUsersMatchWithCOM_SERVICE.size() : " + cemUsersMatchWithCOM_SERVICE.size() + " cemUsersMisMatchWithCOM_SERVICE.size() : " + cemUsersMisMatchWithCOM_SERVICE.size());
     
            objManager.put("companyEmployeesMap", companyEmployeesMap, ApplicationConstants.SCOPE_BANK_USER);
            objManager.put("cemUsersMisMatchWithCOM_SERVICE", cemUsersMisMatchWithCOM_SERVICE, ApplicationConstants.SCOPE_BANK_USER);
            objManager.put("cemUsersMatchWithCOM_SERVICE", cemUsersMatchWithCOM_SERVICE, ApplicationConstants.SCOPE_BANK_USER);

            
        } catch (SBLException ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            //return new ModelAndView("employee/existingEmp");
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            //return new ModelAndView("employee/existingEmp");
        }

        System.out.println("LEFT    | CompanyEmployeeCommonViewController.getEmployeeDetailsForCommonView()");
        return new ModelAndView("employee/existingEmp");
	}
		
	@RequestMapping(value = { "/CompanyEmployee/SearchEmp"})
	public ModelAndView getSearchBankEmp(HttpServletRequest req, HttpServletResponse resp,HttpSession session){		
		//UserData uData;
        Map<String, CompanyUserModel> companyEmployeesMap = new HashMap();
        Map<String, CompanyUserModel> COM_SERVICEEmployeesMap = new HashMap();
        Map<String, CompanyUserModel> cemActiveUsers = new HashMap();//to hold CEM Active User AS KEY => employeeID: VALUE => UserData Object.
        Map<String, CompanyUserModel> cemInactiveUsers = new HashMap();//to hold CEM Inactive User AS KEY => employeeID: VALUE => UserData Object.
        Map<String, CompanyUserModel> cemUsersMisMatchWithCOM_SERVICE = new HashMap();//to hold CEM Users who are not match with UPM.
        Map<String, CompanyUserModel> cemNotAvailableUsers = new HashMap();//to hold Users who are not in CEM(They are only in UPM).
        Map<String, DivInfo> divInfoMap = new HashMap();//to store sol infomation
        CompanyUserModel model = null;
        HashMap<String, UserData> userDataMap = new HashMap<>();
        List<UserData> userDataList = new ArrayList<>();
        ObjectManager objManager = null;
        UserData userData;
        try {
            /*
           Getting CEM user for logged user DEP id – MAP- bankEmployeesMap
            IF depCode NULL AND empId NOT NULL
                Special case to fetch data from a data base link
                add data to UPMEmployeesMap
            ELSE
                Getting DEP bank user for logged user DEP id – MAP-UPMEmployeesMap =>List-UPMEmployeesList
            LOOP UPM users:
                    IF it is in CEM
                        TRUE:
                            check it and separate to dvmUsersMisMatchWithUPM,dvmActiveUsers,dvmInactiveUsers
                        FALSE:
                            IF it is in DVM with another SOL
                                TRUE:
                                    add to dvmUsersMisMatchWithUPM as MODIFY record
                                FALSE:
                                    add to dvmNotAvailableUsers as NEW record
            END LOOP 
             */
        	objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");
        	
            objManager.remove("COM_SERVICEEmployeesMap");
            objManager.remove("cemActiveUsers");
            objManager.remove("cemInactiveUsers");
            objManager.remove("cemNotAvailableUsers");
            objManager.remove("cemUsersMisMatchWithCOM_SERVICE");
            

            divInfoMap = objManager.get("divInfoMap") != null ? (HashMap) objManager.get("divInfoMap") : new HashMap(); //to store sol infomation

            String depCode = req.getParameter("sol");
            String empId = req.getParameter("empId");
            System.out.println("MESSAGE | depCode : " + depCode + " ,empId : " + empId);

            if (!APPUtills.isThisStringValid(depCode) && !APPUtills.isThisStringValid(empId)) {
               System.out.println("ERROR   | Please select values to fetch data. ");
                throw new SBLException("Please select values to fetch data.");
            }

            //Only For showing search criteria...............
            String criteria = divInfoMap.get(depCode) != null ? ((DivInfo) divInfoMap.get(depCode)).getName() : "";
            criteria = "DEPARTMENT :" + criteria + "  |  EMPLOYEE ID :" + empId;
            System.out.println("MESSAGE | criteria : " + criteria);
            objManager.put("criteria", criteria, ApplicationConstants.SCOPE_BANK_USER);
            //.....................

            companyEmployeesMap = companyUserLogic.getCompanyUsers(ApplicationConstants.MASTER_DATA, empId.equalsIgnoreCase("") ? null : Arrays.asList(empId), depCode);

            if (!APPUtills.isThisStringValid(depCode) && APPUtills.isThisStringValid(empId)) {
                System.out.println("Special case...Needs to data base link");
            } else {
            	userDataMap = companyUserLogic.getCOM_SERVICEEmployeesMap(depCode, empId);
            }

            userDataList = new ArrayList<>(userDataMap.values());
            System.out.println("MESSAGE | UPMEmployeesList.size() : " + userDataList.size());
            for (UserData uData : userDataList) {
                model = CompanyUserModel.userDataToModel(uData);
                model.setActionType(ApplicationConstants.ACTION_TYPE_NEW);
                COM_SERVICEEmployeesMap.put(uData.getAD_USER_ID(), model);

                if (companyEmployeesMap.get(uData.getAD_USER_ID()) != null) {//That user is available in DVM
                    //System.out.println("MESSAGE | That user is available in DVM");
                    model = companyEmployeesMap.get(uData.getAD_USER_ID());
                    model.setActionType(ApplicationConstants.ACTION_TYPE_MODIFY);

                    if (!model.getCompanyUserFirstName().equalsIgnoreCase(uData.getFIRST_NAME()) || !model.getCompanyUserLastName().equalsIgnoreCase(uData.getLAST_NAME()) || !model.getCompanyUserDepName().equalsIgnoreCase(uData.getDIV_NAME())) {
                    	cemUsersMisMatchWithCOM_SERVICE.put(uData.getAD_USER_ID(), model);//dvmUsersMisMatchWithUPM.put(uData.getAD_USER_ID(), CompanyUserModel.userDataToModel(uData));
                    } else if (model.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_ACTIVE)) {
                        cemActiveUsers.put(uData.getAD_USER_ID(), model);
                    } else if (model.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_INACTIVE)) {
                        cemInactiveUsers.put(uData.getAD_USER_ID(), model);
                    }
                } else {
                    //System.out.println("MESSAGE | That user is not available in DVM");
                    model = CompanyUserModel.userDataToModel(uData);

                    Map<String, CompanyUserModel> bEMap = companyUserLogic.getCompanyUsers(ApplicationConstants.MASTER_DATA, Arrays.asList(uData.getAD_USER_ID()), null);
                    List<CompanyUserModel> bEObjList = new ArrayList<>(bEMap.values());
                    if (bEObjList.size() > 0) {
                    	CompanyUserModel dvmMsterModel = (CompanyUserModel) bEObjList.get(0);
                    	cemUsersMisMatchWithCOM_SERVICE.put(dvmMsterModel.getCompanyUserEmpId(), dvmMsterModel);
                    } else {
                        model.setActionType(ApplicationConstants.ACTION_TYPE_NEW);
                        cemNotAvailableUsers.put(uData.getAD_USER_ID(), model);
                    }

                }
            }

            objManager.put("COM_SERVICEEmployeesMap", COM_SERVICEEmployeesMap, ApplicationConstants.SCOPE_BANK_USER);
            objManager.put("cemActiveUsers", cemActiveUsers, ApplicationConstants.SCOPE_BANK_USER);
            objManager.put("cemInactiveUsers", cemInactiveUsers, ApplicationConstants.SCOPE_BANK_USER);
            objManager.put("cemUsersMisMatchWithCOM_SERVICE", cemUsersMisMatchWithCOM_SERVICE, ApplicationConstants.SCOPE_BANK_USER);
            objManager.put("cemNotAvailableUsers", cemNotAvailableUsers, ApplicationConstants.SCOPE_BANK_USER);
        	
        	
        } catch (SBLException ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            //return new ModelAndView("employee/existingEmp");
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            //return new ModelAndView("employee/existingEmp");
        }

        System.out.println("LEFT    | CompanyEmployeeCommonViewController.getAllEmployeeList()");
        return new ModelAndView("employee/existingEmp");
		
	}
		
	@RequestMapping(value = { "/CompanyEmployee/ExistingEmpDetails"})
	public ModelAndView ExistingEmpDetails(HttpServletRequest req, HttpServletResponse resp,HttpSession session){		
		System.out.println("ENTERED | CompanyEmployeeMngController.getEmployeeDetails()");
		CompanyUserModel CEMmodelMaster;
        //CompanyUserModel CEMmodelTemp;
        CompanyUserModel COM_SERVICEmodel;
        Map<String, CompanyUserModel> companyEmployeesMap = new HashMap();
        Map<String, CompanyUserModel> COM_SERVICEEmployeesMap = new HashMap();
        ObjectManager objManager = null;
        UserData userData;
        try {
        	 /*
            //------------ MASTER ------------ 
            fetching DVM master from objManager - DVMmodelMaster
            fetching UPM from objManager - UPMmodel
             */
        	objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");

            objManager.remove("CEMmodelMaster");
            //objManager.remove("CEMmodelTemp");
            objManager.remove("COM_SERVICEmodel");

            String empId = req.getParameter("id");
            System.out.println("MESSAGE | Employee ID : " + empId);
            if (!APPUtills.isThisStringValid(empId)) {
                System.out.println("ERROR   | Id is not recived.");
                throw new SBLException("Id is not recived.");
            }
            
            companyEmployeesMap = objManager.get("bankEmployeesMap") != null ? (HashMap) objManager.get("bankEmployeesMap") : new HashMap();
        	COM_SERVICEEmployeesMap = objManager.get("upmBankEmployeesMap") != null ? (HashMap) objManager.get("upmBankEmployeesMap") : new HashMap();

        	CEMmodelMaster = companyEmployeesMap.get(empId);
        	COM_SERVICEmodel = COM_SERVICEEmployeesMap.get(empId);

            if (CEMmodelMaster == null && COM_SERVICEmodel != null) {
            	CEMmodelMaster = COM_SERVICEmodel;
            } else if (CEMmodelMaster == null && COM_SERVICEmodel == null) {
                System.out.println("ERROR | Can not found a object for " + empId);
                throw new SBLException("Can not found a object.");
            }

            System.out.println("MESSAGE | Successfully found objects.");
            objManager.put("CEMmodelMaster", CEMmodelMaster, ApplicationConstants.SCOPE_BANK_USER);
            objManager.put("UPMmodel", COM_SERVICEmodel, ApplicationConstants.SCOPE_BANK_USER);
        } catch (SBLException ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            //return new ModelAndView("employee/existingEmp");
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            //return new ModelAndView("employee/existingEmp");
        }

        System.out.println("LEFT    | CompanyEmployeeCommonViewController.getAllEmployeeList()");
        return new ModelAndView("employee/existingEmp");
		
	}	
	
	@RequestMapping(value = { "/CompanyEmployee/PendingEmpDetails"})
	public ModelAndView PendingEmpDetails(HttpServletRequest req, HttpServletResponse resp,HttpSession session){		
		System.out.println("ENTERED | CompanyEmployeeMngController.getEmployeeDetails()");
		CompanyUserModel CEMmodelMaster;
        CompanyUserModel CEMmodelTemp;
        CompanyUserModel COM_SERVICEmodel;
        Map<String, CompanyUserModel> companyEmployeesMap = new HashMap();
        Map<String, CompanyUserModel> COM_SERVICEEmployeesMap = new HashMap();
        ObjectManager objManager = null;
        UserData userData;
        try {
        	 /*
            //------------ TEMP ------------ 
            fetching DVM temp from objManager - DVMmodelTemp
            fetching DVM master from Database(by giving empId and solId, take it from temp object - DVMmodelTemp)
            fetching UPM from objManager - UPMmodel
             */
        	
        	objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");

            objManager.remove("CEMmodelMaster");
            objManager.remove("CEMmodelTemp");
            objManager.remove("COM_SERVICEmodel");

            String empId = req.getParameter("id");
            System.out.println("MESSAGE | Employee ID : " + empId);
            if (!APPUtills.isThisStringValid(empId)) {
                System.out.println("ERROR   | Id is not recived.");
                throw new SBLException("Id is not recived.");
            }
            
            System.out.println("MESSAGE | Going to fetch from DVM temp.");
            companyEmployeesMap = objManager.get("bankEmployeesMap") != null ? (HashMap) objManager.get("bankEmployeesMap") : new HashMap();
            CEMmodelTemp = companyEmployeesMap.get(empId);

            if (CEMmodelTemp == null) {
                System.out.println("ERROR | Can not found a object for " + empId);
                throw new SBLException("Can not found a object.");
            }

            System.out.println("MESSAGE | Going to fetch from DVM master.");
            Map<String, CompanyUserModel> dfumsMap = companyUserLogic.getCompanyUsers(ApplicationConstants.MASTER_DATA, Arrays.asList(empId), null);//String tableType, List<String> ids, String depCode               
            CEMmodelMaster = dfumsMap.get(empId);

            System.out.println("MESSAGE | Going to fetch from UPM.");
            COM_SERVICEEmployeesMap = objManager.get("upmBankEmployeesMap") != null ? (HashMap) objManager.get("upmBankEmployeesMap") : new HashMap();
            COM_SERVICEmodel = COM_SERVICEEmployeesMap.get(empId);

            System.out.println("MESSAGE | Successfully found objects.");

            objManager.put("CEMmodelMaster", CEMmodelMaster, ApplicationConstants.SCOPE_BANK_USER);
            objManager.put("CEMmodelTemp", CEMmodelTemp, ApplicationConstants.SCOPE_BANK_USER);
            objManager.put("COM_SERVICEmodel", COM_SERVICEmodel, ApplicationConstants.SCOPE_BANK_USER);
        } catch (SBLException ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            //return new ModelAndView("employee/existingEmp");
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            //return new ModelAndView("employee/existingEmp");
        }

        System.out.println("LEFT    | CompanyEmployeeCommonViewController.getAllEmployeeList()");
        return new ModelAndView("employee/existingEmp");
		
	}
		
	@RequestMapping(value = { "CompanyEmployee/SaveCompanyEmp"})
	public ModelAndView SaveCompanyEmp(HttpServletRequest req, HttpServletResponse resp,HttpSession session){	
		System.out.println("ENTERED | CompanyEmployeeMngController.saveEmployeeDetails()");
        CompanyUserModel model;
        List<CompanyUserModel> bankUserModels = new ArrayList();
        boolean isSuccess = false;
        String eventAction = "", eventDesc = "", tempObjectToString = "", masterObjectToString = "";
        Map<String, CompanyUserModel> companyEmployeesMap = new HashMap();
        Map<String, CompanyUserModel> COM_SERVICEEmployeesMap = new HashMap();
        List<String> IdsAryList = new ArrayList<>();
        ObjectManager objManager = null;
        UserData userData;
        try {
        	
        	 /*
            take object from objManager and add to bankUserModels List to save
            Update event log
             */
        	
        	objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");
        	
        	model = objManager.get("CEMmodelMaster") != null ? (CompanyUserModel) objManager.get("CEMmodelMaster") : new CompanyUserModel();

            if (model == null) {
                System.out.println("ERROR | Can not find a object..");
                throw new SBLException("Can not find a object." + ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
            }

            String employeeId = model.getCompanyUserEmpId();
            System.out.println("MESSAGE | employeeId :" + employeeId + ".");
            if (validateFieldsFillData(model.getCompanyUserEmpId(), false, req)) {
                model = fillObject(model, false, req);
                System.out.println("MESSAGE | (NotBulk) model.toString() : " + model.toString());
                bankUserModels.add(model);
            }
            if (bankUserModels.size() <= 0) {
                throw new SBLException("No reviced objects to save.");
            }
            isSuccess = companyUserLogic.saveCompanyUser(bankUserModels, IdsAryList, userData);
/*
            if (isSuccess) {

                for (CompanyUserModel userModel : bankUserModels) {
                    eventAction = ApplicationConstants.actionTypeDesc(userModel.getActionType());
                    eventDesc = "Bank user temp Id : " + userModel.getBankUserTmpId() + ", Save - ";
                    masterObjectToString = "";
                    tempObjectToString = ((DvmBankUserTmp) userModel.modelToObject(ApplicationConstants.TEMP_DATA)).toString();
                    // Write to event log table.
                    EventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.BANK_EMPLOYEE, eventAction, eventDesc + "Successful. ", masterObjectToString, tempObjectToString, ApplicationConstants.EVENTSUCCESSFUL);
                }
                req.setAttribute("rtnMsg", "Successfully Save.");
            }
*/
            //eventAction = "";
            //eventDesc = "Save bank user in temp table.";
            //EventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.BANK_EMPLOYEE, eventAction, eventDesc + "Successful. ", "", "", ApplicationConstants.EVENTSUCCESSFUL);
            req.setAttribute("rtnMsg", "Successfully save.");
            
        } catch (SBLException ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            //return new ModelAndView("employee/existingEmp");
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            //return new ModelAndView("employee/existingEmp");
        }

        System.out.println("LEFT    | CompanyEmployeeCommonViewController.getAllEmployeeList()");
        return new ModelAndView("employee/existingEmp");
		
	}
	
	@RequestMapping(value = { "/CompanyEmployee/SaveBulkEmp"})
	public ModelAndView SaveBulkEmp(HttpServletRequest req, HttpServletResponse resp,HttpSession session){	
		System.out.println("ENTERED | CompanyEmployeeMngController.saveEmployeeDetails()");
        CompanyUserModel model;
        List<CompanyUserModel> bankUserModels = new ArrayList();
        boolean isSuccess = false;
        String eventAction = "", eventDesc = "", tempObjectToString = "", masterObjectToString = "";
        Map<String, CompanyUserModel> companyEmployeesMap = new HashMap();
        Map<String, CompanyUserModel> COM_SERVICEEmployeesMap = new HashMap();
        List<String> IdsAryList = new ArrayList<>();
        ObjectManager objManager = null;
        UserData userData;
        try {
        	
        	 /*
             IF addNewEmpIdsAry.length > 1 || !addNewEmpIdsAry[0].equalsIgnoreCase("")
                        TRUE 
                            take object from upmBankEmployeesMap and add to bankUserModels List to save
                    IF modifyEmpIdsAry.length > 1 || !modifyEmpIdsAry[0].equalsIgnoreCase("")
                        TRUE
                            take object from bankEmployeesMap and add to bankUserModels List to save
            Update event log
             */
        	objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");

            String addNewEmpIds = req.getParameter("newIds");// 152,534,......
            String modifyEmpIds = req.getParameter("modifyIds");// 127,534,......
            System.out.println("MESSAGE | addNewEmpIds : " + addNewEmpIds);
            System.out.println("MESSAGE | modifyEmpIds : " + modifyEmpIds);

            companyEmployeesMap = objManager.get("companyEmployeesMap") != null ? (HashMap) objManager.get("companyEmployeesMap") : new HashMap();
            COM_SERVICEEmployeesMap = objManager.get("COM_SERVICEEmployeesMap") != null ? (HashMap) objManager.get("COM_SERVICEEmployeesMap") : new HashMap();

            String[] addNewEmpIdsAry = addNewEmpIds.split(",");
            if (addNewEmpIdsAry.length > 1 || !addNewEmpIdsAry[0].equalsIgnoreCase("")) {
                for (String employeeId : addNewEmpIdsAry) {
                    System.out.println("MESSAGE | employeeId :" + employeeId + ".");
                    model = COM_SERVICEEmployeesMap.get(employeeId);
                    if (model == null) {
                        System.out.println("ERROR | Can not find a object..");
                        throw new SBLException("Can not find a object." + ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
                    }
                    if (validateFieldsFillData(model.getCompanyUserEmpId(), true, req)) {
                        model = fillObject(model, true, req);
                        System.out.println("MESSAGE | (addNewEmpIdsAry) model.toString() : " + model.toString());
                        bankUserModels.add(model);
                        IdsAryList.add(employeeId);
                    }

                }
            }
            String[] modifyEmpIdsAry = modifyEmpIds.split(",");
            if (modifyEmpIdsAry.length > 1 || !modifyEmpIdsAry[0].equalsIgnoreCase("")) {
                for (String employeeId : modifyEmpIdsAry) {
                    System.out.println("MESSAGE | employeeId :" + employeeId + ". modifyEmpIdsAry.length : " + modifyEmpIdsAry.length + " . " + modifyEmpIdsAry[0]);
                    model = companyEmployeesMap.get(employeeId);
                    if (model == null) {
                        System.out.println("ERROR | Can not find a object.");
                        throw new SBLException("Can not find a object." + ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
                    }
                    if (validateFieldsFillData(model.getCompanyUserEmpId(), true, req)) {
                        model = fillObject(companyEmployeesMap.get(employeeId), true, req);
                        System.out.println("MESSAGE | (modifyEmpIdsAry) model.toString() : " + model.toString());
                        bankUserModels.add(model);
                        IdsAryList.add(employeeId);
                    }

                }
            }
            if (bankUserModels.size() <= 0) {
                throw new SBLException("No reviced objects to save.");
            }
            isSuccess = companyUserLogic.saveCompanyUser(bankUserModels, IdsAryList, userData);
/*
            if (isSuccess) {

                for (CompanyUserModel userModel : bankUserModels) {
                    eventAction = ApplicationConstants.actionTypeDesc(userModel.getActionType());
                    eventDesc = "Bank user temp Id : " + userModel.getBankUserTmpId() + ", Save - ";
                    masterObjectToString = "";
                    tempObjectToString = ((DvmBankUserTmp) userModel.modelToObject(ApplicationConstants.TEMP_DATA)).toString();
                    // Write to event log table.
                    EventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.BANK_EMPLOYEE, eventAction, eventDesc + "Successful. ", masterObjectToString, tempObjectToString, ApplicationConstants.EVENTSUCCESSFUL);
                }
                req.setAttribute("rtnMsg", "Successfully Save.");
            }
*/
            //eventAction = "";
            //eventDesc = "Save bank user in temp table.";
            //EventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.BANK_EMPLOYEE, eventAction, eventDesc + "Successful. ", "", "", ApplicationConstants.EVENTSUCCESSFUL);
            req.setAttribute("rtnMsg", "Successfully save.");
            
        } catch (SBLException ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            //return new ModelAndView("employee/existingEmp");
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            //return new ModelAndView("employee/existingEmp");
        }

        System.out.println("LEFT    | CompanyEmployeeCommonViewController.getAllEmployeeList()");
        return new ModelAndView("employee/existingEmp");
		
	}
	
	private boolean validateFieldsFillData(String empId, boolean isBulk, HttpServletRequest req) throws SBLException {
       System.out.println("ENTERED | CompanyEmployeeMngController.validateFields()");
        String errorStrg = "";
        boolean isValid = false;
        try {
            /*
            Request data Validation
             */

            if (!isBulk) {
                //UPM related values Begin ------------------------
                if (!APPUtills.isThisStringValid(req.getParameter("empId" + empId))) {
                    System.out.println("ERROR   | Employee ID can not be empty.");
                    errorStrg = APPUtills.isThisStringValid(errorStrg) ? errorStrg + ",Employee ID" : "Employee ID";
                }
                if (!APPUtills.isThisStringValid(req.getParameter("fName" + empId))) {
                    System.out.println("ERROR   | First name can not be empty.");
                    errorStrg = APPUtills.isThisStringValid(errorStrg) ? errorStrg + ",First Name" : "First Name";
                }
                if (!APPUtills.isThisStringValid(req.getParameter("lName" + empId))) {
                    System.out.println("ERROR   | Last name can not be empty.");
                    errorStrg = APPUtills.isThisStringValid(errorStrg) ? errorStrg + ",Last Name" : "Last Name";
                }
                if (!APPUtills.isThisStringValid(req.getParameter("solId" + empId))) {
                    System.out.println("ERROR   | SOL ID can not be empty.");
                    errorStrg = APPUtills.isThisStringValid(errorStrg) ? errorStrg + ",SOL ID" : "SOL ID";
                }
                if (!APPUtills.isThisStringValid(req.getParameter("depName" + empId))) {
                    System.out.println("ERROR   | Department name can not be empty.");
                    errorStrg = APPUtills.isThisStringValid(errorStrg) ? errorStrg + ",Department Name" : "Department Name";
                }
            }
            //UPM related values End ------------------------

            if (!APPUtills.isThisStringValid(req.getParameter("gender" + empId))) {
                System.out.println("ERROR   | Gender can not be empty.");
                errorStrg = APPUtills.isThisStringValid(errorStrg) ? errorStrg + ",Gender" : "Gender";
            }
            if (!APPUtills.isThisStringValid(req.getParameter("grade" + empId))) {
                System.out.println("ERROR   | Grade can not be empty.");
                errorStrg = APPUtills.isThisStringValid(errorStrg) ? errorStrg + ",Grade" : "Grade";
            }
            if (!APPUtills.isThisStringValid(req.getParameter("floor" + empId))) {
                System.out.println("ERROR   | Floor can not be empty.");
                errorStrg = APPUtills.isThisStringValid(errorStrg) ? errorStrg + ",Floor" : "Floor";
            }
            if (!APPUtills.isThisStringValid(req.getParameter("depExtention" + empId))) {
                System.out.println("ERROR   | Department Extention can not be empty.");
                errorStrg = APPUtills.isThisStringValid(errorStrg) ? errorStrg + ",Department Extention" : "Department Extention";
            }
            if (!APPUtills.isThisStringValid(req.getParameter("empExtention" + empId))) {
                System.out.println("ERROR   | Office Extention can not be empty.");
                errorStrg = APPUtills.isThisStringValid(errorStrg) ? errorStrg + ",Office Extention" : "Office Extention";
            }
            if (!APPUtills.isThisStringValid(req.getParameter("empEmail" + empId))) {
                System.out.println("ERROR   | Office Email can not be empty.");
                errorStrg = APPUtills.isThisStringValid(errorStrg) ? errorStrg + ",Office Email" : "Office Email";
            }
            if (!APPUtills.isThisStringValid(req.getParameter("nextPersonName" + empId))) {
                System.out.println("ERROR   | Contact Person Name can not be empty.");
                errorStrg = APPUtills.isThisStringValid(errorStrg) ? errorStrg + ",Contact Person Name" : "Contact Person Name";
            }
            if (!APPUtills.isThisStringValid(req.getParameter("nextPersonExtention" + empId))) {
                System.out.println("ERROR   | Contact Person Extention can not be empty.");
                errorStrg = APPUtills.isThisStringValid(errorStrg) ? errorStrg + ",Contact Person Extention" : "Contact Person Extention";
            }
            if (!APPUtills.isThisStringValid(req.getParameter("status" + empId))) {
                System.out.println("ERROR   | Status can not be empty.");
                errorStrg = APPUtills.isThisStringValid(errorStrg) ? errorStrg + ",Status" : "Status";
            }

            if (APPUtills.isThisStringValid(errorStrg)) {
                System.out.println("ERROR   | Validation faild.");
                throw new SBLException("Please fill required fields.<br>(<i>" + errorStrg + "</i>)");
            }

            isValid = true;
           System.out.println("MESSAGE | isValid : " + isValid);

        } catch (SBLException ex) {
            throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage() + "\n");
            throw new SBLException("Unable to validate employee details");
        }

       System.out.println("LEFT    |  CompanyEmployeeMngController.validateFields()");
        return isValid;
    }

    private CompanyUserModel fillObject(CompanyUserModel model, boolean isBulk, HttpServletRequest req) throws SBLException {
       System.out.println("ENTERED | CompanyEmployeeMngController.fillObject()");
        try {
            /*
            Filling request values to given CompanyUserModel
             */

            if (model == null) {
                System.out.println("ERROR | Null Object.");
                throw new SBLException("Null Object.");
            }
            String empId = model.getCompanyUserEmpId();

            Part filePart = req.getPart("profPic" + empId);
            //System.out.println("MESSAGE |  filled object => filePart : " + filePart);
            String fileName = getFileName(filePart);
            if (APPUtills.isThisStringValid(fileName)) {
                if (!isValidFileType(fileName)) {
                    throw new SBLException("Please upload a valid file(PNG or JPEG).");
                }
                InputStream filecontent = filePart.getInputStream();

                byte[] fileAsByteArray = new byte[1048576];
                filecontent.read(fileAsByteArray);

                model.setCompanyUserProfImg(fileAsByteArray);
            }

            if (!isBulk) {
                model.setCompanyUserEmpId(req.getParameter("empId" + empId)); //Emp Id ------------ UPM related value          
                model.setCompanyUserFirstName(req.getParameter("fName" + empId));//First Name ------------ UPM related value
                model.setCompanyUserLastName(req.getParameter("lName" + empId));//Last Name ------------ UPM related value
                model.setCompanyUserDivId(req.getParameter("solId" + empId));//SolId ------------ UPM related value
                model.setCompanyUserDepName(req.getParameter("depName" + empId));//Dep Name ------------ UPM related value
            }

            model.setCompanyUserMiddleName(req.getParameter("mName" + empId));//Middle Name
            model.setCompanyUserGender(req.getParameter("gender" + empId));//Gender
            model.setCompanyUserGrade(req.getParameter("grade" + empId));//Grade            
            model.setCompanyUserDestination(req.getParameter("designation" + empId)); //Destination
            model.setCompanyUserFloor(req.getParameter("floor" + empId));//Floor
            model.setCompanyUserJobDesc(req.getParameter("jobdesc" + empId));//Job Description
            model.setCompanyUserDepExten(req.getParameter("depExtention" + empId));//Dep Extention
            model.setCompanyUserOfficeMobile(req.getParameter("empMobile" + empId));//Mobile
            model.setCompanyUserOfficeExten(req.getParameter("empExtention" + empId));//Extention                    
            model.setCompanyUserOfficeEmail(req.getParameter("empEmail" + empId));//Email
            model.setCompanyUserContactPersonName(req.getParameter("nextPersonName" + empId));//Next Person Name
            model.setCompanyUserContactPersonExten(req.getParameter("nextPersonExtention" + empId));//Next Person Mobile
            model.setCompanyUserContactPersonMobile(req.getParameter("nextPersonMobile" + empId));//Next Person Extention
            model.setCompanyUserRemarks(req.getParameter("remark" + empId)); //Remarks
            model.setUserStatus(req.getParameter("status" + empId));//Status

        } catch (SBLException e) {
            System.out.println("ERROR   | Unable to fill data to object." + e.getMessage());
            throw new SBLException(e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR   | Unable to fill data to object." + e.getMessage());
            throw new SBLException("Unable to fill data to object.");
        }

       System.out.println("LEFT    |  CompanyEmployeeMngController.fillObject()");
        return model;
    }

    private String getFileName(Part part) throws SBLException {
       System.out.println("ENTERED | CompanyEmployeeMngController.getFileName()");
        String fileName = "";
        try {
            /*
            Getting Image File Name
             */
            String partHeader = part.getHeader("content-disposition");
           System.out.println("MESSAGE |  Part Header = {0}"+partHeader);
            for (String content : part.getHeader("content-disposition").split(";")) {
                if (content.trim().startsWith("filename")) {
                    fileName = content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                    break;
                }
            }
           System.out.println("MESSAGE |  fileName : " + fileName);
        } catch (Exception e) {
            System.out.println("ERROR   | " + e.getMessage());
            //throw new SBLException("Unable to get file name.");
        }

       System.out.println("LEFT    |  CompanyEmployeeMngController.getFileName()");
        return fileName;
    }

    private boolean isValidFileType(String fileName) throws SBLException {
       System.out.println("ENTERED | CompanyEmployeeMngController.getFileType()");
        String fileType = "";
        try {
            String[] val = fileName.split("\\.");
            fileType = val[val.length - 1].toUpperCase();
           System.out.println("MESSAGE |  File Type : " + fileType);
        } catch (Exception e) {
            System.out.println("ERROR   | " + e.getMessage());
            throw new SBLException("Unable to get file type.");
        }

       System.out.println("LEFT    |  CompanyEmployeeMngController.getFileType()");
        return fileType.equalsIgnoreCase("PNG") || fileType.equalsIgnoreCase("JPG") || fileType.equalsIgnoreCase("JPEG");
    }
		
	@RequestMapping(value = { "/CompanyEmployee/RejectEmp"})
	public ModelAndView rejectEmployeeDetails(HttpServletRequest req, HttpServletResponse resp,HttpSession session){		
		System.out.println("ENTERED | CompanyEmployeeMngController.rejectEmployeeDetails()");
        CompanyUserModel model;
        List<CompanyUserModel> companyUserModels = new ArrayList();
        boolean isSuccess = false;
        String eventAction = "", eventDesc = "", tempObjectToString = "", masterObjectToString = "";
        ObjectManager objManager = null;
        UserData userData;
        try {
            /*
            IF IdsAry.length > 1 || !IdsAry[0].equalsIgnoreCase("")
                TRUE 
                    take object from bankEmployeesMap and add to bankUserModels List to reject
            
            Update event log
             */
        	objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");
        	
            Map<String, CompanyUserModel> companyEmployeesMap = objManager.get("companyEmployeesMap") != null ? (HashMap) objManager.get("companyEmployeesMap") : new HashMap();
            String reason = req.getParameter("rejectReason");
            String ids = req.getParameter("ids");
            System.out.println("MESSAGE | reason : " + reason + " ,ids : " + ids);

            if (!APPUtills.isThisStringValid(reason)) {
                throw new SBLException("Comment is required.");
            }

            String[] IdsAry = ids.split(",");
            if (IdsAry.length > 1 || !IdsAry[0].equalsIgnoreCase("")) {
                for (String id : IdsAry) {
                    model = companyEmployeesMap.get(id);

                    model.setAuthComment(reason);
                    model.setModifiedBy(Integer.parseInt(userData.getUSER_ID()));
                    model.setModifiedDate(APPUtills.getCurrentDate());
                    model.setRecStatus(ApplicationConstants.RECORD_STATUS_REJECT);

                    companyUserModels.add(model);
                }
                List<String> IdsAryList = new ArrayList<>(Arrays.asList(IdsAry));
                Map<String, CompanyUserModel> bankEmployeesMapMaster = companyUserLogic.getCompanyUsers(ApplicationConstants.MASTER_DATA, IdsAryList, null);
                isSuccess = companyUserLogic.rejectCompanyUser(companyUserModels, IdsAryList, userData);
                System.out.println("MESSAGE | Is bank user rejected ? " + isSuccess);

                if (isSuccess) {

                   /* for (CompanyUserModel userModel : companyUserModels) {
                        eventAction = ApplicationConstants.actionTypeDesc(userModel.getActionType());
                        eventDesc = "Bank user temp Id : " + userModel.getCompanyUserTmpId() + ", Reject - ";
                        masterObjectToString = userModel.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY) ? ((CompanyUserMaster) bankEmployeesMapMaster.get(userModel.getCompanyUserEmpId()).modelToObject(ApplicationConstants.MASTER_DATA)).toString() : "";
                        tempObjectToString = ((CompanyUserTmp) userModel.modelToObject(ApplicationConstants.TEMP_DATA)).toString();
                        // Write to event log table.
                        EventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.BANK_EMPLOYEE, eventAction, eventDesc + "Successful. ", masterObjectToString, tempObjectToString, ApplicationConstants.EVENTSUCCESSFUL);
                    }*/
                    req.setAttribute("rtnMsg", "Successfully Rejected.");
                }
            } else {
                System.out.println("ERROR | No ids recived to reject.");
                throw new SBLException("Please select rows to reject.");
            }

        } catch (SBLException ex) {
            //EventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.BANK_EMPLOYEE, "", "Reject - Fail. " + "Message : " + ex.getMessage(), "", "", ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR | " + ex.getMessage());
            //throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            //EventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.BANK_EMPLOYEE, "", "Reject -Fail. " + "Message : " + ex.getMessage(), "", "", ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR | " + ex.getMessage());
            System.out.println("ERROR   | " + ex.getMessage() + "\n");
            //throw new SBLException("Unable to reject employee details");
        }

        System.out.println("LEFT    | CompanyEmployeeMngController.rejectEmployeeDetails()");
        return new ModelAndView("employee/pendingEmp");
		
	}
		
	@RequestMapping(value = { "/CompanyEmployee/RemoveEmp"})
	public ModelAndView removeEmployeeDetails(HttpServletRequest req, HttpServletResponse resp,HttpSession session){		
		System.out.println("ENTERED |  CompanyEmployeeMngController.removeEmployeeDetails()");
        List<CompanyUserModel> companyUserModels = new ArrayList();

        boolean isSuccess = false;
        String eventAction = "", eventDesc = "", tempObjectToString = "", masterObjectToString = "";
        ObjectManager objManager = null;
        UserData userData;
        try {
            /*
            IF IdsAry.length > 1 || !IdsAry[0].equalsIgnoreCase("")
                TRUE 
                    take object from bankEmployeesMap and add to bankUserModels List to remove
            
            Update event log
             */
        	objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");

            Map<String, CompanyUserModel> bankEmployeesMap = objManager.get("bankEmployeesMap") != null ? (HashMap) objManager.get("bankEmployeesMap") : new HashMap();
            String ids = req.getParameter("ids");
            System.out.println("MESSAGE | ids : " + ids);

            String[] IdsAry = ids.split(",");
            if (IdsAry.length > 1 || !IdsAry[0].equalsIgnoreCase("")) {
                for (String id : IdsAry) {
                	companyUserModels.add(bankEmployeesMap.get(id));
                }

                isSuccess = companyUserLogic.deleteCompanyUser(companyUserModels);
                System.out.println("MESSAGE | Is bank user Deleted ? " + isSuccess);

                if (isSuccess) {

                    for (CompanyUserModel userModel : companyUserModels) {
                        eventAction = "";
                        eventDesc = "Bank user temp Id : " + userModel.getCompanyUserTmpId() + ", Delete - ";
                        masterObjectToString = "";
                        tempObjectToString = ((CompanyUserTmp) userModel.modelToObject(ApplicationConstants.TEMP_DATA)).toString();
                        // Write to event log table.
                        //EventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.BANK_EMPLOYEE, eventAction, eventDesc + "Successful. ", masterObjectToString, tempObjectToString, ApplicationConstants.EVENTSUCCESSFUL);
                    }
                    req.setAttribute("rtnMsg", "Successfully delete.");
                }
            } else {
                System.out.println("ERROR | No ids recived to reject.");
                throw new SBLException("Please select rows to reject.");
            }
        } catch (SBLException ex) {
            //EventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.BANK_EMPLOYEE, eventAction, " Delete - Fail. " + "Message : " + ex.getMessage(), masterObjectToString, tempObjectToString, ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR | " + ex.getMessage());
            //throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            //EventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.BANK_EMPLOYEE, eventAction, " Delete - Fail. " + "Message : " + ex.getMessage(), masterObjectToString, tempObjectToString, ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR | " + ex.getMessage());
            System.out.println("ERROR   | " + ex.getMessage() + "\n");
            //throw new SBLException("Unable to reject employee details");
        }

        System.out.println("LEFT    | CompanyEmployeeMngController.removeEmployeeDetails()");
        return new ModelAndView("employee/pendingEmp");
		
	}
	
	@RequestMapping(value = { "/CompanyEmployee/VerifyEmp"})
	public ModelAndView verifyEmployeeDetails(HttpServletRequest req, HttpServletResponse resp,HttpSession session){		
		System.out.println("ENTERED | CompanyEmployeeMngController.verifyEmployeeDetails()");
        CompanyUserModel model;
        List<CompanyUserModel> bankUserModels = new ArrayList();
        boolean isSuccess = false;
        String eventAction = "", eventDesc = "", tempObjectToString = "", masterObjectToString = "";
        ObjectManager objManager = null;
        UserData userData;
        try {
            /*
            IF IdsAry.length > 1 || !IdsAry[0].equalsIgnoreCase("")
                TRUE 
                    take object from bankEmployeesMap and add to bankUserModels List to verify
            
            Update event log
             */
        	
        	objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");

            Map<String, CompanyUserModel> bankEmployeesMap = objManager.get("bankEmployeesMap") != null ? (HashMap) objManager.get("bankEmployeesMap") : new HashMap();
            String reason = req.getParameter("rejectReason");
            String ids = req.getParameter("ids");
            System.out.println("MESSAGE | reason : " + reason + " ,ids : " + ids);

            String[] IdsAry = ids.split(",");
            if (IdsAry.length > 1 || !IdsAry[0].equalsIgnoreCase("")) {
                for (String id : IdsAry) {
                    model = bankEmployeesMap.get(id);

                    model.setAuthComment(reason);
                    model.setVerifiedBy(Integer.parseInt(userData.getUSER_ID()));
                    model.setVerifiedDate(APPUtills.getCurrentDate());
                    model.setRecStatus(ApplicationConstants.RECORD_STATUS_VERIFY);

                    bankUserModels.add(model);
                }
                List<String> IdsAryList = new ArrayList<>(Arrays.asList(IdsAry));
                Map<String, CompanyUserModel> bankEmployeesMapMaster = companyUserLogic.getCompanyUsers(ApplicationConstants.MASTER_DATA, IdsAryList, null);
                isSuccess = companyUserLogic.verifyCompanyUser(bankUserModels, IdsAryList);
                System.out.println("MESSAGE | Is bank user verified ? " + isSuccess);

                if (isSuccess) {

                    for (CompanyUserModel userModel : bankUserModels) {
                        eventAction = ApplicationConstants.actionTypeDesc(userModel.getActionType());
                        eventDesc = "Bank user temp Id : " + userModel.getCompanyUserTmpId() + ", Verification - ";
                        masterObjectToString = userModel.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY) ? ((CompanyUserMaster) bankEmployeesMapMaster.get(userModel.getCompanyUserEmpId()).modelToObject(ApplicationConstants.MASTER_DATA)).toString() : "";
                        tempObjectToString = ((CompanyUserTmp) userModel.modelToObject(ApplicationConstants.TEMP_DATA)).toString();
                        // Write to event log table.
                        //EventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.BANK_EMPLOYEE, eventAction, eventDesc + "Successful. ", masterObjectToString, tempObjectToString, ApplicationConstants.EVENTSUCCESSFUL);
                    }
                    req.setAttribute("rtnMsg", "Successfully verified.");
                }
            } else {
                System.out.println("ERROR | No ids recived to verify.");
                throw new SBLException("Please select rows to verify.");
            }

        } catch (SBLException ex) {
            //EventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.BANK_EMPLOYEE, "", "Verification - Fail. " + "Message : " + ex.getMessage(), "", "", ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR | " + ex.getMessage());
            //throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            //EventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.BANK_EMPLOYEE, "", "Verification -Fail. " + "Message : " + ex.getMessage(), "", "", ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR | " + ex.getMessage());
            System.out.println("ERROR   | " + ex.getMessage() + "\n");
            //throw new SBLException("Unable to verify employee details");
        }

        System.out.println("LEFT    | CompanyEmployeeMngController.verifyEmployeeDetails()");
        return new ModelAndView("employee/pendingEmp");
		
	}
}

