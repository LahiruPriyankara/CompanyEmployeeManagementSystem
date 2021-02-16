package com.company.bl;

import javax.servlet.http.HttpServletRequest;

public interface EventLoggerLocal {
	public void doLog(HttpServletRequest req, String pUserId, String pEvtType, String pEvtAction, String pEvtDesc, String pOldValue, String pNewValue, String pEvtStatus);
}
