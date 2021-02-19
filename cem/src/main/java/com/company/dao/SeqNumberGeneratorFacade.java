package com.company.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.company.common.ApplicationConstants;
import com.company.dbconfig.DbConfig;
import com.company.dto.CompanyUserMaster;
import com.company.dto.CompanyUserTmp;
import com.company.dto.FdUserMaster;
import com.company.dto.FdUserTmp;

@Repository
public class SeqNumberGeneratorFacade implements SeqNumberGeneratorFacadeLocal {

	Session session;
	Transaction tx;
	// private static Logger log =
	// LogManager.getLogger(UserReferenceFacade.class);

	@Override
	public int getSequenceNumber(int sequenceIdType) throws Exception {
		System.out.println("ENTERED | SeqNumberGeneratorFacade.getSequenceNumber()");
		int id = 0;
		Query query = null;
		try {
			session = DbConfig.sessionBulder();
			tx = session.beginTransaction();

			if (sequenceIdType == ApplicationConstants.COMPANY_USER_MASTER_ID) {
				query = session.createQuery("SELECT MAX(t.companyUserMasterId) FROM CompanyUserMaster t");				
			} else if (sequenceIdType == ApplicationConstants.COMPANY_USER_TMP_ID) {
				query = session.createQuery("SELECT MAX(t.companyUserTmpId) FROM CompanyUserTmp t");
			} else if (sequenceIdType == ApplicationConstants.FD_USER_MASTER_ID) {
				query = session.createQuery("SELECT MAX(t.fdUserMasterId) FROM FdUserMaster t");
			} else if (sequenceIdType == ApplicationConstants.FD_USER_TMP_ID) {
				query = session.createQuery("SELECT MAX(t.fdUserTmpId) FROM FdUserTmp t");
			}else if (sequenceIdType == ApplicationConstants.COMMON_USER_MASTER_ID) {
				query = session.createQuery("SELECT MAX(t.cmnUserMasterId) FROM CommonUserMaster t");
			} else if (sequenceIdType == ApplicationConstants.COMMON_USER_TMP_ID) {
				query = session.createQuery("SELECT MAX(t.cmnUserTmpId) FROM CommonUserTmp t");
			} else {
				System.out.println("ERROR   | Invalid requested id type : " + sequenceIdType);
				throw new Exception("Invalid requested id type.");
			}
			
			id = (Integer)query.uniqueResult();
			tx.commit();
			
			id++;
			System.out.println("ID : " + id);
			
		} catch (Exception ex) {
			System.out.println("ERROR   | Unable to fetch a id."+ex.getMessage());
			//throw new Exception("Unable to fetch a id.");
			id = 1;
		} finally {
			if (session != null)
				session.close();
		}
		System.out.println("LEFT    | SeqNumberGeneratorFacade.getSequenceNumber()");
		return id;
	}

}
