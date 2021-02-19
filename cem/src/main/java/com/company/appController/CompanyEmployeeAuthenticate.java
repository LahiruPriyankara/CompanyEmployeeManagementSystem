package com.company.appController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company.bl.CompanyUserLogicLocal;
import com.company.bl.EventLoggerLocal;
import com.company.bl.FDUserLogicLocal;
import com.company.bl.UserReferenceLogicLocal;
import com.company.common.APPUtills;
import com.company.common.AppConstants;
import com.company.common.ApplicationConstants;
import com.company.common.ObjectManager;
import com.company.common.SBLException;
import com.company.init.Initializer;
import com.company.models.CompanyUserModel;
import com.company.models.FdUserModel;
import com.company.models.UserData;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;


@Controller
@ComponentScan(basePackages = {"com.company.bl"})
public class CompanyEmployeeAuthenticate {
	//private static final Log log = LogFactory.getLog(CompanyEmployeeAuthenticate.class);
	
	@Autowired
    private FDUserLogicLocal fdUserLogic;
	
	@Autowired
    private EventLoggerLocal eventLogger;
	
	@Autowired
    private CompanyUserLogicLocal companyUserLogic;
	
	@Autowired
    private UserReferenceLogicLocal userReferenceLogic;

	
	@RequestMapping(value = {"/", "/authonticate","/authonticate/RedirectLogin"})
	public ModelAndView loginPage(HttpServletRequest req, HttpServletResponse resp,HttpSession session){
		ObjectManager objManager = null;
		try {
			System.out.println("GOING TO INITIALIZE THE PARAMETERS. START");
			Initializer.initCommonParams();
			System.out.println("GOING TO INITIALIZE THE PARAMETERS. END");
			
			objManager = new ObjectManager(session);
			objManager.remove("isPswdReset");
			 
			return new ModelAndView("main/login");          
        } catch (Exception ex) {
        	return new ModelAndView("main/login");   
        }		
	}
	
