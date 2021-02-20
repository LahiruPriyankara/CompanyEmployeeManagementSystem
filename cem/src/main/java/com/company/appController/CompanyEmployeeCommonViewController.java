package com.company.appController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.company.dao.VisitorDataFacadeLocal;
import com.company.dto.VisitorData;
import com.company.models.CompanyUserModel;
import com.company.models.UserData;

@Controller
@ComponentScan(basePackages = {"com.company.bl","com.company.dao"})
public class CompanyEmployeeCommonViewController {
	//private static final Log log = LogFactory.getLog(CompanyEmployeeCommonViewController.class);
	@Autowired
    private CompanyUserLogicLocal companyUserLogic;
	
	@Autowired 
	VisitorDataFacadeLocal visitorDataFacade;
	
	@RequestMapping(value = { "/CommonView/ExistingEmp" })
	public ModelAndView getAllEmployeeList(HttpServletRequest req, HttpServletResponse resp,HttpSession session){		
		System.out.println("ENTERED | CompanyEmployeeCommonViewController.getAllEmployeeList()");
        Map<String, CompanyUserModel> dfumsMap = new HashMap();
        Map<String, CompanyUserModel> companyEmployeesMap = new HashMap();
        ObjectManager objManager = null;
        UserData userData = null;
        try {
            /*
            Getting all active CEM users from master table.
             */
        	objManager = new ObjectManager(session);
            userData = (UserData) objManager.get("userData");
            
            String result = checkSessionTimeOut(userData,objManager);
        	if(APPUtills.isThisStringValid(result)){
        		req.setAttribute("errMsg", result);
        		return new ModelAndView("includes/include-dashboard");
        	}
        	objManager.remove("compnayEmployeesMap");
        	
            dfumsMap = companyUserLogic.getCompanyUsers(ApplicationConstants.MASTER_DATA, null, "");
            List<CompanyUserModel> dfumsList = new ArrayList<>(dfumsMap.values());
            for (CompanyUserModel model : dfumsList) {
                if (model.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_ACTIVE)) {
                	companyEmployeesMap.put(model.getCompanyUserEmpId(), model);
                }
            }
            System.out.println("MESSAGE | dfumsMapActiveUsers.size() : " + companyEmployeesMap.size());
            objManager.put("companyEmployeesMap", companyEmployeesMap, ApplicationConstants.SCOPE_COMMON_VIEW);
        } catch (SBLException ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            req.setAttribute("errMsg", ex.getMessage());
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            req.setAttribute("errMsg", ex.getMessage());
        }

