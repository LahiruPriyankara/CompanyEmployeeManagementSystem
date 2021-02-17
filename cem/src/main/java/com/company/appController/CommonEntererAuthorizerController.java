package com.company.appController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company.common.APPUtills;
import com.company.common.ApplicationConstants;
import com.company.common.ObjectManager;
import com.company.models.UserData;

@Controller
@ComponentScan(basePackages = { "com.company.bl" })
public class CommonEntererAuthorizerController {
	// private static final Log log =LogFactory.getLog(FrontDeskUserMngController.class);

	@RequestMapping(value = "/CommonEntererAuth/ExistingCommonEntererAuths")
	public ModelAndView ExistingCommonEntererAuths(HttpServletRequest req, HttpServletResponse resp,HttpSession session) {
		ModelAndView mv = new ModelAndView("CommonEntererAuth/existingCommonEntererAuth");
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
			
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX");
			// log.debug("Enter | getAvilbleVehicleData");
			mv.addObject("vehicles", "");

		} catch (Exception e) {
			// log.debug("Exception : " + e);
		}
		return mv;
	}
	
	@RequestMapping(value = "/CommonEntererAuth/PendingCommonEntererAuths")
	public ModelAndView pendingCommonEntererAuths(HttpServletRequest req, HttpServletResponse resp,HttpSession session) {
		ModelAndView mv = new ModelAndView("CommonEntererAuth/existingCommonEntererAuth");
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
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX");
			// log.debug("Enter | getAvilbleVehicleData");
			mv.addObject("vehicles", "");

		} catch (Exception e) {
			// log.debug("Exception : " + e);
		}
		return mv;
	}
	
	public String checkSessionTimeOut(UserData userData,ObjectManager objManager){
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
	}
}
