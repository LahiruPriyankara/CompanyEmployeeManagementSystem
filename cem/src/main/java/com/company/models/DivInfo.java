/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.models;

/**
 *
 * @author sits_lahirupr
 */
public class DivInfo {

    String divId;
    String name;

    public String getDivId() {
        return divId;
    }

    public void setDivId(String solId) {
        this.divId = solId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SolInfo{" + "divId=" + divId + ", name=" + name + '}';
    }    
    
}
