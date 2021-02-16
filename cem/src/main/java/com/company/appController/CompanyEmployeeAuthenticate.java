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
import com.company.bl.FDUserLogicLocal;
import com.company.bl.UserReferenceLogicLocal;
import com.company.common.APPUtills;
import com.company.common.AppConstants;
import com.company.common.ApplicationConstants;
import com.company.common.ObjectManager;
import com.company.common.SBLException;
import com.company.models.CompanyUserModel;
import com.company.models.FdUserModel;
import com.company.models.UserData;


@Controller
@ComponentScan(basePackages = {"com.company.bl"})
public class CompanyEmployeeAuthenticate {
	//private static final Log log = LogFactory.getLog(CompanyEmployeeAuthenticate.class);
	
	@Autowired
    private FDUserLogicLocal fdUserLogic;
	
	//@Autowired
    //private EventLoggerLocal eventLogger = new EventLogger();
	
	@Autowired
    private CompanyUserLogicLocal companyUserLogic;
	
	@Autowired
    private UserReferenceLogicLocal userReferenceLogic;

	
	@RequestMapping(value = { "/authonticate","/authonticate/RedirectLogin" })
	public ModelAndView loginPage(){
		try {
			return new ModelAndView("main/login");          
        } catch (Exception ex) {
        	return new ModelAndView("main/login");   
        }		
	}
	
	@RequestMapping(value = { "/authonticate/LoadDashboard" })
	public ModelAndView LoadDashboard(){
		try {
			return new ModelAndView("main/login");          
        } catch (Exception ex) {
        	return new ModelAndView("main/login");   
        }		
	}
	
	@RequestMapping(value = { "/authonticate/UserLogin" })
	public ModelAndView loginPage(HttpServletRequest req, HttpServletResponse resp,HttpSession session){		
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
                	req.setAttribute("errMsg", response);            	
                	return new ModelAndView("main/login");
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
                
                return new ModelAndView("main/home");
                //System.out.println("MESSAGE | is upateReference table : " + userReferenceLogic.upateReferenceByUserId(userData));
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
                    	req.setAttribute("errMsg", response);            	
                    	return new ModelAndView("main/login");
                    }
                    
                    System.out.println("MESSAGE | Login Successful");
                    
                    userData = new UserData();
                    userData.setUSER_TYPE(ApplicationConstants.FRONT_DESK_USER);
                    userData.setUSER_ID(dfum.getFdUserMasterId()+"");
                    userData.setUSER_NAME(dfum.getFdUserName());
                    userData.setFIRST_NAME(dfum.getFdUserFirstName());
                    userData.setLAST_NAME(dfum.getFdUserLastName());
                    
                    
                    System.out.println("MESSAGE | successfully login.");
                    //System.out.println("MESSAGE | is upateReference table : " + userReferenceLogic.upateReferenceByUserId(userData));
                    
                    System.out.println("MESSAGE | LOGGIN SUCCESS.");
        			return new ModelAndView("main/home");
                    
                } else if (dfum.getSecurepassUserStatus().equalsIgnoreCase(ApplicationConstants.SECUREPASS_USER_REGISTER)) {//going to set password
                    System.out.println("MESSAGE | ReDirect to password setup.");
        			return new ModelAndView("main/login");
                }
                return new ModelAndView("main/login");
            }			

		}catch (SBLException ex) {
			System.out.println("ERROR | Error Occured." + ex.getMessage());
			//throw new SBLException("Unable to fetch data.");
			return new ModelAndView("main/login");
		}catch (Exception ex) {
			System.out.println("ERROR | Error Occured." + ex.getMessage());
			//throw new SBLException("Unable to fetch data.");
			return new ModelAndView("main/login");
		}

	}
	private void processLogoff(HttpSession session, HttpServletRequest req, ObjectManager objManager) throws Exception, SBLException {
        System.out.println("ENTERED | EmployeeLoginAction.processLogoff()");
        try {
            UserData userData = objManager.get("userData") != null ? (UserData) objManager.get("userData") : new UserData();
            // Get the login user
            objManager.remove("userData");
            objManager.cleanup();
            //eventLogger.doLog(req, userData.getAD_USER_ID(), ApplicationConstants.EVENT_LOG_LOGIN, "LOGOFF", "Logoff Successful", "", "", ApplicationConstants.EVENTSUCCESSFUL);
        } catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage());
            throw new SBLException("Logoff process failed");
        }

        System.out.println("LEFT    | EmployeeLoginAction.processLogoff()");
    }
	
	@RequestMapping(value = { "/authonticate/UserLogout" })
	public ModelAndView userLogout(HttpServletRequest req, HttpServletResponse resp,HttpSession session){
		System.out.println("ENTERED | EmployeeLoginAction.userLogout()");
		ModelAndView mv = null;
		ObjectManager objManager = null;
		try {
			objManager = new ObjectManager(session);
			processLogoff(session, req, objManager);            
        } catch (Exception ex) {
            //System.out.println("ERROR   | Unable to fetch list of object from temp table." + ex.getMessage(), ex);
            //throw new SBLException("Unable to fetch data.");
        }
		System.out.println("LEFT | EmployeeLoginAction.userLogout()");
		return new ModelAndView("main/login");
		
	}
	
	
}
