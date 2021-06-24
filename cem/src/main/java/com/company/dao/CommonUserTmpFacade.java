/* 
    Author     : lahiru priyankara
*/

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
import com.company.dto.CommonUserTmp;
import com.company.models.CommonUserModel;

@Repository
public class CommonUserTmpFacade implements CommonUserTmpFacadeLocal{
	Session session;
	Transaction tx;
	private static Logger log = LogManager.getLogger(FDUserTmpFacade.class);

	@Autowired
	SeqNumberGeneratorFacadeLocal seqNumberGeneratorFacade;
	
	
	@Override
	public Map<Integer, CommonUserModel> getAllUsers() throws Exception {
		System.out.println("ENTERED | CommonUserTmpFacade.getAllUsers()");
		Map<Integer, CommonUserModel> dfumsMap = new HashMap();
		List<CommonUserTmp> dfumsList = new ArrayList<>();
		List<String> recStatusList;
		Query query = null;
		try {
			session = DbConfig.sessionBulder();
			tx = session.beginTransaction();
			
			recStatusList = Arrays.asList(ApplicationConstants.RECORD_STATUS_PENDING,ApplicationConstants.RECORD_STATUS_REJECT);
			query = session.createQuery("FROM CommonUserTmp tmp WHERE tmp.recStatus IN :recStatus");
			query.setParameterList("recStatus", recStatusList);

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
		System.out.println("LEFT    | CommonUserTmpFacade.getAllUsers()");
		return dfumsMap;
	}
	
	@Override
	public Map<Integer, CommonUserModel> getAllUserByUserId(String cmnUserId) throws Exception{
		System.out.println("ENTERED | CommonUserTmpFacade.getAllUserByMasterId()");
		Map<Integer, CommonUserModel> dfumsMap = new HashMap();
		List<CommonUserTmp> dfumsList = new ArrayList<>();
		List<String> recStatusList;
		Query query = null;
		try {
			session = DbConfig.sessionBulder();
			tx = session.beginTransaction();
			
			query = session.createQuery("FROM CommonUserTmp tmp WHERE tmp.cmnUserId= :cmnUserId AND tmp.recStatus IN :recStatus");
			recStatusList = Arrays.asList(ApplicationConstants.RECORD_STATUS_PENDING,ApplicationConstants.RECORD_STATUS_REJECT);
			query.setParameter("cmnUserId", cmnUserId);
			query.setParameterList("recStatus", recStatusList);
			
			
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
		System.out.println("LEFT    | CommonUserTmpFacade.getAllUserByMasterId()");
		return dfumsMap;
	}

	public Map<Integer, CommonUserModel> objectToModel(List<CommonUserTmp> dfumsTempList) throws Exception {
		System.out.println("ENTERED    | CommonUserTmpFacade.objectToModel()");
		Map<Integer, CommonUserModel> dfumsMap = new HashMap();
		CommonUserTmp dfum = null;
		try {
			dfumsTempList = dfumsTempList != null ? dfumsTempList : new ArrayList<>();
			for (CommonUserTmp dfumsTemp : dfumsTempList) {
				dfumsMap.put(dfumsTemp.getCmnUserTmpId(),CommonUserModel.objectToModel(dfumsTemp, ApplicationConstants.TEMP_DATA));
			}
		} catch (Exception ex) {
			log.error("ERROR   | Unable to convert temp to model." + ex.getMessage(), ex);
			throw new SBLException("Object Conversion error.Please try again.");
		}
		System.out.println("LEFT    | CommonUserTmpFacade.objectToModel()");
		return dfumsMap;
	}
	
	
	@Override
	public boolean modifyUser(CommonUserModel model, int actionType) {

		System.out.println("Enter | modifyUser TEMP TBL");
		try {
			System.out.println("actionType : "+actionType);
			CommonUserTmp temp =  (CommonUserTmp) model.modelToObject(ApplicationConstants.TEMP_DATA);
			session = DbConfig.sessionBulder();
			tx = session.beginTransaction();
			System.out.println("master.toString() : "+temp.toString());
			if (actionType == 1) {
				temp.setCmnUserTmpId(seqNumberGeneratorFacade.getSequenceNumber(ApplicationConstants.COMMON_USER_TMP_ID));
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
