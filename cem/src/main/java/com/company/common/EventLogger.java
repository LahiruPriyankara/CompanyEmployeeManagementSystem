/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.common;

import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.company.dao.EventLogFacade;
import com.company.dao.EventLogFacadeLocal;
import com.company.dao.SeqNumberGeneratorBean;
import com.company.dao.SeqNumberGeneratorBeanLocal;
import com.company.dto.EventLogTbl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sits_lahirupr
 */
//@Repository("eventLogger")
public class EventLogger implements EventLoggerLocal {
/*
    private static Logger dedLog = LogManager.getLogger(EventLogger.class);

    //@Autowired
    private EventLogFacadeLocal eventLogFacade = new EventLogFacade();

    //@Autowired
    private SeqNumberGeneratorBeanLocal seqNumberGeneratorBean = new SeqNumberGeneratorBean();

    @Override
    public void doLog(HttpServletRequest req, String pUserId, String pEvtType, String pEvtAction, String pEvtDesc, String pOldValue, String pNewValue, String pEvtStatus) {
        dedLog.info("ENTERED | EventLogger.doLog()");
          try {
                HttpSession session 	= req.getSession(false);
                EventLogTbl logData 	= new EventLogTbl();
                
                // Get current date
                Calendar currentDate = Calendar.getInstance();
                logData.setEventId(seqNumberGeneratorBean.getNextAdminEventLogId()); 
                logData.setEventDate(currentDate.getTime()); 
                logData.setEventType(pEvtType);
                logData.setEventAction(pEvtAction);
                logData.setEventDesc(pEvtDesc);
                logData.setEventStatus(pEvtStatus);
                logData.setSessionId(session.getId());
                logData.setIpAddress(req.getRemoteAddr());
                logData.setUserId(pUserId);
                logData.setOldValue(pOldValue); 
                logData.setNewValue(pNewValue); 
                logData.setClientInfo(req.getHeader("User-Agent"));  
                
                eventLogFacade.create(logData);
        }
        catch (Exception ex) {
            dedLog.error("ERROR   | " + ex.getMessage() + "\n" );
        }
        dedLog.info("LEFT    | EventLogger.doLog()");
    }
    */
}
