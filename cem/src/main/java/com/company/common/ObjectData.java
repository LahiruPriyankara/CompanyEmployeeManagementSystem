/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.common;

/**
 *
 * @author sits_lahirupr
 */
public class ObjectData {

    private String objName;
    private int scope;

    public ObjectData(String objName, int scope) {
        this.objName = objName;
        this.scope = scope;
    }

    /**
     * @return
     */
    public String getObjName() {
        return objName;
    }

    /**
     * @return
     */
    public int getScope() {
        return scope;
    }

    /**
     * @param string
     */
    public void setObjName(String string) {
        objName = string;
    }

    /**
     * @param i
     */
    public void setScope(int i) {
        scope = i;
    }
}
