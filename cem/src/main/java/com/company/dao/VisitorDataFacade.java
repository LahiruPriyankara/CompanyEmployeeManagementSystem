

package com.company.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.company.dbconfig.DbConfig;
import com.company.dto.VisitorData;
/* 
Author     : lahiru priyankara
*/
@Repository
public class VisitorDataFacade implements VisitorDataFacadeLocal{
	Session session;
	Transaction tx;
    private static Logger log = LogManager.getLogger(VisitorDataFacade.class);

    
    @Override
    public boolean insertRecord(VisitorData data) throws Exception {
        System.out.println("ENTERED | VisitorDataFacade.insertRecord()");
        boolean isInsert = false;
        try {
            session = DbConfig.sessionBulder();  
            tx = session.beginTransaction();
            session.save(data); 
            tx.commit();
            isInsert = true;
        } catch (Exception ex) {
            log.error("ERROR   | No match data in reference table.");
            //throw new SBLException("Unable to fetch data.");
        }finally{
        	if(session!=null)session.close();
        }
        System.out.println("LEFT    | VisitorDataFacade.insertRecord()");
        return isInsert;
    }
    
    public List<VisitorData> getAllRecord() throws Exception{
        System.out.println("ENTERED | VisitorDataFacade.getAllRecord()");
        Query query = null;
        boolean isInsert = false;
        List<VisitorData> list = new ArrayList<>();
        try {
            session = DbConfig.sessionBulder();  
            tx = session.beginTransaction();
            query = session.createQuery("FROM VisitorData tmp");
            list = query.list();
            tx.commit();
            isInsert = true;
        } catch (Exception ex) {
            log.error("ERROR   | No match data in table.");
            //throw new SBLException("Unable to fetch data.");
        }finally{
        	if(session!=null)session.close();
        }
        System.out.println("LEFT    | VisitorDataFacade.getAllRecord()");
        return list;
    }
    
}