        System.out.println("LEFT    | CompanyEmployeeCommonViewController.getAllEmployeeList()");
        return new ModelAndView("commonView/comnViewBankEmployee");
		
	}
	
	@RequestMapping(value = { "/CommonView/EmployeeDetailsForCommon" })
	public ModelAndView getEmployeeDetailsForCommonView(HttpServletRequest req, HttpServletResponse resp,HttpSession session){
		System.out.println("ENTERED | CompanyEmployeeCommonViewController.getEmployeeDetailsForCommonView()");
        CompanyUserModel modelFromCEM = null;
        CompanyUserModel modelFromCOM_SERVICE = null;
        String id;
        ObjectManager objManager = null;
        UserData userData = null;
        try {
            /*
            getting CEM record from objManager
            getting related COM_SERVICE record from COMPANY service (by passing solId)
             */
        	objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");
        	
        	String result = checkSessionTimeOut(userData,objManager);
        	if(APPUtills.isThisStringValid(result)){
        		req.setAttribute("errMsg", result);
        		return new ModelAndView("includes/include-dashboard");
        	}
        	
            objManager.remove("modelFromCEM");
            objManager.remove("modelFromCOM_SERVICE");

            Map<String, CompanyUserModel> companyEmployeesMap = objManager.get("companyEmployeesMap") != null ? (HashMap) objManager.get("companyEmployeesMap") : new HashMap();

            id = req.getParameter("id");
            if (!APPUtills.isThisStringValid(id)) {
                System.out.println("ERROR   | Id is not received.");
                throw new SBLException("Id is not received.");
            }

            modelFromCEM = companyEmployeesMap.get(id);

            HashMap<String, UserData> COM_SERVICEEmployeesMap = companyUserLogic.getCOM_SERVICEEmployeesMap(modelFromCEM.getCompanyUserDivId(), id);
            modelFromCOM_SERVICE = CompanyUserModel.userDataToModel(COM_SERVICEEmployeesMap.get(id));

            objManager.put("modelFromCEM", modelFromCEM, ApplicationConstants.SCOPE_COMMON_VIEW);
            objManager.put("modelFromCOM_SERVICE", modelFromCOM_SERVICE, ApplicationConstants.SCOPE_COMMON_VIEW);
            
            
        } catch (SBLException ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            req.setAttribute("errMsg", ex.getMessage());
            return new ModelAndView("commonView/comnViewBankEmployee");
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            req.setAttribute("errMsg", ex.getMessage());
            return new ModelAndView("commonView/comnViewBankEmployee");
        }

        System.out.println("LEFT    | CompanyEmployeeCommonViewController.getEmployeeDetailsForCommonView()");
        return new ModelAndView("commonView/comnViewBankEmployeeDetails");
	}
	
	@RequestMapping(value = { "/CommonView/SaveVisitorDetails" })
	public void saveVisitorDetails(HttpServletRequest req, HttpServletResponse resp,HttpSession session){
		System.out.println("ENTERED | CompanyEmployeeCommonViewController.saveVisitorDetails()");
        VisitorData data = new VisitorData();
        String passId,nicNum,empId;
        ObjectManager objManager = null;
        UserData userData = null;
        try {
            /*
            getting CEM record from objManager
            getting related COM_SERVICE record from COMPANY service (by passing solId)
             */
        	objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");
        	
        	String result = checkSessionTimeOut(userData,objManager);
        	if(APPUtills.isThisStringValid(result)){
        		req.setAttribute("errMsg", result);
        		//return new ModelAndView("includes/include-dashboard");
        		throw new Exception(result);
        	}
        	
            Map<String, CompanyUserModel> companyEmployeesMap = objManager.get("companyEmployeesMap") != null ? (HashMap) objManager.get("companyEmployeesMap") : new HashMap();

            empId = req.getParameter("id");
            passId = req.getParameter("passId");
            nicNum = req.getParameter("nicNum");
            
            System.out.println("empId : "+empId+" ,passId : "+passId+" ,nicNum : "+nicNum);
            
            if (!APPUtills.isThisStringValid(passId)||!APPUtills.isThisStringValid(nicNum)) {
                System.out.println("ERROR   | Id is not received.");
                throw new SBLException("Ids are not received.");
            }

            data.setCompanyEmpId(empId);
            data.setVisitorPassId(passId);
            data.setVisitorNicNum(nicNum);
            data.setCreatedBy(Integer.parseInt(userData.getUSER_ID()));
            data.setCreatedDate(APPUtills.getCurrentDate());
            
            if(visitorDataFacade.insertRecord(data)){
            	req.setAttribute("rtnMsg", "Successfully save.");
            } else{
            	req.setAttribute("errMsg", "Fail to save the record.");
            }
        } catch (SBLException ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            req.setAttribute("errMsg", ex.getMessage());
            //return new ModelAndView("commonView/comnViewBankEmployee");
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            req.setAttribute("errMsg", ex.getMessage());
            //return new ModelAndView("commonView/comnViewBankEmployee");
        }

        System.out.println("LEFT    | CompanyEmployeeCommonViewController.saveVisitorDetails()");
        //return new ModelAndView("commonView/comnViewBankEmployee");
        //return "redirect:/CommonView/ExistingEmp";
        //return new ModelAndView("includes/include-dashboard");
	}
	
	
	@RequestMapping(value = { "/CommonView/GetAllVisitorData" })
	public ModelAndView getAllVisitorData(HttpServletRequest req, HttpServletResponse resp,HttpSession session){
		System.out.println("ENTERED | CompanyEmployeeCommonViewController.getAllVisitorData()");
        List<VisitorData> dataList;
        ObjectManager objManager = null;
        UserData userData = null;
        try {
            /*
            getting CEM record from objManager
            getting related COM_SERVICE record from COMPANY service (by passing solId)
             */
        	objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");
        	
        	String result = checkSessionTimeOut(userData,objManager);
        	if(APPUtills.isThisStringValid(result)){
        		req.setAttribute("errMsg", result);
        		return new ModelAndView("includes/include-dashboard");
        	}
        	
        	dataList = visitorDataFacade.getAllRecord();
        	objManager.put("dataList", dataList, ApplicationConstants.SCOPE_VISITOR_VIEW);
        
        } catch (SBLException ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            req.setAttribute("errMsg", ex.getMessage());
            //return new ModelAndView("commonView/comnViewBankEmployee");
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            req.setAttribute("errMsg", ex.getMessage());
            //return new ModelAndView("commonView/comnViewBankEmployee");
        }

        System.out.println("LEFT    | CompanyEmployeeCommonViewController.getAllVisitorData()");
        return new ModelAndView("visitorData/visitorData");
	}
	
	public String checkSessionTimeOut(UserData userData,ObjectManager objManager){
		try{
			if (userData == null) {
	            objManager.remove("userType");
	            objManager.cleanup(ApplicationConstants.SCOPE_GLOBAL);
	            objManager.cleanup(ApplicationConstants.SCOPE_COMPANY_USER);
	            objManager.cleanup(ApplicationConstants.SCOPE_COMMON_USER);
	            objManager.cleanup(ApplicationConstants.SCOPE_FD_USER);
	            objManager.cleanup(ApplicationConstants.SCOPE_COMMON_VIEW);

	            return ApplicationConstants.ERR_MSG_SESSION_TERMINTATED;
	        }else{
	        	return "";
	        }
		}catch (Exception e) {
			return "Error in checking session terminated";	
		}
	}
	
	
}

