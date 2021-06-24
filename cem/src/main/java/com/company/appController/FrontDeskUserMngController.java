/* 
    Author     : lahiru priyankara
*/

package com.company.appController;

import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company.bl.EventLoggerLocal;
import com.company.bl.FDUserLogicLocal;
import com.company.common.APPUtills;
import com.company.common.ApplicationConstants;
import com.company.common.ObjectManager;
import com.company.common.SBLException;
import com.company.dto.FdUserMaster;
import com.company.dto.FdUserTmp;
import com.company.models.FdUserModel;
import com.company.models.UserData;

@Controller
@ComponentScan(basePackages = { "com.company.bl" })
public class FrontDeskUserMngController {
	// private static final Log log =  LogFactory.getLog(FrontDeskUserMngController.class);

	@Autowired
	FDUserLogicLocal fDUserLogic;
	
	@Autowired
    private EventLoggerLocal eventLogger;

	@RequestMapping(value = "/FrontDeskUser/ExistingFrontDeskUsers")
	public ModelAndView getExistingFrontDeskUsers(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		System.out.println("ENTERED | FrontDeskUserMngAction.getExistingFrontDeskUsers()");
		ObjectManager objManager = null;
		UserData userData;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				return new ModelAndView("includes/include-dashboard");
			}
			getFrontDeskUsers(objManager, ApplicationConstants.MASTER_DATA);

		} catch (SBLException ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			// return new ModelAndView("frontDeskUser/existingFrontDeskUser");
		} catch (Exception ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			// return new ModelAndView("frontDeskUser/existingFrontDeskUser");
		}

		System.out.println("LEFT    | FrontDeskUserMngAction.getExistingFrontDeskUsers()");
		return new ModelAndView("frontDeskUser/existingFrontDeskUser");

	}

	@RequestMapping(value = "FrontDeskUser/PendingFrontDeskUsers")
	public ModelAndView getPendingFrontDeskUsers(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		System.out.println("ENTERED | FrontDeskUserMngAction.getPendingFrontDeskUsers()");
		ObjectManager objManager = null;
		UserData userData;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				return new ModelAndView("includes/include-dashboard");
			}
			getFrontDeskUsers(objManager, ApplicationConstants.TEMP_DATA);
		} catch (SBLException ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			// return new ModelAndView("frontDeskUser/existingFrontDeskUser");
		} catch (Exception ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			// return new ModelAndView("frontDeskUser/existingFrontDeskUser");
		}

		System.out.println("LEFT    | FrontDeskUserMngAction.getPendingFrontDeskUsers()");
		return new ModelAndView("frontDeskUser/pendingFrontDeskUser");

	}
	 
	private void getFrontDeskUsers(ObjectManager objManager, String type) throws Exception {
		System.out.println("ENTERED | FrontDeskUserMngAction.getFrontDeskUsers()");
        try {
            objManager.remove("frontDeskUsers");
            objManager.put("frontDeskUsers", fDUserLogic.getFdUsers(type, null), ApplicationConstants.SCOPE_FD_USER);
        } catch (SBLException ex) {
            System.out.println("ERROR | " + ex.getMessage());
            throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            throw new SBLException("Unable to get front desk users");
        }
        System.out.println("LEFT    | FrontDeskUserMngAction.getFrontDeskUsers()");
	}
	        
