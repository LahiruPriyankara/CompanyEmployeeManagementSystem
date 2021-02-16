/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sits_lahirupr
 */
@Repository
public class SeqNumberGeneratorBean implements SeqNumberGeneratorBeanLocal {
/*
    private static final Logger cbmAdminLog = LogManager.getLogger(SeqNumberGeneratorBean.class);

    @PersistenceContext(unitName = "DEDirectory-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public int getNextAdminEventLogId() throws Exception {
        cbmAdminLog.info("ENTERED | SeqNumberGeneratorBean.getNextAdminEventLogId()");
        int seqNo = 0;

        try {
            String hql = "SELECT SEQ_DVM_EVENT_LOG.NEXTVAL FROM DUAL";
            Query qu = em.createNativeQuery(hql);
            seqNo = (int) qu.getSingleResult();
        } catch (Exception ex) {
            cbmAdminLog.error("ERROR   | " + ex.getMessage(), ex);
            throw new Exception("Failed to getNextAdminEventLogId");
        }

        cbmAdminLog.info("LEFT    | SeqNumberGeneratorBean.getNextAdminEventLogId()");
        return seqNo;
    }

    @Override
    public int getNextBankUserMasterId() throws Exception {
        cbmAdminLog.info("ENTERED | SeqNumberGeneratorBean.getNextBankUserMasterId()");
        int seqNo = 0;

        try {
            String hql = "SELECT SEQ_BANK_USER_ID_MASTER.NEXTVAL FROM DUAL";
            Query qu = em.createNativeQuery(hql);
            seqNo = (int) qu.getSingleResult();
        } catch (Exception ex) {
            cbmAdminLog.error("ERROR   | " + ex.getMessage(), ex);
            throw new Exception("Failed to getNextBankUserMasterId");
        }

        cbmAdminLog.info("LEFT    | SeqNumberGeneratorBean.getNextBankUserMasterId()");
        return seqNo;
    }

    @Override
    public int getNextBankUserTempId() throws Exception {
        cbmAdminLog.info("ENTERED | SeqNumberGeneratorBean.getNextBankUserTempId()");
        int seqNo = 0;

        try {
            String hql = "SELECT SEQ_BANK_USER_ID_TMP.NEXTVAL FROM DUAL";
            Query qu = em.createNativeQuery(hql);
            seqNo = (int) qu.getSingleResult();
        } catch (Exception ex) {
            cbmAdminLog.error("ERROR   | " + ex.getMessage(), ex);
            throw new Exception("Failed to getNextBankUserTempId");
        }

        cbmAdminLog.info("LEFT    | SeqNumberGeneratorBean.getNextBankUserTempId()");
        return seqNo;
    }

    @Override
    public int getNextFDUserMasterId() throws Exception {
        cbmAdminLog.info("ENTERED | SeqNumberGeneratorBean.getNextFDUserMasterId()");
        int seqNo = 0;

        try {
            String hql = "SELECT SEQ_FD_USER_ID_MASTER.NEXTVAL FROM DUAL";
            Query qu = em.createNativeQuery(hql);
            seqNo = (int) qu.getSingleResult();
        } catch (Exception ex) {
            cbmAdminLog.error("ERROR   | " + ex.getMessage(), ex);
            throw new Exception("Failed to getNextFDUserMasterId");
        }

        cbmAdminLog.info("LEFT    | SeqNumberGeneratorBean.getNextFDUserMasterId()");
        return seqNo;
    }

    @Override
    public int getNextFDUserTempId() throws Exception {
        cbmAdminLog.info("ENTERED | SeqNumberGeneratorBean.getNextFDUserTempId()");
        int seqNo = 0;

        try {
            String hql = "SELECT SEQ_FD_USER_ID_TMP.NEXTVAL FROM DUAL";
            Query qu = em.createNativeQuery(hql);
            seqNo = (int) qu.getSingleResult();
        } catch (Exception ex) {
            cbmAdminLog.error("ERROR   | " + ex.getMessage(), ex);
            throw new Exception("Failed to getNextFDUserTempId");
        }

        cbmAdminLog.info("LEFT    | SeqNumberGeneratorBean.getNextFDUserTempId()");
        return seqNo;
    }

    @Override
    public int getNextUserReferenceId() throws Exception {
        cbmAdminLog.info("ENTERED | SeqNumberGeneratorBean.getNextUserReferenceId()");
        int seqNo = 0;

        try {
            String hql = "SELECT SEQ_USER_REFERENCE_ID.NEXTVAL FROM DUAL";
            Query qu = em.createNativeQuery(hql);
            seqNo = (int) qu.getSingleResult();
        } catch (Exception ex) {
            cbmAdminLog.error("ERROR   | " + ex.getMessage(), ex);
            throw new Exception("Failed to getNextUserReferenceId");
        }

        cbmAdminLog.info("LEFT    | SeqNumberGeneratorBean.getNextUserReferenceId()");
        return seqNo;
    }*/
}
