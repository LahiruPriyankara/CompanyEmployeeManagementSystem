/* 
    Author     : lahiru priyankara
*/

package com.company.dao;

import java.util.List;

import com.company.dto.VisitorData;

public interface VisitorDataFacadeLocal {
	public boolean insertRecord(VisitorData data) throws Exception;
	public List<VisitorData> getAllRecord() throws Exception;
}
