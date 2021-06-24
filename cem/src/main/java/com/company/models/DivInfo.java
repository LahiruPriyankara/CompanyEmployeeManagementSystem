
package com.company.models;

/* 
Author     : lahiru priyankara
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
