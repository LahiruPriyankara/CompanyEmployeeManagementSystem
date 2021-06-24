/* 
    Author     : lahiru priyankara
*/

package com.company.appController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company.bl.CommonUserMasterLogicLocal;
import com.company.bl.CompanyUserLogicLocal;
import com.company.bl.EventLoggerLocal;
import com.company.common.APPUtills;
import com.company.common.ApplicationConstants;
import com.company.common.ObjectManager;
import com.company.common.SBLException;
import com.company.models.CommonUserModel;
import com.company.models.CompanyUserModel;
import com.company.models.DivInfo;
import com.company.models.UserData;

@Controller
@ComponentScan(basePackages = { "com.company.bl" })
public class CommonEntererAuthorizerController {
	// private static final Log log
	// =LogFactory.getLog(FrontDeskUserMngController.class);
	@Autowired
	private CommonUserMasterLogicLocal commonUserMasterLogic;

	@Autowired
	private CompanyUserLogicLocal companyUserLogic;

	@Autowired
	private EventLoggerLocal eventLogger;

	@RequestMapping(value = "/CommonEntererAuth/ExistingCommonEntererAuths")
	public ModelAndView existingCommonEntererAuths(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session) {
		System.out.println("ENTERED | CommonEntererAuthorizerController.existingCommonEntererAuths()");
		ModelAndView mv = new ModelAndView("CommonEntererAuth/existingCommonEntererAuth");
		ObjectManager objManager = null;
		UserData userData = null;
		Map<String, DivInfo> divInfoMap = new HashMap();// to store Dep
														// infomation
		Map<Integer, CommonUserModel> commonUserMap = new HashMap();
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");
			objManager.remove("companyUserModel");
			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				return new ModelAndView("includes/include-dashboard");
			}

			divInfoMap = companyUserLogic.getAllDepInfo();
			objManager.put("divInfoMap", divInfoMap, ApplicationConstants.SCOPE_COMMON_USER);

