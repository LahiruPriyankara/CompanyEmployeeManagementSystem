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
public class UserData implements Cloneable {

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    String USER_NAME = "";
    String USER_PASWD = "";

    //     --------     should be manually assignede ------------    
    String USER_TYPE = "";//AD user or FRONT DESK USER

    //     --------     from resrvice response ------------
    String USER_ID = "";//4553
    String FIRST_NAME = "";//SAMAN
    String LAST_NAME = "";//MANCHANAYAKE
    String SECURITY_CLASS = "";//10
    String AD_USER_ID = "";
    String OFFICER_STATUS = "";
    String SOL_ID = "";//900
    String DIV_CODE = "";//875 -Deprartment
    String DIV_NAME = "";

    String base64Image = "";

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	public String getUSER_PASWD() {
		return USER_PASWD;
	}

	public void setUSER_PASWD(String uSER_PASWD) {
		USER_PASWD = uSER_PASWD;
	}

	public String getUSER_TYPE() {
		return USER_TYPE;
	}

	public void setUSER_TYPE(String uSER_TYPE) {
		USER_TYPE = uSER_TYPE;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

	public String getLAST_NAME() {
		return LAST_NAME;
	}

	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}

	public String getSECURITY_CLASS() {
		return SECURITY_CLASS;
	}

	public void setSECURITY_CLASS(String sECURITY_CLASS) {
		SECURITY_CLASS = sECURITY_CLASS;
	}

	public String getAD_USER_ID() {
		return AD_USER_ID;
	}

	public void setAD_USER_ID(String aD_USER_ID) {
		AD_USER_ID = aD_USER_ID;
	}

	public String getOFFICER_STATUS() {
		return OFFICER_STATUS;
	}

	public void setOFFICER_STATUS(String oFFICER_STATUS) {
		OFFICER_STATUS = oFFICER_STATUS;
	}

	public String getSOL_ID() {
		return SOL_ID;
	}

	public void setSOL_ID(String sOL_ID) {
		SOL_ID = sOL_ID;
	}

	public String getDIV_CODE() {
		return DIV_CODE;
	}

	public void setDIV_CODE(String dIV_CODE) {
		DIV_CODE = dIV_CODE;
	}

	public String getDIV_NAME() {
		return DIV_NAME;
	}

	public void setDIV_NAME(String dIV_NAME) {
		DIV_NAME = dIV_NAME;
	}

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

	@Override
	public String toString() {
		return "UserData [USER_NAME=" + USER_NAME + ", USER_PASWD=" + USER_PASWD + ", USER_TYPE=" + USER_TYPE
				+ ", USER_ID=" + USER_ID + ", FIRST_NAME=" + FIRST_NAME + ", LAST_NAME=" + LAST_NAME
				+ ", SECURITY_CLASS=" + SECURITY_CLASS + ", AD_USER_ID=" + AD_USER_ID + ", OFFICER_STATUS="
				+ OFFICER_STATUS + ", SOL_ID=" + SOL_ID + ", DIV_CODE=" + DIV_CODE + ", DIV_NAME=" + DIV_NAME
				+ ", base64Image=" + base64Image + "]";
	}

    
}
