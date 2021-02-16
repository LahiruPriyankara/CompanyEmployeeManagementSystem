/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.bl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.company.dao.UserReferenceFacadeLocal;
import com.company.dto.UserReference;
import com.company.models.UserData;
import com.company.common.APPUtills;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

/**
 *
 * @author sits_lahirupr
 */
@Service
@ComponentScan(basePackages = { "com.company.dao" })
public class UserReferenceLogic implements UserReferenceLogicLocal {

	private static Logger APPLog = LogManager.getLogger(UserReferenceLogic.class);

	@Autowired
	private UserReferenceFacadeLocal userReferenceFacade;

	@Override
	public boolean upateReferenceByUserId(UserData userData) throws Exception {
		APPLog.info("ENTERED | UserReferenceLogic.upateReferenceByUserId()");
		boolean isSuccess = false;
		try {
			UserReference dur = userReferenceFacade.getReferenceByUserId(Integer.parseInt(userData.getUSER_ID()));
			if (dur == null) {
				dur = new UserReference();

				dur.setUserId(Integer.parseInt(userData.getUSER_ID()));
				dur.setFirstLoginDate(APPUtills.getCurrentDate());
				dur.setLoggingCount(1);
				dur.setLastLoginDate(APPUtills.getCurrentDate());
			} else {
				dur.setLoggingCount(dur.getLoggingCount() + 1);
				dur.setLastLoginDate(APPUtills.getCurrentDate());
			}
			userReferenceFacade.addReference(dur);

		} catch (Exception ex) {
			APPLog.error("ERROR   | Unable to update reference table." + ex.getMessage(), ex);
			// throw new SBLException("Unable to reject.Please try again.");
		}
		APPLog.info("LEFT    | UserReferenceLogic.upateReferenceByUserId()");
		return isSuccess;
	}
}
