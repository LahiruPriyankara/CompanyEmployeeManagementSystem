/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao;

import com.company.common.SBLException;
import com.company.dbconfig.DbConfig;
import com.company.dto.EventLogTbl;
import com.company.dto.FdUserMaster;
import com.company.models.FdUserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sits_lahirupr
 */
@Repository
public class EventLogFacade implements EventLogFacadeLocal {
	Session session;
	Transaction tx;
	private static final Log log = LogFactory.getLog(FDUserMasterFacade.class);
	
	@Override
    public void doLog(EventLogTbl logData) throws SBLException {
		System.out.println("ENTERED | DedFDUserMasterFacade.getFdUsers()");
        Map<Integer, FdUserModel> dfumsMap = new HashMap();
        List<FdUserMaster> dfumsList = new ArrayList<>();
        try {
            if(logData==null){
            	throw new SBLException("Please sSent a vlid event log object to save.");
            }
            session = DbConfig.sessionBulder();  
            tx = session.beginTransaction();
            session.save(logData);
            tx.commit();
        } catch (Exception ex) {
            log.error("ERROR   | Unable to fetch list of object from temp table." + ex.getMessage(), ex);
            throw new SBLException("Unable to fetch data.");
        }finally{
        	if(session!=null)session.close();
        }
        System.out.println("LEFT    | DedFDUserMasterFacade.getFdUsers()");
    }
}
