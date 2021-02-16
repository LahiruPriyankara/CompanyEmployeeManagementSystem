package com.company.bl;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.company.dao.EventLogFacadeLocal;
import com.company.dao.SeqNumberGeneratorBeanLocal;
import com.company.dto.EventLogTbl;

@Service
@ComponentScan(basePackages = { "com.company.dao" })
public class EventLogger implements EventLoggerLocal{
	//private static Logger dedLog = LogManager.getLogger(EventLogger.class);

    @Autowired
    private EventLogFacadeLocal eventLogFacade;

    //@Autowired
    //private SeqNumberGeneratorBeanLocal seqNumberGeneratorBean;

    @Override
    public void doLog(HttpServletRequest req, String pUserId, String pEvtType, String pEvtAction, String pEvtDesc, String pOldValue, String pNewValue, String pEvtStatus) {
        System.out.println("ENTERED | EventLogger.doLog()");
          try {
                HttpSession session 	= req.getSession(false);
                EventLogTbl logData 	= new EventLogTbl();
                
                // Get current date
                Calendar currentDate = Calendar.getInstance();
                //logData.setEventId(seqNumberGeneratorBean.getNextAdminEventLogId()); 
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
                
                eventLogFacade.doLog(logData);
        }
        catch (Exception ex) {
            System.out.println("ERROR   | " + ex.getMessage() + "\n" );
        }
        System.out.println("LEFT    | EventLogger.doLog()");
    }
}
