/* 
    Author     : lahiru priyankara
*/

package com.company.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CUD_COMMON_USER_MASTER")
public class CommonUserMaster implements Serializable {

    private static final long serialVersionUID = 1L;   
    
    @Id
    @Column(name = "COMMON_USER_MASTER_ID")
    private int cmnUserMasterId;
    
    @Size(max = 20)
    @Column(name = "COMMON_USER_ID")
    private String cmnUserId;
    
    @Size(max = 30)
    @Column(name = "COMMON_USER_ROLE")
    private String cmnUserRole;
    
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Column(name = "MODIFIED_BY")
    private Integer modifiedBy;
    
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
    @Column(name = "VERIFIED_BY")
    private Integer verifiedBy;
    
    @Column(name = "VERIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date verifiedDate;
    
    @Size(max = 3)
    @Column(name = "USER_STATUS")
    private String userStatus;

	public int getCmnUserMasterId() {
		return cmnUserMasterId;
	}

	public void setCmnUserMasterId(int cmnUserMasterId) {
		this.cmnUserMasterId = cmnUserMasterId;
	}

	public String getCmnUserId() {
		return cmnUserId;
	}

	public void setCmnUserId(String cmnUserId) {
		this.cmnUserId = cmnUserId;
	}

	public String getCmnUserRole() {
		return cmnUserRole;
	}

	public void setCmnUserRole(String cmnUserRole) {
		this.cmnUserRole = cmnUserRole;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(Integer verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public Date getVerifiedDate() {
		return verifiedDate;
	}

	public void setVerifiedDate(Date verifiedDate) {
		this.verifiedDate = verifiedDate;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CommonUserMaster [cmnUserMasterId=" + cmnUserMasterId + ", cmnUserId=" + cmnUserId + ", cmnUserRole="
				+ cmnUserRole + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate + ", verifiedBy=" + verifiedBy + ", verifiedDate="
				+ verifiedDate + ", userStatus=" + userStatus + "]";
	}

}
