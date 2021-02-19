/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

import com.company.common.ApplicationConstants;
import com.company.dto.FdUserTmp;
import com.company.models.FdUserModel;
import com.company.common.SBLException;
import com.company.dbconfig.DbConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sits_lahirupr
 */
@Repository
public class FDUserTmpFacade implements FDUserTmpFacadeLocal {
	Session session;
	Transaction tx;
	private static Logger log = LogManager.getLogger(FDUserTmpFacade.class);

	@Autowired
	SeqNumberGeneratorFacadeLocal seqNumberGeneratorFacade;
	
	
	@Override
	public Map<Integer, FdUserModel> getTempFdUsers(List<Integer> ids, String tableType) throws Exception {
		System.out.println("ENTERED | DedFDUserTmpFacade.getTempFdUsers()");
		Map<Integer, FdUserModel> dfumsMap = new HashMap();
		List<FdUserTmp> dfumsList = new ArrayList<>();
		List<String> recStatusList;
		Query query = null;
		try {
			ids = (ids != null) ? ids : new ArrayList<>();
			System.out.println("MESSAGE | Ids.size() : " + ids.size());
			session = DbConfig.sessionBulder();
			tx = session.beginTransaction();

			if (tableType.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)) {
				if (ids.isEmpty()) {
					recStatusList = Arrays.asList(ApplicationConstants.RECORD_STATUS_PENDING,
							ApplicationConstants.RECORD_STATUS_REJECT);
					query = session.createQuery(
							"FROM FdUserTmp tmp WHERE tmp.recStatus IN :recStatus OR tmp.securepassUserStatus = :securepassUserStatus ORDER BY tmp.recStatus DESC");
					query.setParameterList("recStatus", recStatusList);
					query.setParameter("securepassUserStatus", ApplicationConstants.SECUREPASS_USER_SET_PASSWORD);

				} else {
					query = session.createQuery("FROM FdUserTmp tmp WHERE tmp.fdUserTmpId IN :fdUserTmpId");
					query.setParameterList("fdUserTmpId", ids);
				}

			} else if (tableType.equalsIgnoreCase(ApplicationConstants.MASTER_DATA)) {
				if (ids.isEmpty()) {
					throw new SBLException("Recived Empty userId.");
				} else {
					recStatusList = Arrays.asList(ApplicationConstants.RECORD_STATUS_PENDING,ApplicationConstants.RECORD_STATUS_REJECT);
					query = session.createQuery("FROM FdUserTmp tmp WHERE tmp.fdUserMasterId IN :fdUserMasterIds AND tmp.recStatus IN :recStatus");
					query.setParameterList("fdUserMasterIds", ids);
					query.setParameterList("recStatus", recStatusList);
				}
			}

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
		System.out.println("LEFT    | DedFDUserTmpFacade.getTempFdUsers()");
		return dfumsMap;
	}

	public Map<Integer, FdUserModel> objectToModel(List<FdUserTmp> dfumsTempList) throws Exception {
		System.out.println("ENTERED    | DedFDUserTmpFacade.objectToModel()");
		FdUserModel dfum = null;
		Map<Integer, FdUserModel> dfumsMap = new HashMap();
		try {
			dfumsTempList = dfumsTempList != null ? dfumsTempList : new ArrayList<>();
			for (FdUserTmp dfumsTemp : dfumsTempList) {
				dfumsMap.put(dfumsTemp.getFdUserTmpId(),
						FdUserModel.objectToModel(dfumsTemp, ApplicationConstants.TEMP_DATA));
			}
		} catch (Exception ex) {
			log.error("ERROR   | Unable to convert temp to model." + ex.getMessage(), ex);
			throw new SBLException("Object Conversion error.Please try again.");
		}
		System.out.println("LEFT    | DedFDUserTmpFacade.objectToModel()");
		return dfumsMap;
	}

	@Override
	public boolean modifyUser(FdUserModel model, int actionType) {

		System.out.println("Enter | modifyUser TEMP TBL");
		try {
			System.out.println("actionType : "+actionType);
			FdUserTmp temp =  (FdUserTmp) model.modelToObject(ApplicationConstants.TEMP_DATA);
			session = DbConfig.sessionBulder();
			tx = session.beginTransaction();
			System.out.println("master.toString() : "+temp.toString());
			if (actionType == 1) {
				temp.setFdUserTmpId(seqNumberGeneratorFacade.getSequenceNumber(ApplicationConstants.FD_USER_TMP_ID));
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
