/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
