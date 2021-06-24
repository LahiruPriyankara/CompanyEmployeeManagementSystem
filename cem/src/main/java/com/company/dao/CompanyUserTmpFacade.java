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

import com.company.common.APPUtills;
import com.company.common.ApplicationConstants;
import com.company.dto.CompanyUserTmp;
import com.company.models.CompanyUserModel;
import com.company.common.SBLException;
import com.company.dbconfig.DbConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.company.models.UserData;

/**
 *
 * @author sits_lahirupr
 */
@Repository
public class CompanyUserTmpFacade implements CompanyUserTmpFacadeLocal {
	Session session;
	Transaction tx;
    private static Logger Log = LogManager.getLogger(CompanyUserTmpFacade.class);

    @Autowired
	SeqNumberGeneratorFacadeLocal seqNumberGeneratorFacade;


    @Override
    public Map<String, CompanyUserModel> getTempCompanyUsers(List<String> ids, String depCode) throws Exception {
        System.out.println("ENTERED | CompanyUserTmpFacade.getTempCompanyUsers()");
        Map<String, CompanyUserModel> dfumsMap = new HashMap();
        List<CompanyUserTmp> dfumsList = new ArrayList<>();
        List<String> recStatusList;
        Query query = null;
        try {
            ids = (ids != null) ? ids : new ArrayList<>();
            System.out.println("MESSAGE | Ids.size() : " + ids.size() + " ,depCode : " + depCode);
            session = DbConfig.sessionBulder();            
            tx = session.beginTransaction();
            
            if (ids.isEmpty() && !APPUtills.isThisStringValid(depCode)) {//empty - empty
                //dfumsList = findAll();
                System.out.println("MESSAGE | Going to fetch all pending and rejected data.");
                query = session.createQuery("FROM CompanyUserTmp tmp WHERE tmp.recStatus IN :recStatus ORDER BY tmp.recStatus DESC");
                
            } else if (ids.isEmpty() && APPUtills.isThisStringValid(depCode)) {//empty - not empty
                System.out.println("MESSAGE | Going to fetch all pending and rejected data for sol id "+depCode);
                query = session.createQuery("FROM CompanyUserTmp tmp WHERE tmp.companyUserDivId =:companyUserDivId AND tmp.recStatus IN :recStatus ORDER BY tmp.recStatus DESC");
                query.setParameter("companyUserDivId", depCode);
                
            } else if (!ids.isEmpty() && !APPUtills.isThisStringValid(depCode)) {//not empty -  empty
                System.out.println("MESSAGE | Going to fetch all pending and rejected data for emp ids "+Arrays.asList(ids));                
                query = session.createQuery("FROM CompanyUserTmp tmp WHERE tmp.companyUserEmpId IN :companyUserEmpId AND tmp.recStatus IN :recStatus ORDER BY tmp.recStatus DESC");
                query.setParameterList("companyUserEmpId", ids);
                
            } else {//not empty - not empty
                System.out.println("MESSAGE | Going to fetch data for emp ids "+Arrays.asList(ids)+" and sol id "+depCode);                
                query = session.createQuery("FROM CompanyUserTmp tmp WHERE tmp.companyUserEmpId IN :companyUserEmpId AND tmp.recStatus IN :recStatus ORDER BY tmp.recStatus DESC");
                query.setParameterList("companyUserEmpId", ids);

            }

            recStatusList = Arrays.asList(ApplicationConstants.RECORD_STATUS_PENDING, ApplicationConstants.RECORD_STATUS_REJECT);
            query.setParameterList("recStatus", recStatusList);
            System.out.println("JQL     | " + query.toString());
            dfumsList = query.list();
            tx.commit();
            System.out.println("MESSAGE | dfumsTemp.size() : " + dfumsList.size());

            // Converting to PayModeHelper list
            dfumsMap = objectToModel(dfumsList);
        } catch (Exception ex) {
            Log.error("ERROR   | Unable to fetch list of object from temp table." + ex.getMessage(), ex);
            throw new SBLException("Unable to fetch data.");
        }finally {
        	if(session!=null)session.close();
		}
        System.out.println("LEFT    | CompanyUserTmpFacade.getTempCompanyUsers()");
        return dfumsMap;
    }
  //-----------------------------------------------------------------------------------------------------------------------------
    public Map<String, CompanyUserModel> objectToModel(List<CompanyUserTmp> dfumsTempList) throws Exception {
        System.out.println("ENTERED | CompanyUserTmpFacade.objectToModel()");
        Map<String, CompanyUserModel> dfumsMap = new HashMap();
        try {
            dfumsTempList = dfumsTempList != null ? dfumsTempList : new ArrayList<>();
            for (CompanyUserTmp dfumsTemp : dfumsTempList) {
                dfumsMap.put(dfumsTemp.getCompanyUserEmpId(), CompanyUserModel.objectToModel(dfumsTemp, ApplicationConstants.TEMP_DATA));
            }
        } catch (Exception ex) {
            Log.error("ERROR   | Unable to convert temp to model." + ex.getMessage(), ex);
            throw new SBLException("Object Conversion error.Please try again.");
        }
        System.out.println("LEFT    | CompanyUserTmpFacade.objectToModel()");
        return dfumsMap;
    }
//-----------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean saveEmp(List<CompanyUserModel> CompanyUserModelList, UserData userData) throws Exception {
        System.out.println("ENTERED | CompanyUserTmpFacade.saveEmp()");
        boolean isSuccess = false;
        int id = 0;
        try {
        	session = DbConfig.sessionBulder();  
            //tx = session.beginTransaction();
            
            for (CompanyUserModel CompanyUserModel : CompanyUserModelList) {
            	tx = session.beginTransaction();
                CompanyUserModel.setModifiedBy(Integer.parseInt(userData.getUSER_ID()));
                CompanyUserModel.setModifiedDate(APPUtills.getCurrentDate());
                CompanyUserModel.setRecStatus(ApplicationConstants.RECORD_STATUS_PENDING);
         
                if (CompanyUserModel.getCompanyUserTmpId() == 0) {
                	if(id == 0){
                		id = seqNumberGeneratorFacade.getSequenceNumber(ApplicationConstants.COMPANY_USER_TMP_ID);
                	}else{
                		id++;
                	}
                    CompanyUserModel.setCompanyUserTmpId(id);
                    CompanyUserModel.setCreatedBy(Integer.parseInt(userData.getUSER_ID()));
                    CompanyUserModel.setCreatedDate(APPUtills.getCurrentDate());
                    System.out.println("MESSAGE | CREATE "+CompanyUserModel.toString());
                    session.save((CompanyUserTmp) CompanyUserModel.modelToObject(ApplicationConstants.TEMP_DATA));
                    
                } else {
                    System.out.println("MESSAGE | MODIFY "+CompanyUserModel.toString());
                    session.update((CompanyUserTmp) CompanyUserModel.modelToObject(ApplicationConstants.TEMP_DATA));
                    
                }
                tx.commit();
            }
            //tx.commit();
            isSuccess = true;
        } catch (Exception ex) {
            //context.setRollbackOnly();
            Log.error("ERROR   | Unable to save temp data." + ex.getMessage(), ex);
            throw new SBLException("Unable to save temp data.");
        }finally {
        	if(session!=null)session.close();
		}
        System.out.println("LEFT    | CompanyUserTmpFacade.saveEmp()");
        return isSuccess;
    }
  //-----------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean rejectEmp(List<CompanyUserModel> CompanyUserModelList) throws Exception {
        System.out.println("ENTERED | CompanyUserTmpFacade.rejectEmp()");
        boolean isSuccess = false;
        try {
        	session = DbConfig.sessionBulder();  
            tx = session.beginTransaction();
            for (CompanyUserModel CompanyUserModel : CompanyUserModelList) {
            	session.update((CompanyUserTmp) CompanyUserModel.modelToObject(ApplicationConstants.TEMP_DATA));            	
            }
            tx.commit();
            isSuccess = true;
        } catch (Exception ex) {
            //context.setRollbackOnly();
            Log.error("ERROR   | Unable to reject temp data." + ex.getMessage(), ex);
            throw new SBLException("Unable to reject temp data.");
        }finally {
        	if(session!=null)session.close();
		}
        System.out.println("LEFT    | CompanyUserTmpFacade.rejectEmp()");
        return isSuccess;
    }
  //-----------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean deleteEmp(List<CompanyUserModel> CompanyUserModelList) throws Exception {
        System.out.println("ENTERED | CompanyUserTmpFacade.deleteEmp()");
        boolean isSuccess = false;
        try {
        	session = DbConfig.sessionBulder();  
            tx = session.beginTransaction();
            for (CompanyUserModel CompanyUserModel : CompanyUserModelList) {
                session.delete((CompanyUserTmp) CompanyUserModel.modelToObject(ApplicationConstants.TEMP_DATA));
            }
            tx.commit();
            isSuccess = true;
        } catch (Exception ex) {
            //context.setRollbackOnly();
            Log.error("ERROR   | Unable to delete temp data." + ex.getMessage(), ex);
            throw new SBLException("Unable to delete temp data.");
        }finally {
        	if(session!=null)session.close();
		}
        System.out.println("LEFT    | CompanyUserTmpFacade.deleteEmp()");
        return isSuccess;
    }
  //-----------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean verifyEmp(List<CompanyUserModel> CompanyUserModelList) throws Exception {
        System.out.println("ENTERED | CompanyUserTmpFacade.verifyEmp()");
        boolean isSuccess = false;
        try {
        	session = DbConfig.sessionBulder();  
            tx = session.beginTransaction();
            for (CompanyUserModel CompanyUserModel : CompanyUserModelList) {
            	session.update((CompanyUserTmp) CompanyUserModel.modelToObject(ApplicationConstants.TEMP_DATA));
            }
            tx.commit();
            isSuccess = true;
        } catch (Exception ex) {
            //context.setRollbackOnly();
            Log.error("ERROR   | Unable to verify temp data." + ex.getMessage(), ex);
            throw new SBLException("Unable to verify temp data.");
        }finally {
        	if(session!=null)session.close();
		}
        System.out.println("LEFT    | CompanyUserTmpFacade.verifyEmp()");
        return isSuccess;
    }
}
