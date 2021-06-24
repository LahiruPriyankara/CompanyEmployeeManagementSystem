/* 
    Author     : lahiru priyankara
*/
package com.company.dao;

import java.util.List;

import com.company.common.SBLException;
import com.company.dto.EventLogTbl;

/**
 *
 * @author sits_lahirupr
 */

public interface EventLogFacadeLocal {
	public void doLog(EventLogTbl logData) throws SBLException;
}
