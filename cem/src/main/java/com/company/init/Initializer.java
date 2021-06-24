
package com.company.init;

import java.util.Arrays;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.company.common.APPUtills;
import com.company.common.ApplicationConstants;

/* 
Author     : lahiru priyankara
*/
public class Initializer{

    private static Logger dedLog = LogManager.getLogger(Initializer.class);
/*
    public void init() throws ServletException {
        super.init();
        APPUtills.setLog4jUserParams();
        initCommonParams();
    }
*/
    public static void initCommonParams() {

        dedLog.info("MESSAGE | Loading property files...");

        PropertyResourceBundle commonProps = null;
        String propertyFile = "com/company/resource/Param";
        //String LOG_PATH = "";

        try {

            //FIRST SET COMMON PROPERTIES
            commonProps = (PropertyResourceBundle) PropertyResourceBundle.getBundle(propertyFile);

            ApplicationConstants.APP_ENV = commonProps.getString("APP_ENV").trim();
            ApplicationConstants.APP_CODE = commonProps.getString("APP_CODE").trim();
            ApplicationConstants.UPM_APP_CODE = commonProps.getString("UPM_APP_CODE").trim();
            ApplicationConstants.USER_GRADES = Arrays.asList(commonProps.getString("GRADE").trim().split(","));        
            
            String COM_SERVICE_URL = commonProps.getString("COM_SERVICE_URL").trim();
            ApplicationConstants.GET_ALL_DEPARTMRNTS = COM_SERVICE_URL+commonProps.getString("GET_ALL_DEPARTMRNTS").trim();
            ApplicationConstants.GET_ALL_NON_COMPANY_USERS = COM_SERVICE_URL+commonProps.getString("GET_ALL_NON_COMPANY_USERS").trim();
            ApplicationConstants.CREATE_NON_COMPANY_USER = COM_SERVICE_URL+commonProps.getString("CREATE_NON_COMPANY_USER").trim();
            ApplicationConstants.VERIFY_NON__COMPANY_USER = COM_SERVICE_URL+commonProps.getString("VERIFY_NON__COMPANY_USER").trim();
            ApplicationConstants.UNLOCK_PASSWD_NON_COMPANY_USER = COM_SERVICE_URL+commonProps.getString("UNLOCK_PASSWD_NON_COMPANY_USER").trim();
            ApplicationConstants.SET_PASSWD_NON_COMPANY_USER = COM_SERVICE_URL+commonProps.getString("SET_PASSWD_NON_COMPANY_USER").trim();
            ApplicationConstants.ACTIVE_PASSWD_NON_COMPANY_USER = COM_SERVICE_URL+commonProps.getString("ACTIVE_PASSWD_NON_COMPANY_USER").trim();
            ApplicationConstants.RESET_PASSWD_NON_COMPANY_USER = COM_SERVICE_URL+commonProps.getString("RESET_PASSWD_NON_COMPANY_USER").trim();
            ApplicationConstants.LOGGIN_NON_COMPANY_USER = COM_SERVICE_URL+commonProps.getString("LOGGIN_NON_COMPANY_USER").trim();
            ApplicationConstants.GET_ALL_COMPANY_USERS = COM_SERVICE_URL+commonProps.getString("GET_ALL_COMPANY_USERS").trim();
            ApplicationConstants.GET_ALL_COMPANY_USER_BY_DEP_ID = COM_SERVICE_URL+commonProps.getString("GET_ALL_COMPANY_USER_BY_DEP_ID").trim();
            ApplicationConstants.GET_COMPANY_USER_BY_USER_ID = COM_SERVICE_URL+commonProps.getString("GET_COMPANY_USER_BY_USER_ID").trim();           
            ApplicationConstants.LOGGIN_COMPANY_USER = COM_SERVICE_URL+commonProps.getString("LOGGIN_COMPANY_USER").trim();
            ApplicationConstants.COM_ADMIN_ENTERER_ASSIGN = COM_SERVICE_URL+commonProps.getString("COM_ADMIN_ENTERER_ASSIGN").trim();
            
            /*

            ApplicationConstants.UPM_WS_URL = props.getString("UPM_WS_URL").trim();
            ApplicationConstants.UPM_REST_WS_URL = props.getString("UPM_REST_WS_URL").trim();
            ApplicationConstants.SECUREPASS_WS_URL = props.getString("SECUREPASS_WS_URL").trim();
            ApplicationConstants.INITIAL_CONTEXT_FACTORY = props.getString("INITIAL_CONTEXT_FACTORY").trim();
            ApplicationConstants.SECURITY_AUTHENTICATION = props.getString("SECURITY_AUTHENTICATION").trim();
            ApplicationConstants.PROVIDER_URL = props.getString("PROVIDER_URL").trim();
            ApplicationConstants.USERNAME_TAIL = props.getString("USERNAME_TAIL").trim();
*/
            //ApplicationConstants.USE_PROXY = Boolean.valueOf(props.getString("USE_PROXY").trim());
            //ApplicationConstants.PROXY_HOST = props.getString("PROXY_HOST").trim();
            //ApplicationConstants.PROXY_PORT = props.getString("PROXY_PORT").trim();

        } catch (MissingResourceException e) {
            System.err.println("Failed to load Properties file. Be sure " + commonProps
                    + " is located correctly.");
            dedLog.error("ERROR   | Failed to load Properties file. Be sure " + commonProps
                    + " is located correctly. " + e.getMessage());
        } catch (NumberFormatException ne) {
            ne.printStackTrace();
            dedLog.error("ERROR   | NumberFormatException. Please check values in property files. " + ne.getMessage());
        } catch (Exception ex) {
            dedLog.error("ERROR   | " + ex.getMessage());
        }
    }

}