//-------------------------------------------------------------------------------------------------------------------------------------	

	@RequestMapping(value = "/FrontDeskUser/ExistingFrontDeskUserDetails")
	public ModelAndView getExixtingFrontDeskUserDetails(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		System.out.println("ENTERED | FrontDeskUserMngAction.getExixtingFrontDeskUserDetails()");
		ObjectManager objManager = null;
		UserData userData;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				throw new Exception(result);
			}
			
			getFrontDeskUserDetails(objManager, ApplicationConstants.MASTER_DATA, req);
		} catch (SBLException ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			// return new ModelAndView("frontDeskUser/existingFrontDeskUser");
		} catch (Exception ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			// return new ModelAndView("frontDeskUser/existingFrontDeskUser");
		}

		System.out.println("LEFT    | FrontDeskUserMngAction.getExixtingFrontDeskUserDetails()");
		return new ModelAndView("frontDeskUser/existingFrontDeskUserDetails");

	}
	
	@RequestMapping(value = "/FrontDeskUser/PendingFrontDeskUserDetails")
	public ModelAndView getPendingFrontDeskUserDetails(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		System.out.println("ENTERED | FrontDeskUserMngAction.getPendingFrontDeskUserDetails()");
		ObjectManager objManager = null;
		UserData userData;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				throw new Exception(result);
			}
			
			getFrontDeskUserDetails(objManager, ApplicationConstants.TEMP_DATA, req);
		} catch (SBLException ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			// return new ModelAndView("frontDeskUser/existingFrontDeskUser");
		} catch (Exception ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			// return new ModelAndView("frontDeskUser/existingFrontDeskUser");
		}

		System.out.println("LEFT    | FrontDeskUserMngAction.getPendingFrontDeskUserDetails()");
		return new ModelAndView("frontDeskUser/pendingFrontDeskUserDetails");

	}
		
	private void getFrontDeskUserDetails(ObjectManager objManager, String type, HttpServletRequest req) throws Exception {
        System.out.println("ENTERED | FrontDeskUserMngAction.getFrontDeskUserDetails()");
        Map<Integer, FdUserModel> userMap = new HashMap();
        Map<Integer, FdUserModel> userMapFromMaster = new HashMap();
        FdUserModel userModel;
        FdUserModel userModelFromMaster = new FdUserModel();
        try {
            String userTmpId = req.getParameter("id");
            System.out.println("MESSAGE    | Recived id : " + userTmpId);
            userMap = objManager.get("frontDeskUsers") != null ? (HashMap) objManager.get("frontDeskUsers") : new HashMap();
            System.out.println("MESSAGE    | userMap.size() : " + userMap.size());

            userModel = userMap.get(Integer.parseInt(userTmpId));
            if (userModel == null) {
                System.out.println("ERROR | Can not found the object from map.");
                throw new SBLException(ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
            }
            if (type.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)) {
                if (userModel.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY)) {
                    userMapFromMaster = fDUserLogic.getFdUsers(ApplicationConstants.MASTER_DATA, Arrays.asList(userModel.getFdUserMasterId()));
                    userModelFromMaster = userMapFromMaster.get(userModel.getFdUserMasterId());
                }
            }
            System.out.println("MESSAGE    | userModel.toString()           : " + userModel.toString());
            System.out.println("MESSAGE    | userModelFromMaster.toString() : " + userModelFromMaster.toString());
            objManager.put("userModel", userModel, ApplicationConstants.SCOPE_FD_USER);
            objManager.put("userModelFromMaster", userModelFromMaster, ApplicationConstants.SCOPE_FD_USER);
        } catch (SBLException ex) {
            System.out.println("ERROR | " + ex.getMessage());
            throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage() + "\n");
            throw new SBLException("Unable to get front desk users details");
        }

        System.out.println("LEFT    | FrontDeskUserMngAction.getFrontDeskUserDetails()");
    }