	@RequestMapping(value = { "/authonticate/LoadDashboard" })
	public ModelAndView LoadDashboard(HttpServletRequest req, HttpServletResponse resp,HttpSession session){
		ObjectManager objManager = null;
		UserData userData = null;
		try {
			objManager = new ObjectManager(session);
        	userData = (UserData) objManager.get("userData");
        	
        	String result = checkSessionTimeOut(userData,objManager);
        	if(APPUtills.isThisStringValid(result)){
        		req.setAttribute("errMsg", result);
        		return new ModelAndView("includes/include-dashboard");
        	}
			
			return new ModelAndView("includes/include-dashboard");          
        } catch (Exception ex) {
        	return new ModelAndView("includes/include-dashboard");   
        }		
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
	
	@RequestMapping(value = { "/company" })
	public ModelAndView login(HttpServletRequest req, HttpServletResponse resp,HttpSession session){		
		HttpURLConnection conn = null;
		URL url;
        String serviceUrl;
        BufferedReader br;
        String response = "",responseFromBR;
		ObjectManager objManager = null;
		UserData userData = null;
        int iStatus = -1;
        String userName = "", password = "";
        boolean isbankUser = false, isPasswordSet = false;
        
		try {
			//ModelAndView mv = new ModelAndView("main/home");
			objManager = new ObjectManager(session);
			
			userName = req.getParameter("txtUserId").trim();
            password = req.getParameter("txtPassword").trim();
            isbankUser = req.getParameter("cBoxuserType") != null;
            System.out.println("userName : "+userName+", password "+password+", isbankUser "+isbankUser);
            
            if (isbankUser) {//going to log as bank user
                if (!APPUtills.isThisStringValid(userName) || !APPUtills.isThisStringValid(password)) {
                    throw new SBLException("Username and Password cannot be empty.");
                }
                processLogoff(session, req, objManager);

                System.out.println("MESSAGE | Bank User going to loggin.");
                //going to log user through company service	---------------------------------------------
    			serviceUrl = ApplicationConstants.LOGGIN_COMPANY_USER + "/" + userName + "/" +password;
                System.out.println("MESSAGE | Service URL : " + serviceUrl);
                url = new URL(serviceUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                /*  String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
                        OutputStream os = conn.getOutputStream();
                        os.write(input.getBytes());
                        os.flush();*/
                //APPUtills.setProxySettings();
                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                	System.out.println("ERROR   | Failed : HTTP error code : " + conn.getResponseCode());
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                response = "";
                responseFromBR = "";
                while ((responseFromBR = br.readLine()) != null) {
                	System.out.println("MESSAGE | Response : " + responseFromBR);
                	response = responseFromBR;
                }
                
                if(!response.equalsIgnoreCase(ApplicationConstants.LOGIN_SUCCESS)){
                	System.out.println("MESSAGE | LOGGIN FAIL.");
                	throw new Exception(response);
                }		
                System.out.println("MESSAGE | LOGGIN SUCCESS.");
                //getting logged user details from company service.	---------------------------------------------
                serviceUrl = ApplicationConstants.GET_COMPANY_USER_BY_USER_ID + "/" + userName;
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
                //response = "";
                responseFromBR = "";
                while ((responseFromBR = br.readLine()) != null) {
                	System.out.println("MESSAGE | Response : " + responseFromBR);
                	userData = new UserData();
                    userData.setUSER_TYPE(ApplicationConstants.BANK_DEP_USER);

                    JSONObject jSONObject = new JSONObject(responseFromBR);
                    userData.setUSER_ID(jSONObject.get("id") != null ? jSONObject.get("id").toString() : "");
                    userData.setFIRST_NAME(jSONObject.get("user_first_name") != null ? jSONObject.get("user_first_name").toString() : "");
                    userData.setLAST_NAME(jSONObject.get("user_last_name") != null ? jSONObject.get("user_last_name").toString() : "");
                    userData.setDIV_CODE(jSONObject.get("user_dep_id") != null ? jSONObject.get("user_dep_id").toString() : "");
                    userData.setDIV_NAME(jSONObject.get("user_dep_name") != null ? jSONObject.get("user_dep_name").toString() : "");
                    userData.setSECURITY_CLASS(jSONObject.get("userRole") != null ? jSONObject.get("userRole").toString() : "");
                    userData.setAD_USER_ID(jSONObject.get("user_id") != null ? jSONObject.get("user_id").toString() : "");
                    
                    System.out.println("MESSAGE | userData.toString() "+userData.toString());
                }
                //getting logged user details from cem system databasee.	---------------------------------------------
                Map<String, CompanyUserModel> dfumsMap = companyUserLogic.getCompanyUsers(ApplicationConstants.MASTER_DATA, Arrays.asList(userData.getUSER_ID()), null);//String tableType, List<String> ids, String depCode               
                CompanyUserModel model = dfumsMap.get(userData.getUSER_ID());                
                userData.setBase64Image(model!=null?model.getBase64Image():"");
                userData.setUSER_TYPE(ApplicationConstants.BANK_EMPLOYEE);
            }else{
                FdUserModel dfum = fdUserLogic.getFdUserByUserName(Arrays.asList(userName));
                if (dfum == null) {
                    throw new SBLException("Invalied Username.");
                } else if (dfum.getSecurepassUserStatus().equalsIgnoreCase(ApplicationConstants.SECUREPASS_USER_ACTIVE)) {//going to log as front desk user  
                    if (!APPUtills.isThisStringValid(userName) || !APPUtills.isThisStringValid(password)) {
                        throw new SBLException("Username and Password cannot be empty.");
                    }
                    processLogoff(session, req, objManager);
                    
                    
                    //System.out.println(fdUserLogic.test());			
        			serviceUrl = ApplicationConstants.LOGGIN_NON_COMPANY_USER + "/" + userName + "/" +password;
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
                    
                    if(!response.equalsIgnoreCase(ApplicationConstants.LOGIN_SUCCESS)){
                    	System.out.println("MESSAGE | LOGGIN FAIL.");    
                    	if(response.equalsIgnoreCase(ApplicationConstants.LOCK_USER)){
                    		dfum.setSecurepassUserStatus(ApplicationConstants.LOCK_USER);
                    		fdUserLogic.updateFdUserPasswordLock(dfum);
                    	}
                    	
                    	throw new Exception(response);
                    }
                    
                    System.out.println("MESSAGE | Login Successful");
                    
                    userData = new UserData();
                    userData.setUSER_TYPE(ApplicationConstants.FRONT_DESK_USER);
                    userData.setUSER_ID(dfum.getFdUserMasterId()+"");
                    userData.setUSER_NAME(dfum.getFdUserName());
                    userData.setFIRST_NAME(dfum.getFdUserFirstName());
                    userData.setLAST_NAME(dfum.getFdUserLastName());                  
                    
                }else if (dfum.getSecurepassUserStatus().equalsIgnoreCase(ApplicationConstants.SECUREPASS_USER_REGISTER) || dfum.getSecurepassUserStatus().equalsIgnoreCase(ApplicationConstants.SECUREPASS_USER_SET_PASSWORD_AUTH)) {//going to set password
                    System.out.println("MESSAGE | ReDirect to password setup.");
                    objManager.put("isPswdReset",true);
                    eventLogger.doLog(req, userData!=null?userData.getAD_USER_ID():userName, ApplicationConstants.EVENT_LOG_LOGIN, "PASSWORDSET", "password set Successful", "", "", ApplicationConstants.EVENTSUCCESSFUL);
        			return new ModelAndView("main/login");
                }
            }	
            
            System.out.println("MESSAGE | successfully login.");
            System.out.println("MESSAGE | is upateReference table : " + userReferenceLogic.upateReferenceByUserId(userData)); 
            objManager.put("userData",userData,ApplicationConstants.SCOPE_GLOBAL);
            eventLogger.doLog(req, userData.getAD_USER_ID(), ApplicationConstants.EVENT_LOG_LOGIN, "LOGIN", "Login Successful", "", "", ApplicationConstants.EVENTSUCCESSFUL);
		
            return new ModelAndView("main/home");

		}catch (SBLException ex) {
			eventLogger.doLog(req, userData!=null?userData.getAD_USER_ID():"", ApplicationConstants.EVENT_LOG_LOGIN, "LOGIN", "Login Fail", "", "", ApplicationConstants.EVENTFAIL);
			System.out.println("ERROR | Error Occured." + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			return new ModelAndView("main/login");
		}catch (Exception ex) {
			eventLogger.doLog(req, userData!=null?userData.getAD_USER_ID():"", ApplicationConstants.EVENT_LOG_LOGIN, "LOGIN", "Login Fail", "", "", ApplicationConstants.EVENTFAIL);
			System.out.println("ERROR | Error Occured." + ex.getMessage());
			req.setAttribute("errMsg", ex.getMessage());
			return new ModelAndView("main/login");
		}

	}
		
	@RequestMapping(value = { "/authonticate/SetPassword" })
	public ModelAndView setPassword(HttpServletRequest req, HttpServletResponse resp,HttpSession session){
		System.out.println("ENTERED | EmployeeLoginAction.setPassword()");
		ModelAndView mv = null;
		ObjectManager objManager = null;
		//UserData userData = null;
		String userName = "", password = "", confirmPassword = "";
        try {
        	System.out.println("MESSAGE | Front Desk User Password Resetting.");

        	objManager = new ObjectManager(session);
            //userData = (UserData) objManager.get("userData");
        	
            userName = req.getParameter("txtUserId").trim();
            password = req.getParameter("txtPassword").trim();
            confirmPassword = req.getParameter("txtConformPassword").trim();

            if (!APPUtills.isThisStringValid(userName) || !APPUtills.isThisStringValid(password)) {
                throw new SBLException("Username and Password cannot be empty.");
            } else if (!password.equalsIgnoreCase(confirmPassword)) {
                throw new SBLException("Passwords are not match.");
            }
            if (fdUserLogic.setPassword(userName, password)) {
                //If success
                objManager.remove("isPswdReset");
                System.out.println("MESSAGE | New password successfull save.");
            }
			
        } catch (Exception ex) {
        	eventLogger.doLog(req, "", ApplicationConstants.EVENT_LOG_LOGIN, "LOGOFF", "Logoff fail", "", "", ApplicationConstants.EVENTFAIL);
        	req.setAttribute("errMsg", ex.getMessage());
        }
		System.out.println("LEFT | EmployeeLoginAction.setPassword()");
		return new ModelAndView("main/login");	
	}
		
	private void processLogoff(HttpSession session, HttpServletRequest req, ObjectManager objManager) throws Exception, SBLException {
        System.out.println("ENTERED | EmployeeLoginAction.processLogoff()");
        try {
            UserData userData = objManager.get("userData") != null ? (UserData) objManager.get("userData") : new UserData();
            // Get the login user
            objManager.remove("userData");
            objManager.cleanup();
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            throw new SBLException("Logoff process failed");
        }
        System.out.println("LEFT    | EmployeeLoginAction.processLogoff()");
    }
	
	@RequestMapping(value = { "/authonticate/UserLogout" })
	public String userLogout(HttpServletRequest req, HttpServletResponse resp,HttpSession session){
		System.out.println("ENTERED | EmployeeLoginAction.userLogout()");
		ModelAndView mv = null;
		ObjectManager objManager = null;
		UserData userData;
		try {
			userData = (UserData) objManager.get("userData");
			objManager = new ObjectManager(session);
			processLogoff(session, req, objManager);   
			if(userData == null){
				eventLogger.doLog(req, "", ApplicationConstants.EVENT_LOG_LOGIN, "LOGOFF", "Logoff Successful", "", "", ApplicationConstants.EVENTSUCCESSFUL);
			}else{
				eventLogger.doLog(req, userData.getAD_USER_ID(), ApplicationConstants.EVENT_LOG_LOGIN, "LOGOFF", "Logoff Successful", "", "", ApplicationConstants.EVENTSUCCESSFUL);
			}
			
        } catch (Exception ex) {
        	eventLogger.doLog(req, "", ApplicationConstants.EVENT_LOG_LOGIN, "LOGOFF", "Logoff fail", "", "", ApplicationConstants.EVENTFAIL);
        	req.setAttribute("errMsg", ex.getMessage());
        }
		System.out.println("LEFT | EmployeeLoginAction.userLogout()");
		//return new ModelAndView("main/login");	
		return "redirect:/authonticate";
	}
	
	
}
