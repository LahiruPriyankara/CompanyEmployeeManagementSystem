/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.company.common.APPUtills;
import com.company.common.SBLException;
import com.company.dbconfig.DbConfig;
import com.company.dto.UserReference;

/**
 *
 * @author sits_lahirupr
 */
@Repository
public class UserReferenceFacade implements UserReferenceFacadeLocal {
	Session session;
	Transaction tx;
    private static Logger log = LogManager.getLogger(UserReferenceFacade.class);


    @Override
    public UserReference getReferenceByUserId(int uId) throws Exception {
        System.out.println("ENTERED | UserReferenceFacade.getReferenceByUserId()");
        UserReference dur = null;
        Query query = null;
        try {
            System.out.println("MESSAGE | UserId : " + uId);
            session = DbConfig.sessionBulder();  
            tx = session.beginTransaction();
            query = session.createQuery("FROM UserReference ur WHERE ur.userId = :userId");
            query.setParameter("userId", uId);  
            
            System.out.println("HQL     | " + query.toString());

            dur = (UserReference) query.uniqueResult();
            tx.commit();
        } catch (Exception ex) {
            log.error("ERROR   | No match data in reference table.");
            //throw new SBLException("Unable to fetch data.");
        }finally{
        	if(session!=null)session.close();
        }
        System.out.println("LEFT    | UserReferenceFacade.getReferenceByUserId()");
        return dur;
    }
    
    @Override
    public void addReference(UserReference dur) throws Exception {
        System.out.println("ENTERED | UserReferenceFacade.addReference()");
        try {
        	if(dur == null){
        		throw new SBLException("Please sSent a vlid event log object to save.");
        		}
        	System.out.println("LEFT    | dur.toString() : "+dur.toString());
            session = DbConfig.sessionBulder();  
            tx = session.beginTransaction();
            if(dur.getReferenceId()==0){
            	session.save(dur);
            }else{
            	session.update(dur);
            }
            tx.commit();
        } catch (Exception ex) {
            log.error("ERROR   | No match data in reference table.");
            //throw new SBLException("Unable to fetch data.");
        }finally{
        	if(session!=null)session.close();
        }
        System.out.println("LEFT    | UserReferenceFacade.addReference()");
    }
}