//-------------------------------------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/FrontDeskUser/SaveNewFrontDeskUser")
	public String saveNewFrontDeskUser(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		System.out.println("ENTERED | FrontDeskUserMngAction.saveNewFrontDeskUser()");
		ObjectManager objManager = null;
		UserData userData;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				throw new Exception(result);
			}
			
			saveFrontDeskUser(objManager, userData, false, false, req);
			
		} catch (SBLException ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			//return "frontDeskUser/existingFrontDeskUser";
		} catch (Exception ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			//return "frontDeskUser/existingFrontDeskUser";
		}

		System.out.println("LEFT    | FrontDeskUserMngAction.saveNewFrontDeskUser()");
		return "frontDeskUser/existingFrontDeskUser";
	}
	
	@RequestMapping(value = "/FrontDeskUser/SaveModifiedFdUser")
	public String saveModifiedFdUser(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		System.out.println("ENTERED | FrontDeskUserMngAction.saveModifiedFdUser()");
		ObjectManager objManager = null;
		UserData userData;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				throw new Exception(result);
			}
			
			saveFrontDeskUser(objManager, userData, true, false, req);
			
		} catch (SBLException ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			return "frontDeskUser/existingFrontDeskUser";
		} catch (Exception ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			return "frontDeskUser/existingFrontDeskUser";
		}

		System.out.println("LEFT    | FrontDeskUserMngAction.saveModifiedFdUser()");
		return "redirect:/FrontDeskUser/ExistingFrontDeskUsers";
	}
	
	@RequestMapping(value = "/FrontDeskUser/SaveReModifiedFrontDeskUser")
	public String saveReModifiedFrontDeskUser(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		System.out.println("ENTERED | FrontDeskUserMngAction.saveReModifiedFrontDeskUser()");
		ObjectManager objManager = null;
		UserData userData;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				throw new Exception(result);
			}
			
			saveFrontDeskUser(objManager, userData, false, false, req);
			
		} catch (SBLException ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			return "frontDeskUser/existingFrontDeskUser";
		} catch (Exception ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			return "frontDeskUser/existingFrontDeskUser";
		}

		System.out.println("LEFT    | FrontDeskUserMngAction.saveReModifiedFrontDeskUser()");
		return "redirect:/FrontDeskUser/PendingFrontDeskUsers";
	}
	
	@RequestMapping(value = "/FrontDeskUser/SaveNewFrontDeskUserPassword")
	public String saveNewFrontDeskUserPassword(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		System.out.println("ENTERED | FrontDeskUserMngAction.saveNewFrontDeskUserPassword()");
		ObjectManager objManager = null;
		UserData userData;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				throw new Exception(result);
			}
			
			saveFrontDeskUser(objManager, userData, true, true, req);
			
		} catch (SBLException ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			return "frontDeskUser/existingFrontDeskUser";
		} catch (Exception ex) {
			System.out.println("ERROR   | " + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			return "frontDeskUser/existingFrontDeskUser";
		}

		System.out.println("LEFT    | FrontDeskUserMngAction.saveNewFrontDeskUserPassword()");
		return "redirect:/FrontDeskUser/PendingFrontDeskUsers";
	}
	
	private void saveFrontDeskUser(ObjectManager objManager, UserData userData, boolean isMasterTableRecord, boolean isSecurepassProcess, HttpServletRequest req) throws SBLException {
        System.out.println("ENTERED | FrontDeskUserMngAction.saveFrontDeskUser()");
        String eventAction = "", eventDesc = "";
        Map<Integer, FdUserModel> userMap = new HashMap();
        FdUserModel userModelFromObMng = new FdUserModel();
        FdUserModel userModel = new FdUserModel();
        String tempObjectToString = "";
        try {
            System.out.println("MESSAGE | isMasterTableRecord : " + isMasterTableRecord + " | isSecurepassProcess : " + isSecurepassProcess);
            if (isMasterTableRecord) {
                String id = req.getParameter("id");
                String status = req.getParameter("status");
                System.out.println("MESSAGE | id : " + id + " | status : " + status);
                userMap = objManager.get("frontDeskUsers") != null ? (HashMap) objManager.get("frontDeskUsers") : new HashMap();

                userModelFromObMng = userMap.get(Integer.parseInt(id));
                userModel = (FdUserModel) userModelFromObMng.clone();
                if (userModel == null) {
                    System.out.println("ERROR | Can not found the object from map.");
                    throw new SBLException(ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
                }

                if (!isSecurepassProcess && userModel.getUserStatus().equalsIgnoreCase(status)) {
                    throw new SBLException(ApplicationConstants.ERR_MSG_VALUES_ARE_SAME);
                }
                if (!isSecurepassProcess) {
                    userModel.setUserStatus(status);
                }
                userModel.setActionType(ApplicationConstants.ACTION_TYPE_MODIFY);
            } else {
                String id = req.getParameter("id");
                String userName = req.getParameter("userName");
                String userFirstName = req.getParameter("userFirstName").toUpperCase();
                String userLastName = req.getParameter("userLastName").toUpperCase();

                System.out.println("MESSAGE | id : " + id + " | userName : " + userName + " | userFirstName : " + userFirstName + " | userLastName : " + userLastName);

                if (!APPUtills.isThisStringValid(userName) || !APPUtills.isThisStringValid(userFirstName) || !APPUtills.isThisStringValid(userLastName)) {
                    throw new SBLException("Please fill all input field.");
                }

                if (!id.equalsIgnoreCase("")) {//for modifying a rejected record
                    System.out.println("MESSAGE | modifying a rejected record");
                    userMap = objManager.get("frontDeskUsers") != null ? (HashMap) objManager.get("frontDeskUsers") : new HashMap();

                    userModel = userMap.get(Integer.parseInt(id));
                    if (userModel == null) {
                        System.out.println("ERROR | Can not found the object from map.");
                        throw new SBLException(ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
                    }
                    if (userModel.getFdUserName().equalsIgnoreCase(userName) && userModel.getFdUserFirstName().equalsIgnoreCase(userFirstName) && userModel.getFdUserLastName().equalsIgnoreCase(userLastName)) {
                        throw new SBLException(ApplicationConstants.ERR_MSG_VALUES_ARE_SAME);
                    }
                    userModel.setFdUserName(userName);
                    userModel.setFdUserFirstName(userFirstName);
                    userModel.setFdUserLastName(userLastName);
                } else { //for addding new record
                    System.out.println("MESSAGE | add new record");//ApplicationConstants.APP_CODE + userName
                    userModel = new FdUserModel(userName, userFirstName, userLastName, ApplicationConstants.STATUS_ACTIVE, ApplicationConstants.ACTION_TYPE_NEW, ApplicationConstants.SECUREPASS_USER_REGISTER);
                }
            }

            System.out.println("MESSAGE | Is Front desk user Saved ? " + fDUserLogic.saveFdUser(userModel, userData, isSecurepassProcess));

            eventAction = ApplicationConstants.actionTypeDesc(userModel.getActionType());
            eventDesc = (userModel.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_NEW) ? "Add new " : "Edit ") + "front end user temp id : " + userModel.getFdUserTmpId();
            tempObjectToString = ((FdUserTmp)userModel.modelToObject(ApplicationConstants.TEMP_DATA)).toString();
            eventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "  Save - Success. ", "", tempObjectToString, ApplicationConstants.EVENTSUCCESSFUL);
            req.setAttribute("rtnMsg", "Successfully save.");
        } catch (SBLException ex) {
            eventLogger.doLog(req, userData.getUSER_ID(), ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "  Save - Failed. ", "", tempObjectToString, ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR | " + ex.getMessage());
            throw new SBLException(ex.getMessage());
        } catch (Exception ex) {
            eventLogger.doLog(req, userData.getUSER_ID(), ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "  Save - Failed. ", "", tempObjectToString, ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR   | " + ex.getMessage() + "\n");
            throw new SBLException("Unable to save front desk users details");
        }

        System.out.println("LEFT    |  FrontDeskUserMngAction.saveFrontDeskUser()");
    }
	
//-------------------------------------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/FrontDeskUser/RejectFrontDeskUser")
	public String rejectFrontDeskUser(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws SBLException {
        System.out.println("ENTERED |  FrontDeskUserMngAction.rejectFrontDeskUser()");
        Map<Integer, FdUserModel> userMap = new HashMap();
        FdUserModel userModelFromObMng = new FdUserModel();
        FdUserModel userModel;
        String eventAction = "";
        String eventDesc = "";
        String masterObjectToString = "";
        String tempObjectToString = "";
        ObjectManager objManager = null;
		UserData userData=null;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				throw new Exception(result);
			}
			
            String userTmpId = req.getParameter("id");
            String reason = req.getParameter("reason");
            System.out.println("MESSAGE | userTmpId : " + userTmpId + " | reason : " + reason);

            if (!APPUtills.isThisStringValid(reason)) {
                throw new SBLException("Comment is required.");
            }
            userMap = objManager.get("frontDeskUsers") != null ? (HashMap) objManager.get("frontDeskUsers") : new HashMap();
            userModelFromObMng = userMap.get(Integer.parseInt(userTmpId));
            userModel = (FdUserModel) userModelFromObMng.clone();
            if (userModel == null) {
                System.out.println("ERROR | Can not found the object from map.");
                throw new SBLException(ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
            }

            userModel.setAuthComment(reason);
            boolean isSuccess = fDUserLogic.rejectFdUser(userModel, userData);
            System.out.println("MESSAGE | Is Front desk user Rejected ? " + isSuccess);

            eventAction = ApplicationConstants.actionTypeDesc(userModel.getActionType());
            eventDesc = "Front desk user temp Id : " + userModel.getFdUserTmpId() + ", Reject - ";
            masterObjectToString = userModel.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY) ? ((FdUserMaster)userModel.modelToObject(ApplicationConstants.MASTER_DATA)).toString() : "";
            tempObjectToString = ((FdUserTmp)userModel.modelToObject(ApplicationConstants.TEMP_DATA)).toString();
            // Write to event log table.
            eventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Successful. ", masterObjectToString, tempObjectToString, ApplicationConstants.EVENTSUCCESSFUL);

            req.setAttribute("rtnMsg", "Successfully reject.");
        } catch (SBLException ex) {
            eventLogger.doLog(req, (userData!=null?userData.getUSER_ID():""), ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Fail. " + "Message : " + ex.getMessage(), masterObjectToString, tempObjectToString, ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR | " + ex.getMessage());
            req.setAttribute("errMsg", ex.getMessage());
            return "frontDeskUser/pendingFrontDeskUser";
        } catch (Exception ex) {
            eventLogger.doLog(req, (userData!=null?userData.getUSER_ID():""), ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Fail. " + "Message : " + ex.getMessage(), masterObjectToString, tempObjectToString, ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR   | " + ex.getMessage() + "\n");
            req.setAttribute("errMsg", ex.getMessage());
            return "frontDeskUser/pendingFrontDeskUser";
        }

        System.out.println("LEFT    | FrontDeskUserMngAction.rejectFrontDeskUser()");
        return "redirect:/FrontDeskUser/PendingFrontDeskUsers";
    }

	@RequestMapping(value = "FrontDeskUser/RemoveFrontDeskUser")
    public String cancelFrontDeskUserModification(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws SBLException {
        System.out.println("ENTERED |  FrontDeskUserMngAction.cancelFrontDeskUserModification()");
        Map<Integer, FdUserModel> userMap = new HashMap();
        FdUserModel userModelFromObMng = new FdUserModel();
        FdUserModel userModel;
        String eventAction = "";
        String eventDesc = "";
        String masterObjectToString = "";
        String tempObjectToString = "";
        ObjectManager objManager = null;
		UserData userData=null;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				throw new Exception(result);
			}
            String userTmpId = req.getParameter("id");
            System.out.println("MESSAGE | userTmpId : " + userTmpId);

            userMap = objManager.get("frontDeskUsers") != null ? (HashMap) objManager.get("frontDeskUsers") : new HashMap();

            userModelFromObMng = userMap.get(Integer.parseInt(userTmpId));
            userModel = (FdUserModel) userModelFromObMng.clone();
            if (userModel == null) {
                System.out.println("ERROR | Can not found the object from map.");
                throw new SBLException(ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
            }
            boolean isSuccess = fDUserLogic.deleteFdUser(userModel);
            System.out.println("MESSAGE | Is Front desk user Rejected ? " + isSuccess);

            eventAction = ApplicationConstants.actionTypeDesc(userModel.getActionType());
            eventDesc = "Front desk user temp Id : " + userModel.getFdUserTmpId() + ", Cancel - ";
            tempObjectToString = ((FdUserTmp)userModel.modelToObject(ApplicationConstants.TEMP_DATA)).toString();
            // Write to event log table.
            eventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Successful. ", masterObjectToString, tempObjectToString, ApplicationConstants.EVENTSUCCESSFUL);

            req.setAttribute("rtnMsg", "Successfully delete.");
        } catch (SBLException ex) {
            eventLogger.doLog(req, (userData!=null?userData.getUSER_ID():""), ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Fail. " + "Message : " + ex.getMessage(), masterObjectToString, tempObjectToString, ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR | " + ex.getMessage());
            req.setAttribute("errMsg", ex.getMessage());
            return "frontDeskUser/pendingFrontDeskUser";
        } catch (Exception ex) {
            eventLogger.doLog(req, (userData!=null?userData.getUSER_ID():""), ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Fail. " + "Message : " + ex.getMessage(), masterObjectToString, tempObjectToString, ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR   | " + ex.getMessage() + "\n");
            req.setAttribute("errMsg", ex.getMessage());
            return "frontDeskUser/pendingFrontDeskUser";
        }

        System.out.println("LEFT    | FrontDeskUserMngAction.cancelFrontDeskUserModification()");
        return "redirect:/FrontDeskUser/PendingFrontDeskUsers";
    }
    
    
    @RequestMapping(value = "/FrontDeskUser/VerifyFrontDeskUser")
    public String verifyFrontDeskUser(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws SBLException {
        System.out.println("ENTERED |  FrontDeskUserMngAction.verifyFrontDeskUser()");
        ObjectManager objManager = null;
		UserData userData;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				throw new Exception(result);
			}
			verifyFrontDeskUser(objManager, userData, false, req);
            
        } catch (SBLException ex) {System.out.println("ERROR | " + ex.getMessage());
            req.setAttribute("errMsg", ex.getMessage());
            return "frontDeskUser/pendingFrontDeskUser";
        } catch (Exception ex) {
        	System.out.println("ERROR   | " + ex.getMessage() + "\n");
            req.setAttribute("errMsg", ex.getMessage());
            return "frontDeskUser/pendingFrontDeskUser";
        }

        System.out.println("LEFT    | FrontDeskUserMngAction.verifyFrontDeskUser()");
        return "redirect:/FrontDeskUser/PendingFrontDeskUsers";
    }
    
    @RequestMapping(value = "/FrontDeskUser/VerifyFrontDeskUserPassword")
    public String VerifyFrontDeskUserPassword(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws SBLException {
        System.out.println("ENTERED |  FrontDeskUserMngAction.VerifyFrontDeskUserPassword()");
        ObjectManager objManager = null;
		UserData userData;
		try {
			objManager = new ObjectManager(session);
			userData = (UserData) objManager.get("userData");

			String result = checkSessionTimeOut(userData, objManager);
			if (APPUtills.isThisStringValid(result)) {
				req.setAttribute("errMsg", result);
				throw new Exception(result);
			}
			verifyFrontDeskUser(objManager, userData, true, req);
            
        } catch (SBLException ex) {System.out.println("ERROR | " + ex.getMessage());
            req.setAttribute("errMsg", ex.getMessage());
            return "frontDeskUser/pendingFrontDeskUser";
        } catch (Exception ex) {
        	System.out.println("ERROR   | " + ex.getMessage() + "\n");
            req.setAttribute("errMsg", ex.getMessage());
            return "frontDeskUser/pendingFrontDeskUser";
        }

        System.out.println("LEFT    | FrontDeskUserMngAction.VerifyFrontDeskUserPassword()");
        return "redirect:/FrontDeskUser/PendingFrontDeskUsers";
    }

    private void verifyFrontDeskUser(ObjectManager objManager, UserData userData, boolean isSecurepassProcess, HttpServletRequest req) throws SBLException {
        System.out.println("ENTERED | FrontDeskUserMngAction.verifyFrontDeskUser()");
        Map<Integer, FdUserModel> userMap = new HashMap();
        FdUserModel userModelFromObMng = new FdUserModel();
        FdUserModel userModel;
        String eventAction = "";
        String eventDesc = "";
        String masterObjectToString = "";
        String tempObjectToString = "";
		try {
            String userTmpId = req.getParameter("id");
            String reason = req.getParameter("reason");
            System.out.println("MESSAGE | userTmpId : " + userTmpId + " | reason : " + reason);

            userMap = objManager.get("frontDeskUsers") != null ? (HashMap) objManager.get("frontDeskUsers") : new HashMap();

            userModelFromObMng = userMap.get(Integer.parseInt(userTmpId));
            userModel = (FdUserModel) userModelFromObMng.clone();
            if (userModel == null) {
                System.out.println("ERROR | Can not found the object from map.");
                throw new SBLException(ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
            }
            /*if (!doSecurePassProcess(objManager, userData, req)) {
                throw new SBLException("Failed to create a user in Secure Pass Service.");
            }
            */

            userModel.setAuthComment(reason);
            boolean isSuccess = fDUserLogic.verifyUser(userModel, userData, isSecurepassProcess);
            System.out.println("MESSAGE | Is Front desk user Verify ? " + isSuccess);

            eventAction = ApplicationConstants.actionTypeDesc(userModel.getActionType());
            eventDesc = "Front desk user temp Id : " + userModel.getFdUserTmpId() + ", Verification - ";
            masterObjectToString = ((FdUserMaster)userModel.modelToObject(ApplicationConstants.MASTER_DATA)).toString();
            tempObjectToString = ((FdUserTmp)userModel.modelToObject(ApplicationConstants.TEMP_DATA)).toString();
            eventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Successful. ", masterObjectToString, tempObjectToString, ApplicationConstants.EVENTSUCCESSFUL);

            req.setAttribute("rtnMsg", "Successfully verify.");

        } catch (SBLException ex) {
            eventLogger.doLog(req, userData!=null?userData.getUSER_ID():"", ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Fail. " + "Message : " + ex.getMessage(), masterObjectToString, tempObjectToString, ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR | " + ex.getMessage());
            req.setAttribute("errMsg", ex.getMessage());
        } catch (Exception ex) {
            eventLogger.doLog(req, userData!=null?userData.getUSER_ID():"", ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Fail. " + "Message : " + ex.getMessage(), masterObjectToString, tempObjectToString, ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR   | " + ex.getMessage() + "\n");
            req.setAttribute("errMsg", ex.getMessage());
        }

        System.out.println("LEFT    | FrontDeskUserMngAction.verifyFrontDeskUser()");
    }
    
    
    @RequestMapping(value = "/FrontDeskUser/reSetFrontDeskUserPassword")
    public String reSetFrontDeskUserPassword(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws SBLException {
        System.out.println("ENTERED |  FrontDeskUserMngAction.reSetFrontDeskUserPassword()");
        Map<Integer, FdUserModel> userMap = new HashMap();
        FdUserModel userModelFromObMng = new FdUserModel();
        FdUserModel userModel;
        String eventAction = "";
        String eventDesc = "";
        String masterObjectToString = "";
        String tempObjectToString = "";
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
			
			 String userId = req.getParameter("id");
			 userMap = objManager.get("frontDeskUsers") != null ? (HashMap) objManager.get("frontDeskUsers") : new HashMap();
			 userModelFromObMng = userMap.get(Integer.parseInt(userId));
	            userModel = (FdUserModel) userModelFromObMng.clone();
	            if (userModel == null) {
	                System.out.println("ERROR | Can not found the object from map.");
	                throw new SBLException(ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
	            }
	            
	            userModel.setSecurepassUserStatus(ApplicationConstants.SECUREPASS_USER_SET_PASSWORD);;
	            
	            boolean isSuccess = fDUserLogic.saveFdUserResetPassword(userModel, userData);
	            System.out.println("MESSAGE | Is Front desk user Verify ? " + isSuccess);

	            eventAction = ApplicationConstants.actionTypeDesc(userModel.getActionType());
	            eventDesc = "Front desk user temp Id : " + userModel.getFdUserTmpId() + ", Password reSet sent for authorization - ";
	            masterObjectToString = ((FdUserMaster)userModel.modelToObject(ApplicationConstants.MASTER_DATA)).toString();
	            tempObjectToString = ((FdUserTmp)userModel.modelToObject(ApplicationConstants.TEMP_DATA)).toString();
	            eventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Successful. ", masterObjectToString, tempObjectToString, ApplicationConstants.EVENTSUCCESSFUL);

	            req.setAttribute("rtnMsg", "Successfully save.");
			
            
        } catch (SBLException ex) {
        	 System.out.println("ERROR | " + ex.getMessage());
        	 eventLogger.doLog(req, userData!=null?userData.getUSER_ID():"", ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Fail. " + "Message : " + ex.getMessage(), masterObjectToString, tempObjectToString, ApplicationConstants.EVENTFAIL);
             req.setAttribute("errMsg", ex.getMessage());
        } catch (Exception ex) {
        	System.out.println("ERROR   | " + ex.getMessage() + "\n");
        	 eventLogger.doLog(req, userData!=null?userData.getUSER_ID():"", ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Fail. " + "Message : " + ex.getMessage(), masterObjectToString, tempObjectToString, ApplicationConstants.EVENTFAIL);
             req.setAttribute("errMsg", ex.getMessage());
        }

        System.out.println("LEFT    | FrontDeskUserMngAction.reSetFrontDeskUserPassword()");
        return "redirect:/FrontDeskUser/ExistingFrontDeskUsers";// PendingFrontDeskUsers
    }
    
    @RequestMapping(value = "/FrontDeskUser/verifyReSetFrontDeskUserPassword")
    public String verifyReSetFrontDeskUserPassword(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws SBLException {
        System.out.println("ENTERED | FrontDeskUserMngAction.verifyReSetFrontDeskUserPassword()");
        Map<Integer, FdUserModel> userMap = new HashMap();
        FdUserModel userModelFromObMng = new FdUserModel();
        FdUserModel userModel;
        String eventAction = "";
        String eventDesc = "";
        String masterObjectToString = "";
        String tempObjectToString = "";
        ObjectManager objManager = null;
		UserData userData = null;
		try {
            String userTmpId = req.getParameter("id");
            String reason = req.getParameter("reason");
            System.out.println("MESSAGE | userTmpId : " + userTmpId + " | reason : " + reason);

            userMap = objManager.get("frontDeskUsers") != null ? (HashMap) objManager.get("frontDeskUsers") : new HashMap();

            userModelFromObMng = userMap.get(Integer.parseInt(userTmpId));
            userModel = (FdUserModel) userModelFromObMng.clone();
            if (userModel == null) {
                System.out.println("ERROR | Can not found the object from map.");
                throw new SBLException(ApplicationConstants.ERR_MSG_SESSION_TERMINTATED);
            }
            userModel.setAuthComment(reason);
            boolean isSuccess = fDUserLogic.verifyFdUserResetPassword(userModel, userData);
            System.out.println("MESSAGE | Is Front desk user Verify ? " + isSuccess);

            eventAction = ApplicationConstants.actionTypeDesc(userModel.getActionType());
            eventDesc = "Front desk user temp Id : " + userModel.getFdUserTmpId() + ", Verification - ";
            masterObjectToString = ((FdUserMaster)userModel.modelToObject(ApplicationConstants.MASTER_DATA)).toString();
            tempObjectToString = ((FdUserTmp)userModel.modelToObject(ApplicationConstants.TEMP_DATA)).toString();
            eventLogger.doLog(req, String.valueOf(userData.getUSER_ID()), ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Successful. ", masterObjectToString, tempObjectToString, ApplicationConstants.EVENTSUCCESSFUL);

            req.setAttribute("rtnMsg", "Successfully verify.");

        } catch (SBLException ex) {
            eventLogger.doLog(req, userData!=null?userData.getUSER_ID():"", ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Fail. " + "Message : " + ex.getMessage(), masterObjectToString, tempObjectToString, ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR | " + ex.getMessage());
            req.setAttribute("errMsg", ex.getMessage());
            return "frontDeskUser/pendingFrontDeskUser";
        } catch (Exception ex) {
            eventLogger.doLog(req, userData!=null?userData.getUSER_ID():"", ApplicationConstants.FRONT_DESK_USER, eventAction, eventDesc + "Fail. " + "Message : " + ex.getMessage(), masterObjectToString, tempObjectToString, ApplicationConstants.EVENTFAIL);
            System.out.println("ERROR   | " + ex.getMessage() + "\n");
            req.setAttribute("errMsg", ex.getMessage());
            return "frontDeskUser/pendingFrontDeskUser";
        }

        System.out.println("LEFT    | FrontDeskUserMngAction.verifyReSetFrontDeskUserPassword()");  
        return "redirect:/FrontDeskUser/PendingFrontDeskUsers";// ExistingFrontDeskUsers
    }
    
	
	public String checkSessionTimeOut(UserData userData, ObjectManager objManager) {
		try {
			if (userData == null) {
				objManager.remove("userType");
				objManager.remove("userData");
				objManager.cleanup(ApplicationConstants.SCOPE_GLOBAL);
				objManager.cleanup(ApplicationConstants.SCOPE_COMPANY_USER);
				objManager.cleanup(ApplicationConstants.SCOPE_COMMON_USER);
				objManager.cleanup(ApplicationConstants.SCOPE_FD_USER);
				objManager.cleanup(ApplicationConstants.SCOPE_COMMON_VIEW);

				return ApplicationConstants.ERR_MSG_SESSION_TERMINTATED;
			} else {
				return "";
			}
		} catch (Exception e) {
			return "Error in checking session terminated";
		}
	}
}
