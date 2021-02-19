package com.company.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.company.common.ApplicationConstants;
import com.company.common.SBLException;
import com.company.dbconfig.DbConfig;
import com.company.dto.CommonUserMaster;
import com.company.models.CommonUserModel;

@Repository
public class CommonUserMasterFacade implements CommonUserMasterFacadeLocal{
	Session session;
	Transaction tx;
	private static Logger log = LogManager.getLogger(FDUserTmpFacade.class);

	@Autowired
	SeqNumberGeneratorFacadeLocal seqNumberGeneratorFacade;
	
	@Override
	public Map<Integer, CommonUserModel> getAllUsers() throws Exception {
		System.out.println("ENTERED | CommonUserMasterFacade.getAllUsers()");
		Map<Integer, CommonUserModel> dfumsMap = new HashMap();
		List<CommonUserMaster> dfumsList = new ArrayList<>();
		List<String> recStatusList;
		Query query = null;
		try {
			session = DbConfig.sessionBulder();
			tx = session.beginTransaction();
			
			query = session.createQuery("FROM CommonUserMaster tmp");

			System.out.println("HQL     | " + query.toString());
			dfumsList = query.list();
			tx.commit();
			System.out.println("MESSAGE | dfumsMaster.size() : " + dfumsList.size());

			// Converting to PayModeHelper list
			dfumsMap = objectToModel(dfumsList);
		} catch (Exception ex) {
			log.error("ERROR   | Unable to fetch list of object from temp table." + ex.getMessage(), ex);
			throw new SBLException("Unable to fetch data.");
		} finally {
			if (session != null)
				session.close();
		}
		System.out.println("LEFT    | CommonUserMasterFacade.getAllUsers()");
		return dfumsMap;
	}
	/*@Override
	public Map<Integer, CommonUserModel> getAllUserByUserId(String cmnUserId) throws Exception{
		System.out.println("ENTERED | CommonUserMasterFacade.getAllUserByMasterId()");
		Map<Integer, CommonUserModel> dfumsMap = new HashMap();
		List<CommonUserMaster> dfumsList = new ArrayList<>();
		List<String> recStatusList;
		Query query = null;
		try {
			session = DbConfig.sessionBulder();
			tx = session.beginTransaction();
			
			query = session.createQuery("FROM CommonUserMaster tmp WHERE tmp.cmnUserId="+cmnUserId);

			System.out.println("HQL     | " + query.toString());
			dfumsList = query.list();
			tx.commit();
			System.out.println("MESSAGE | dfumsTemp.size() : " + dfumsList.size());

			// Converting to PayModeHelper list
			dfumsMap = objectToModel(dfumsList);
		} catch (Exception ex) {
			log.error("ERROR   | Unable to fetch list of object from temp table." + ex.getMessage(), ex);
			throw new SBLException("Unable to fetch data.");
		} finally {
			if (session != null)
				session.close();
		}
		System.out.println("LEFT    | CommonUserMasterFacade.getAllUserByMasterId()");
		return dfumsMap;
	}*/

	public Map<Integer, CommonUserModel> objectToModel(List<CommonUserMaster> dfumsMasterList) throws Exception {
		System.out.println("ENTERED    | CommonUserMasterFacade.objectToModel()");
		Map<Integer, CommonUserModel> dfumsMap = new HashMap();
		CommonUserMaster dfum = null;
		try {
			dfumsMasterList = dfumsMasterList != null ? dfumsMasterList : new ArrayList<>();
			for (CommonUserMaster dfumsMaster : dfumsMasterList) {
				dfumsMap.put(dfumsMaster.getCmnUserMasterId(),CommonUserModel.objectToModel(dfumsMaster, ApplicationConstants.MASTER_DATA));
			}
		} catch (Exception ex) {
			log.error("ERROR   | Unable to convert temp to model." + ex.getMessage(), ex);
			throw new SBLException("Object Conversion error.Please try again.");
		}
		System.out.println("LEFT    | CommonUserMasterFacade.objectToModel()");
		return dfumsMap;
	}
	
	
	@Override
	public boolean modifyUser(CommonUserModel model, int actionType) {

		System.out.println("Enter | modifyUser TEMP TBL");
		try {
			System.out.println("actionType : "+actionType);
			CommonUserMaster temp =  (CommonUserMaster) model.modelToObject(ApplicationConstants.MASTER_DATA);
			session = DbConfig.sessionBulder();
			tx = session.beginTransaction();
			System.out.println("master.toString() : "+temp.toString());
			if (actionType == 1) {
				session.save(temp);
			} else if (actionType == 2) {
				session.update(temp);
			} else if (actionType == 3) {
				session.delete(temp);
			} else {
				throw new Exception("Invalid Action Type");
			}

			tx.commit();
			System.out.println("persist successful");
			return true;
		} catch (Exception e) {
			log.error("persist failed", e);
			return false;
		} finally {
			log.debug("Left | modifyUser");
			if (session != null)
				session.close();
		}
	}

}
