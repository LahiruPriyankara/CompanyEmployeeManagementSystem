/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author sits_lahirupr
 */
public class Initializer extends HttpServlet {

    private static Logger dedLog = LogManager.getLogger(Initializer.class);

    public void init() throws ServletException {
        super.init();
        APPUtills.setLog4jUserParams();
        initCommonParams();
    }

    protected void initCommonParams() {

        dedLog.info("MESSAGE | Loading property files...");

        PropertyResourceBundle commonProps = null;
        String propertyFile = "org.sampath.app.ded.resource/DEDCommonParam";
        //String LOG_PATH = "";

        try {

            //FIRST SET COMMON PROPERTIES
            commonProps = (PropertyResourceBundle) PropertyResourceBundle.getBundle(propertyFile);

            ApplicationConstants.APP_ENV = commonProps.getString("APP_ENV").trim();
            ApplicationConstants.APP_CODE = commonProps.getString("APP_CODE").trim();
            ApplicationConstants.UPM_APP_CODE = commonProps.getString("UPM_APP_CODE").trim();
            ApplicationConstants.USER_GRADES = Arrays.asList(commonProps.getString("GRADE").trim().split(","));

            //THEN SET PROPERTIES DEPENDING ON ENVIRONMENT			
            if (ApplicationConstants.APP_ENV.equalsIgnoreCase("LIVE")) {
                propertyFile = "org.sampath.app.ded.resource/DEDLiveParam";
            } else if (ApplicationConstants.APP_ENV.equalsIgnoreCase("STAGING")) {
                propertyFile = "org.sampath.app.ded.resource/DEDStagingParam";
            } else if (ApplicationConstants.APP_ENV.equalsIgnoreCase("TEST")) {
                propertyFile = "org.sampath.app.ded.resource/DEDTestParam";
            }

            PropertyResourceBundle props = (PropertyResourceBundle) PropertyResourceBundle.getBundle(propertyFile);

            ApplicationConstants.UPM_WS_URL = props.getString("UPM_WS_URL").trim();
            ApplicationConstants.UPM_REST_WS_URL = props.getString("UPM_REST_WS_URL").trim();
            ApplicationConstants.SECUREPASS_WS_URL = props.getString("SECUREPASS_WS_URL").trim();
            ApplicationConstants.INITIAL_CONTEXT_FACTORY = props.getString("INITIAL_CONTEXT_FACTORY").trim();
            ApplicationConstants.SECURITY_AUTHENTICATION = props.getString("SECURITY_AUTHENTICATION").trim();
            ApplicationConstants.PROVIDER_URL = props.getString("PROVIDER_URL").trim();
            ApplicationConstants.USERNAME_TAIL = props.getString("USERNAME_TAIL").trim();

            //ApplicationConstants.USE_PROXY = Boolean.valueOf(props.getString("USE_PROXY").trim());
            //ApplicationConstants.PROXY_HOST = props.getString("PROXY_HOST").trim();
            //ApplicationConstants.PROXY_PORT = props.getString("PROXY_PORT").trim();

        } catch (MissingResourceException e) {
            System.err.println("Failed to load Properties file at CBMAdminInitializer Servlet. Be sure " + commonProps
                    + " is located correctly.");
            dedLog.error("ERROR   | Failed to load Properties file at CBMAdminInitializer Servlet. Be sure " + commonProps
                    + " is located correctly. " + e.getMessage());
        } catch (NumberFormatException ne) {
            ne.printStackTrace();
            dedLog.error("ERROR   | NumberFormatException. Please check values in property files. " + ne.getMessage());
        } catch (Exception ex) {
            dedLog.error("ERROR   | " + ex.getMessage());
        }
    }

}
