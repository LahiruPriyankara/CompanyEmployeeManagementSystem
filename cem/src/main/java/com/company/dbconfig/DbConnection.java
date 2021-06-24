/* 
    Author     : lahiru priyankara
*/

package com.company.dbconfig;

import org.hibernate.cfg.Configuration;

import com.company.dto.CommonUserMaster;
import com.company.dto.CommonUserTmp;
import com.company.dto.CompanyUserMaster;
import com.company.dto.CompanyUserTmp;
import com.company.dto.EventLogTbl;
import com.company.dto.FdUserMaster;
import com.company.dto.FdUserTmp;
import com.company.dto.UserReference;
import com.company.dto.VisitorData;

public class DbConnection {
	private DbConnection() {
	}

	private static DbConnection dbConnection = null;

	public static DbConnection getInstance() {
		if (dbConnection == null)
			dbConnection = new DbConnection();
		return dbConnection;
	}

	public Configuration getConnection() {
		return new Configuration().configure().addAnnotatedClass(CompanyUserMaster.class)
				.addAnnotatedClass(CompanyUserTmp.class).addAnnotatedClass(EventLogTbl.class)
				.addAnnotatedClass(FdUserMaster.class).addAnnotatedClass(FdUserTmp.class)
				.addAnnotatedClass(UserReference.class).addAnnotatedClass(CommonUserMaster.class)
				.addAnnotatedClass(CommonUserTmp.class).addAnnotatedClass(VisitorData.class);

	}
}