			commonUserMap = commonUserMasterLogic.getAllUsers(ApplicationConstants.MASTER_DATA);
			objManager.put("commonUserMap", commonUserMap, ApplicationConstants.SCOPE_COMMON_USER);

		} catch (Exception e) {
			// log.debug("Exception : " + e);
		}
		System.out.println("ENTERED | CommonEntererAuthorizerController.existingCommonEntererAuths()");
		return mv;
	}

	@RequestMapping(value = "/CommonEntererAuth/PendingCommonEntererAuths")
	public ModelAndView pendingCommonEntererAuths(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session) {
		System.out.println("ENTERED | CommonEntererAuthorizerController.pendingCommonEntererAuths()");
		ModelAndView mv = new ModelAndView("CommonEntererAuth/pendingCommonEntererAuth");
		ObjectManager objManager = null;
		UserData userData = null;
		Map<Integer, CommonUserModel> commonUserMap = new HashMap();
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");
			objManager.remove("companyUserModel");
			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				return new ModelAndView("includes/include-dashboard");
			}
			commonUserMap = commonUserMasterLogic.getAllUsers(ApplicationConstants.TEMP_DATA);
			objManager.put("commonUserMap", commonUserMap, ApplicationConstants.SCOPE_COMMON_USER);

		} catch (Exception e) {
			// log.debug("Exception : " + e);
		}
		System.out.println("ENTERED | CommonEntererAuthorizerController.pendingCommonEntererAuths()");
		return mv;
	}

	@RequestMapping(value = "/CommonEntererAuth/SearchCommonEntererAuths")
	public ModelAndView searchCommonEntererAuths(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session) {
		System.out.println("ENTERED | CommonEntererAuthorizerController.existingCommonEntererAuths()");
		ModelAndView mv = new ModelAndView("CommonEntererAuth/existingCommonEntererAuth");
		ObjectManager objManager = null;
		UserData userData = null;
		Map<String, DivInfo> divInfoMap = new HashMap();// to store Dep
														// infomation
		Map<Integer, CommonUserModel> commonUserMap = new HashMap();
		Map<String, CompanyUserModel> companyEmployeesMap = new HashMap();
		CompanyUserModel companyUserModel = new CompanyUserModel();
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");
			objManager.remove("companyUserModel");
			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				return new ModelAndView("includes/include-dashboard");
			}

			String depCode = req.getParameter("depCode");
			String empId = req.getParameter("empId");

			companyEmployeesMap = companyUserLogic.getCompanyUsers(ApplicationConstants.MASTER_DATA,
					empId.equalsIgnoreCase("") ? null : Arrays.asList(empId), depCode);
			companyUserModel = companyEmployeesMap.get(empId);

			objManager.put("companyUserModel", companyUserModel, ApplicationConstants.SCOPE_COMMON_USER);

		} catch (Exception e) {
			// log.debug("Exception : " + e);
		}
		System.out.println("LEFT | CommonEntererAuthorizerController.existingCommonEntererAuths()");
		return mv;
	}

	@RequestMapping(value = "/CommonEntererAuth/ExistingCommonEntererAuthsDetails")
	public ModelAndView existingCommonEntererAuthsDetails(HttpServletRequest req, HttpServletResponse resp,
			HttpSession session) {
		System.out.println("ENTERED | CommonEntererAuthorizerController.existingCommonEntererAuthsDetails()");
		ModelAndView mv = new ModelAndView("CommonEntererAuth/existingCommonEntererAuthDetails");
		ObjectManager objManager = null;
		UserData userData = null;

		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");
			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				throw new Exception(result);
			}
		} catch (Exception e) {
			// log.debug("Exception : " + e);
		}
		System.out.println("LEFT | CommonEntererAuthorizerController.existingCommonEntererAuthsDetails()");
		return mv;
	}

	@RequestMapping(value = "/CommonEntererAuth/CreateCommonEneterAuth")
	public ModelAndView createCommonEneterAuth(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		System.out.println("ENTERED | CommonEntererAuthorizerController.createCommonEneterAuth()");
		ModelAndView mv = new ModelAndView("CommonEntererAuth/existingCommonEntererAuth");
		ObjectManager objManager = null;
		UserData userData = null;
		CommonUserModel cmnModel = null;
		CompanyUserModel companyUserModel = new CompanyUserModel();
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				return new ModelAndView("includes/include-dashboard");
			}

			companyUserModel = objManager.get("companyUserModel") != null
					? (CompanyUserModel) objManager.get("companyUserModel") : null;
			if (companyUserModel == null) {
				System.out.println("ERROR | Can not found the Company Employee object from map.");
				req.setAttribute("errMsg", ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
				return mv;
			}

			String role = req.getParameter("role");
			System.out.println("ENTERED | role : " + role);
			if (!APPUtills.isThisStringValid(role)) {
				System.out.println("ERROR | Can not found the Role to save.");
				req.setAttribute("errMsg", "Please send a user role for assign.");
				return mv;
			}

			// String cmnUserId, String cmnUserRole, String userStatus,String
			// actionType
			cmnModel = new CommonUserModel(companyUserModel.getCompanyUserEmpId(), role,
					ApplicationConstants.STATUS_ACTIVE, ApplicationConstants.ACTION_TYPE_NEW);
			cmnModel.setCreatedBy(Integer.parseInt(userData.getUSER_ID()));
			cmnModel.setCreatedDate(APPUtills.getCurrentDate());
			cmnModel.setModifiedBy(Integer.parseInt(userData.getUSER_ID()));
			cmnModel.setModifiedDate(APPUtills.getCurrentDate());
			cmnModel.setRecStatus(ApplicationConstants.RECORD_STATUS_PENDING);

			commonUserMasterLogic.modifyUser(cmnModel, ApplicationConstants.PERFORMING_ACTCION_SAVE);

			req.setAttribute("rtnMsg", "Successfully created and needs to be verified.");
		} catch (Exception ex) {
			// eventLogger.doLog(req, (userData!=null?userData.getUSER_ID():""),
			// ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc +
			// "Fail. " + "Message : " + ex.getMessage(), masterObjectToString,
			// tempObjectToString, ApplicationConstants.EVENTFAIL);
			System.out.println("ERROR   | " + ex.getMessage() + "\n");
			req.setAttribute("errMsg", ex.getMessage());
			// return "frontDeskUser/pendingFrontDeskUser";
		}
		System.out.println("LEFT | CommonEntererAuthorizerController.createCommonEneterAuth()");
		return mv;
	}

	@RequestMapping(value = "/CommonEntererAuth/ModifyCommonEneterAuth")
	public ModelAndView modifyCommonEneterAuth(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		System.out.println("ENTERED | CommonEntererAuthorizerController.modifyCommonEneterAuth()");
		ModelAndView mv = new ModelAndView("CommonEntererAuth/existingCommonEntererAuth");
		ObjectManager objManager = null;
		UserData userData = null;
		Map<String, DivInfo> divInfoMap = new HashMap();// to store Dep
														// infomation
		Map<Integer, CommonUserModel> commonUserMap = null;
		CommonUserModel cmnModel = null;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				return new ModelAndView("includes/include-dashboard");
			}

			String id = req.getParameter("id");
			String status = "A";
			System.out.println("MESSAGE | id : " + id + " , status : " + status);
			int masterId = 0;
			if (APPUtills.isThisStringValid(id)) {
				masterId = Integer.parseInt(id);
			}
			System.out.println("ENTERED | masterId : " + masterId);

			commonUserMap = objManager.get("commonUserMap") != null ? (HashMap) objManager.get("commonUserMap")
					: new HashMap();
			cmnModel = commonUserMap.get(masterId);
			if (cmnModel == null) {
				System.out.println("ERROR | Can not found the object from map.");
				throw new SBLException(ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
			}

			if (cmnModel.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_INACTIVE)) {
				status = "I";
			}

			cmnModel.setUserStatus(status);
			cmnModel.setActionType(ApplicationConstants.ACTION_TYPE_MODIFY);
			cmnModel.setRecStatus(ApplicationConstants.RECORD_STATUS_PENDING);
			cmnModel.setCreatedBy(Integer.parseInt(userData.getUSER_ID()));
			cmnModel.setCreatedDate(APPUtills.getCurrentDate());
			cmnModel.setModifiedBy(Integer.parseInt(userData.getUSER_ID()));
			cmnModel.setModifiedDate(APPUtills.getCurrentDate());

			commonUserMasterLogic.modifyUser(cmnModel, ApplicationConstants.PERFORMING_ACTCION_SAVE);
			req.setAttribute("rtnMsg", "Successfully save..");
		} catch (Exception e) {
			System.out.println("Exception : " + e);
			req.setAttribute("errMsg", "Fail to save..");
		}
		System.out.println("LEFT | CommonEntererAuthorizerController.modifyCommonEneterAuth()");
		return mv;
	}

	@RequestMapping(value = "/CommonEntererAuth/RejectCommonEneterAuth")
	public String rejectCommonEneterAuth(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		System.out.println("ENTERED | CommonEntererAuthorizerController.rejectCommonEneterAuth()");
		ModelAndView mv = new ModelAndView("CommonEntererAuth/pendingCommonEntererAuth");
		ObjectManager objManager = null;
		UserData userData = null;
		Map<Integer, CommonUserModel> commonUserMap = null;
		CommonUserModel cmnModel = null;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				return "CommonEntererAuth/pendingCommonEntererAuth";
			}

			String id = req.getParameter("id");
			String reason = req.getParameter("reason");
			System.out.println("MESSAGE | id : " + id + " | reason : " + reason);
			if (!APPUtills.isThisStringValid(reason)) {
				req.setAttribute("Comment is required.", result);
				return "CommonEntererAuth/pendingCommonEntererAuth";
			}
			int masterId = 0;
			if (APPUtills.isThisStringValid(id)) {
				masterId = Integer.parseInt(id);
			}
			System.out.println("ENTERED | masterId : " + masterId);

			commonUserMap = objManager.get("commonUserMap") != null ? (HashMap) objManager.get("commonUserMap")
					: new HashMap();
			cmnModel = commonUserMap.get(masterId);
			if (cmnModel == null) {
				System.out.println("ERROR | Can not found the object from map.");
				throw new SBLException(ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
			}

			cmnModel.setAuthComment(reason);
			cmnModel.setRecStatus(ApplicationConstants.RECORD_STATUS_REJECT);
			// cmnModel.setCreatedBy(Integer.parseInt(userData.getUSER_ID()));
			// cmnModel.setCreatedDate(APPUtills.getCurrentDate());
			cmnModel.setModifiedBy(Integer.parseInt(userData.getUSER_ID()));
			cmnModel.setModifiedDate(APPUtills.getCurrentDate());

			commonUserMasterLogic.modifyUser(cmnModel, ApplicationConstants.PERFORMING_ACTCION_REJECT);
			req.setAttribute("rtnMsg", "Successfully rejected..");
		} catch (Exception ex) {
			// eventLogger.doLog(req, (userData!=null?userData.getUSER_ID():""),
			// ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc +
			// "Fail. " + "Message : " + ex.getMessage(), masterObjectToString,
			// tempObjectToString, ApplicationConstants.EVENTFAIL);
			System.out.println("ERROR   | " + ex.getMessage() + "\n");
			req.setAttribute("errMsg", ex.getMessage());
			return "CommonEntererAuth/pendingCommonEntererAuth";
		}
		System.out.println("LEFT | CommonEntererAuthorizerController.rejectCommonEneterAuth()");
		return "redirect:/CommonEntererAuth/PendingCommonEntererAuths";
	}

	@RequestMapping(value = "/CommonEntererAuth/deleteCommonEneterAuth")
	public String deleteCommonEneterAuth(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		System.out.println("ENTERED | CommonEntererAuthorizerController.deleteCommonEneterAuth()");
		ModelAndView mv = new ModelAndView("CommonEntererAuth/pendingCommonEntererAuth");
		ObjectManager objManager = null;
		UserData userData = null;
		Map<Integer, CommonUserModel> commonUserMap = null;
		CommonUserModel cmnModel = null;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				return "CommonEntererAuth/pendingCommonEntererAuth";
			}

			String id = req.getParameter("id");

			int masterId = 0;
			if (APPUtills.isThisStringValid(id)) {
				masterId = Integer.parseInt(id);
			}
			System.out.println("ENTERED | masterId : " + masterId);

			commonUserMap = objManager.get("commonUserMap") != null ? (HashMap) objManager.get("commonUserMap")
					: new HashMap();
			cmnModel = commonUserMap.get(masterId);
			if (cmnModel == null) {
				System.out.println("ERROR | Can not found the object from map.");
				throw new SBLException(ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
			}

			commonUserMasterLogic.modifyUser(cmnModel, ApplicationConstants.PERFORMING_ACTCION_DELETE);
			req.setAttribute("rtnMsg", "Successfully deleted.");
		} catch (Exception ex) {
			// eventLogger.doLog(req, (userData!=null?userData.getUSER_ID():""),
			// ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc +
			// "Fail. " + "Message : " + ex.getMessage(), masterObjectToString,
			// tempObjectToString, ApplicationConstants.EVENTFAIL);
			System.out.println("ERROR   | " + ex.getMessage() + "\n");
			req.setAttribute("errMsg", ex.getMessage());
			return "CommonEntererAuth/pendingCommonEntererAuth";
		}
		System.out.println("LEFT | CommonEntererAuthorizerController.deleteCommonEneterAuth()");
		return "redirect:/CommonEntererAuth/PendingCommonEntererAuths";
	}

	@RequestMapping(value = "/CommonEntererAuth/VerifyCommonEneterAuth")
	public String verifyCommonEneterAuth(HttpServletRequest req, HttpServletResponse resp,HttpSession session) {
		System.out.println("ENTERED | CommonEntererAuthorizerController.verifyCommonEneterAuth()");
		ModelAndView mv = new ModelAndView("CommonEntererAuth/pendingCommonEntererAuth");
		ObjectManager objManager = null;
		UserData userData = null;
		Map<Integer, CommonUserModel> commonUserMap=null;
		CommonUserModel cmnModel = null;
		try {
			objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");
        	
        	String result = checkSessionTimeOut(userData,objManager);
        	if(APPUtills.isThisStringValid(result)){
        		req.setAttribute("errMsg", result);
        		return "CommonEntererAuth/pendingCommonEntererAuth";
        	}
			
        	String id = req.getParameter("id");        	
        	String reason = req.getParameter("reason");
            System.out.println("MESSAGE | id : " + id + " | reason : " + reason);

        	int masterId = 0;
        	if(APPUtills.isThisStringValid(id)){
        		masterId = Integer.parseInt(id);
        	}
        	System.out.println("ENTERED | masterId : "+masterId);       	
        	
        	commonUserMap = objManager.get("commonUserMap") != null ? (HashMap) objManager.get("commonUserMap") : new HashMap();            
        	cmnModel = commonUserMap.get(masterId);
            if (cmnModel == null) {
                System.out.println("ERROR | Can not found the object from map.");
                throw new SBLException(ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
            }
            Map<Integer, CommonUserModel> commonUserMapFroMaster = commonUserMasterLogic.getUserByUserId(cmnModel.getCmnUserId());
            CommonUserModel masterModel = null;
            
            if(!commonUserMapFroMaster.isEmpty()){
            	for(CommonUserModel m : commonUserMapFroMaster.values()){
            		masterModel = m;
            	}
            	System.out.println("masterModel.toString() : "+masterModel.toString());
            }
            System.out.println("masterModel : "+masterModel);
            if(masterModel == null){     
            	cmnModel.setActionType(ApplicationConstants.ACTION_TYPE_NEW);
            }else{            	
            	masterModel.setCmnUserTmpId(cmnModel.getCmnUserTmpId());
            	masterModel.setCmnUserRole(cmnModel.getCmnUserRole());
            	masterModel.setActionType(ApplicationConstants.ACTION_TYPE_MODIFY);
            	cmnModel = masterModel;
            }
            cmnModel.setAuthComment(reason);
            cmnModel.setRecStatus(ApplicationConstants.RECORD_STATUS_VERIFY);
        	cmnModel.setVerifiedBy(Integer.parseInt(userData.getUSER_ID()));
        	cmnModel.setVerifiedDate(APPUtills.getCurrentDate());
        	
        	System.out.println("masterModel.toString() : "+cmnModel.toString());
        	
            commonUserMasterLogic.modifyUser(cmnModel, ApplicationConstants.PERFORMING_ACTCION_VERIFY);
            req.setAttribute("rtnMsg", "Successfully verified.");
		} catch (Exception ex) {
			//eventLogger.doLog(req, (userData!=null?userData.getUSER_ID():""), ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Fail. " + "Message : " + ex.getMessage(), masterObjectToString, tempObjectToString, ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR   | " + ex.getMessage() + "\n");
            req.setAttribute("errMsg", ex.getMessage());
            return "CommonEntererAuth/pendingCommonEntererAuth";
		}
		System.out.println("LEFT | CommonEntererAuthorizerController.verifyCommonEneterAuth()");
		return "redirect:/CommonEntererAuth/PendingCommonEntererAuths";
	}

	public String checkSessionTimeOut(UserData userData, ObjectManager objManager) {
		if (userData == null) {
			objManager.remove("userType");
			objManager.cleanup(ApplicationConstants.SCOPE_GLOBAL);
			objManager.cleanup(ApplicationConstants.SCOPE_COMPANY_USER);
			objManager.cleanup(ApplicationConstants.SCOPE_COMMON_USER);
			objManager.cleanup(ApplicationConstants.SCOPE_FD_USER);
			objManager.cleanup(ApplicationConstants.SCOPE_COMMON_VIEW);

			return ApplicationConstants.ERR_MSG_SESSION_TERMINTATED;
		} else {
			return "";
		}
	}
}
