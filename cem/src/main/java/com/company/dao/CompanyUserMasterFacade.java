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

import com.company.common.ApplicationConstants;
import com.company.dto.CompanyUserMaster;
import com.company.models.CompanyUserModel;
import com.company.common.APPUtills;
import com.company.common.SBLException;
import com.company.dbconfig.DbConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sits_lahirupr
 */
@Repository
public class CompanyUserMasterFacade implements CompanyUserMasterFacadeLocal {
	Session session;
	Transaction tx;
    private static Logger dedLog = LogManager.getLogger(CompanyUserTmpFacade.class);

    @Autowired
   	SeqNumberGeneratorFacadeLocal seqNumberGeneratorFacade;

    @Override
    public Map<String, CompanyUserModel> getMasterCompanyUsers(List<String> ids, String depCode) throws Exception {
        System.out.println("ENTERED | CompanyUserMasterFacade.getMasterCompanyUsers()");
        Map<String, CompanyUserModel> dfumsMap = new HashMap();
        List<CompanyUserMaster> dfumsList = new ArrayList<>();
        Query query = null;
        try {
            System.out.println("MESSAGE | ids : "+ids+" ,depCode : "+depCode);
            ids = (ids != null) ? ids : new ArrayList<>();
            session = DbConfig.sessionBulder();  
            tx = session.beginTransaction();
            
            if (ids.isEmpty() && !APPUtills.isThisStringValid(depCode)) { // empty - depCode empty
                System.out.println("MESSAGE | Going to fetch all master data");
                query = session.createQuery("FROM CompanyUserMaster tbl ORDER BY tbl.companyUserEmpId DESC");

            } else if (ids.isEmpty() && APPUtills.isThisStringValid(depCode)) {// empty -  not empty
                //dfumsList = findAll();
                System.out.println("MESSAGE | Going to fetch all master data for dep id "+depCode);
                query = session.createQuery("FROM CompanyUserMaster tbl WHERE tbl.companyUserDivId=:companyUserDivId ORDER BY tbl.companyUserEmpId DESC");
                query.setParameter("companyUserDivId", depCode);
                
            } else if (!ids.isEmpty() && !APPUtills.isThisStringValid(depCode)) {// not empty -  empty
                //dfumsList = findAll();
                System.out.println("MESSAGE | Going to fetch all master data for emp ids "+Arrays.asList(ids));
                query = session.createQuery("FROM CompanyUserMaster tbl WHERE tbl.companyUserEmpId IN :companyUserEmpId ORDER BY tbl.companyUserEmpId DESC");
                query.setParameterList("companyUserEmpId", ids);
                
            } else {// not empty - not empty
                System.out.println("MESSAGE | Going to fetch all master data for given employee emp ids "+Arrays.asList(ids)+" sol id "+depCode);
                query = session.createQuery("FROM CompanyUserMaster tbl WHERE tbl.companyUserEmpId IN :companyUserEmpId AND tbl.companyUserDivId=:companyUserDivId ORDER BY tbl.companyUserEmpId DESC");
                query.setParameterList("companyUserEmpId", ids);
                query.setParameter("companyUserDivId", depCode); 
                
            }
            
            System.out.println("HQL     | " + query.toString());
            dfumsList = query.list();
            tx.commit();

            System.out.println("MESSAGE | dfumsList.size() : " + dfumsList.size());

            // Converting to PayModeHelper list
            dfumsMap = objectToModel(dfumsList);
        } catch (Exception ex) {
            dedLog.error("ERROR   | Unable to fetch list of object from master table." + ex.getMessage(), ex);
            throw new SBLException("Unable to fetch data.");
        }finally{
        	if(session!=null)session.close();
        }
        System.out.println("LEFT    | CompanyUserMasterFacade.getMasterCompanyUsers()");
        return dfumsMap;
    }
  //-----------------------------------------------------------------------------------------------------------------------------
    public Map<String, CompanyUserModel> objectToModel(List<CompanyUserMaster> dfumsTempList) throws Exception {
        System.out.println("ENTERED | CompanyUserMasterFacade.objectToModel()");
        Map<String, CompanyUserModel> dfumsMap = new HashMap();
        CompanyUserModel model;
        try {
            dfumsTempList = dfumsTempList != null ? dfumsTempList : new ArrayList<>();
            for (CompanyUserMaster dfumsMaster : dfumsTempList) {
                model = CompanyUserModel.objectToModel(dfumsMaster, ApplicationConstants.MASTER_DATA);
                model.setActionType(ApplicationConstants.ACTION_TYPE_MODIFY);
                dfumsMap.put(dfumsMaster.getCompanyUserEmpId(), model);
            }
        } catch (Exception ex) {
            dedLog.error("ERROR   | Unable to convert master to model." + ex.getMessage(), ex);
            throw new SBLException("Object Conversion error.Please try again.");
        }
        System.out.println("LEFT    | CompanyUserMasterFacade.objectToModel()");
        return dfumsMap;
    }
  //-----------------------------------------------------------------------------------------------------------------------------   
    @Override
    public boolean verifyEmp(List<CompanyUserModel> CompanyUserModelList) throws Exception {
        System.out.println("ENTERED | CompanyUserMasterFacade.verifyEmp()");
        boolean isSuccess = false;
        int id = 0;
        try {
        	session = DbConfig.sessionBulder();  
            
            System.out.println("MESSAGE | CompanyUserModelList.size() : "+CompanyUserModelList.size());
            for (CompanyUserModel CompanyUserModel : CompanyUserModelList) {
            	tx = session.beginTransaction();
                if (CompanyUserModel.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY)) {
                    System.out.println("MESSAGE | EDIT => Saving modify record : " + CompanyUserModel.toString());
                    session.update((CompanyUserMaster) CompanyUserModel.modelToObject(ApplicationConstants.MASTER_DATA));
                   
                } else {
                    System.out.println("MESSAGE | CREATE => Saving new record : " + CompanyUserModel.toString());
                    if(id == 0){
                		id = seqNumberGeneratorFacade.getSequenceNumber(ApplicationConstants.COMPANY_USER_MASTER_ID);
                	}else{
                		id++;
                	}
                    CompanyUserModel.setCompanyUserMasterId(id);
                    session.save((CompanyUserMaster) CompanyUserModel.modelToObject(ApplicationConstants.MASTER_DATA));
                   
                }
                tx.commit();
            }
           
            isSuccess = true;
        } catch (Exception ex) {
            //context.setRollbackOnly();
            dedLog.error("ERROR   | Unable to verify temp data." + ex.getMessage(), ex);
            throw new SBLException("Unable to verify temp data.");
        }finally{
        	if(session!=null)session.close();
        }
        System.out.println("LEFT    | CompanyUserMasterFacade.verifyEmp()");
        return isSuccess;
    }

}
