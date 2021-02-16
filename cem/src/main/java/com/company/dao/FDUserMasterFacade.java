/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.company.common.ApplicationConstants;
import com.company.common.SBLException;
import com.company.dbconfig.DbConfig;
import com.company.dto.FdUserMaster;
import com.company.dto.FdUserTmp;
import com.company.models.FdUserModel;


@Repository
//@Transactional
public class FDUserMasterFacade implements FDUserMasterFacadeLocal {
	Session session;
	Transaction tx;
	private static final Log log = LogFactory.getLog(FDUserMasterFacade.class);

    @Override
    public Map<Integer, FdUserModel> getAllFdUsersByUserIds(List<Integer> ids) throws Exception {
        System.out.println("ENTERED | DedFDUserMasterFacade.getFdUsers()");
        Map<Integer, FdUserModel> dfumsMap = new HashMap();
        List<FdUserMaster> dfumsList = new ArrayList<>();
        Query query = null;
        try {
            ids = (ids != null) ? ids : new ArrayList<>();
            System.out.println("MESSAGE | Ids.size() : " + ids.size());
            session = DbConfig.sessionBulder();  
            tx = session.beginTransaction();
            
            if (ids.isEmpty()) {
                query = session.createQuery("FROM FdUserMaster tbl ORDER BY tbl.userStatus");
            } else {
                query = session.createQuery("FROM FdUserMaster tbl WHERE tbl.fdUserMasterId IN :fdUserMasterId");
                query.setParameterList("fdUserMasterId", ids);
            }
            System.out.println("HQL     | " + query.toString());
            dfumsList = query.list();
            tx.commit();
            System.out.println("MESSAGE | dfumsList.size() : " + dfumsList.size());

            // Converting to PayModeHelper list
            dfumsMap = objectToModel(dfumsList);
        } catch (Exception ex) {
            log.error("ERROR   | Unable to fetch list of object from temp table." + ex.getMessage(), ex);
            throw new SBLException("Unable to fetch data.");
        }finally{
        	if(session!=null)session.close();
        }
        System.out.println("LEFT    | DedFDUserMasterFacade.getFdUsers()");
        return dfumsMap;
    }

    @Override
    public Map<Integer, FdUserModel> getFdUserByUserName(List<String> getFdUserByUserNames) throws Exception {
        System.out.println("ENTERED | DedFDUserMasterFacade.getFdUsers()");
        Map<Integer, FdUserModel> dfumsMap = new HashMap();
        List<FdUserMaster> dfumsList = new ArrayList<>();
        List<String> userNames;
        Query query = null;
        try {
            userNames = (getFdUserByUserNames != null) ? getFdUserByUserNames : new ArrayList<>();
            System.out.println("MESSAGE | Ids.size() : " + getFdUserByUserNames.size());
            
            session = DbConfig.sessionBulder();  
            tx = session.beginTransaction();

            if (userNames.size() == 0) {
                throw new SBLException("Recived Empty user names.");
            } else {                
                query = session.createQuery("FROM FdUserMaster tbl WHERE tbl.fdUserName IN :fdUserName");
                query.setParameterList("fdUserName", userNames);
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
        }finally{
        	if(session!=null)session.close();
        }
        System.out.println("LEFT    | DedFDUserMasterFacade.getFdUsers()");
        return dfumsMap;
    }

    public Map<Integer, FdUserModel> objectToModel(List<FdUserMaster> dfumsTempList) throws Exception {
        System.out.println("ENTERED | DedFDUserMasterFacade.objectToModel()");
        Map<Integer, FdUserModel> dfumsMap = new HashMap();
        try {
            dfumsTempList = dfumsTempList != null ? dfumsTempList : new ArrayList<>();
            for (FdUserMaster dfumsMaster : dfumsTempList) {
                dfumsMap.put(dfumsMaster.getFdUserMasterId(), FdUserModel.objectToModel(dfumsMaster, ApplicationConstants.MASTER_DATA));
            }
        } catch (Exception ex) {
            log.error("ERROR   | Unable to convert master to model." + ex.getMessage(), ex);
            throw new SBLException("Object Conversion error.Please try again.");
        }
        System.out.println("LEFT    | DedFDUserMasterFacade.objectToModel()");
        return dfumsMap;
    }
    
	@Override
	public boolean modifyUser(FdUserModel model, int actionType) {

		log.debug("Enter | modifyVehicle");
		try {
			FdUserTmp temp =  (FdUserTmp) model.modelToObject(ApplicationConstants.MASTER_DATA);
			session = DbConfig.sessionBulder();
			tx = session.beginTransaction();

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
			log.debug("persist successful");
			return true;
		} catch (Exception e) {
			log.error("persist failed", e);
			return false;
		} finally {
			log.debug("Left | modifyVehicle");
			if (session != null)
				session.close();
		}
	}
}
