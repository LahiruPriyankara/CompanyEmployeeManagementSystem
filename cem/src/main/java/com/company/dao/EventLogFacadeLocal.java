/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
