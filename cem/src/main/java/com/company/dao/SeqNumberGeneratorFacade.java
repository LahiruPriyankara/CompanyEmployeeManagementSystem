package com.company.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.company.common.ApplicationConstants;
import com.company.dbconfig.DbConfig;
import com.company.dto.SequenceNumber;

@Repository
public class SeqNumberGeneratorFacade implements SeqNumberGeneratorFacadeLocal {

	Session session;
	Transaction tx;
    //private static Logger log = LogManager.getLogger(UserReferenceFacade.class);


    @Override
    public int getSequenceNumber(int sequenceIdType) throws Exception {
        System.out.println("ENTERED | SeqNumberGeneratorFacade.getSequenceNumber()");
        int id = 0;
        try {
            session = DbConfig.sessionBulder();  
            tx = session.beginTransaction();
            SequenceNumber sequenceNumber = (SequenceNumber)session.get(SequenceNumber.class, 1);
            tx.commit();
            
            if(sequenceIdType == ApplicationConstants.COMPANY_USER_MASTER_ID){
            	id = sequenceNumber.getCompanyUserMasterId();
            	id = id+1;
            	sequenceNumber.setCompanyUserMasterId(id);
            	
            }else if(sequenceIdType == ApplicationConstants.COMPANY_USER_TMP_ID){
            	id = sequenceNumber.getCompanyUserTmpId();
            	id = id+1;
            	sequenceNumber.setCompanyUserTmpId(id);
            	
            }else if(sequenceIdType == ApplicationConstants.FD_USER_MASTER_ID){
            	id = sequenceNumber.getFdUserMasterId();
            	id = id+1;
            	sequenceNumber.setCompanyUserMasterId(id);
            	
            }else if(sequenceIdType == ApplicationConstants.FD_USER_TMP_ID){
            	id = sequenceNumber.getFdUserTmpId();
            	id = id+1;
            	sequenceNumber.setFdUserTmpId(id);
            	
            }else{
            	System.out.println("ERROR   | Invalid requested id type : "+sequenceIdType);
                throw new Exception("Invalid requested id type.");
            }
            
            tx = session.beginTransaction();
            session.update(sequenceNumber);
            tx.commit();
            
            System.out.println("ID "+id);
            
        } catch (Exception ex) {
        	System.out.println("ERROR   | Unable to fetch a id.");
            throw new Exception("Unable to fetch a id.");
        }finally{
        	if(session!=null)session.close();
        }
        System.out.println("LEFT    | SeqNumberGeneratorFacade.getSequenceNumber()");
        return id;
    }

}
