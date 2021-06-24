/* 
    Author     : lahiru priyankara
*/
package com.company.dao;

import com.company.dto.UserReference;

/**
 *
 * @author sits_lahirupr
 */

public interface UserReferenceFacadeLocal {
    public UserReference getReferenceByUserId(int uId) throws Exception;
    public void addReference(UserReference dur) throws Exception;
}
