package com.company.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUD_USEQUENCE_NUMBER")
public class SequenceNumber implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID")
    int id;     
    @Column(name = "COMPANY_USER_MASTER_ID")
    int companyUserMasterId; 
    @Column(name = "COMPANY_USER_TMP_ID")
    int companyUserTmpId;
    @Column(name = "FD_USER_MASTER_ID")
    int fdUserMasterId;
    @Column(name = "FD_USER_TMP_ID")
    int fdUserTmpId;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCompanyUserMasterId() {
		return companyUserMasterId;
	}
	public void setCompanyUserMasterId(int companyUserMasterId) {
		this.companyUserMasterId = companyUserMasterId;
	}
	public int getCompanyUserTmpId() {
		return companyUserTmpId;
	}
	public void setCompanyUserTmpId(int companyUserTmpId) {
		this.companyUserTmpId = companyUserTmpId;
	}
	public int getFdUserMasterId() {
		return fdUserMasterId;
	}
	public void setFdUserMasterId(int fdUserMasterId) {
		this.fdUserMasterId = fdUserMasterId;
	}
	public int getFdUserTmpId() {
		return fdUserTmpId;
	}
	public void setFdUserTmpId(int fdUserTmpId) {
		this.fdUserTmpId = fdUserTmpId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "SequenceNumber [id=" + id + ", companyUserMasterId=" + companyUserMasterId + ", companyUserTmpId="
				+ companyUserTmpId + ", fdUserMasterId=" + fdUserMasterId + ", fdUserTmpId=" + fdUserTmpId + "]";
	}
    
}
