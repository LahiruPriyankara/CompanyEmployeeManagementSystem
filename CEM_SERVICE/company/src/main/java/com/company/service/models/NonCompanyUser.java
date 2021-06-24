package com.company.service.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "non_company_user")
public class NonCompanyUser {
	public NonCompanyUser() {}
	
	@Id
	@GeneratedValue//(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private int id;

	@Column(name = "NON_COMPANY_USER_ID")
	private String user_id;

	@Column(name = "NON_COMPANY_USER_PSWD")
	private String user_pswd;	
	
	@Column(name = "LOGGIN_FAIL_COUNT")
	private int loggin_fail_count;
	
	@Column(name = "CREATED_BY")
	private int createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate = new Date();

	@Column(name = "MODIFIED_BY")
	private int modifiedBy;

	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate = new Date();

	@Column(name = "VERIFIED_BY")
	private int verifiedBy;

	@Column(name = "VERIFIED_DATE")
	private Date verifiedDate = new Date();

	@Column(name = "USER_STATUS")
	private String Status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_pswd() {
		return user_pswd;
	}

	public void setUser_pswd(String user_pswd) {
		this.user_pswd = user_pswd;
	}

	public int getLoggin_fail_count() {
		return loggin_fail_count;
	}

	public void setLoggin_fail_count(int loggin_fail_count) {
		this.loggin_fail_count = loggin_fail_count;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(int verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public Date getVerifiedDate() {
		return verifiedDate;
	}

	public void setVerifiedDate(Date verifiedDate) {
		this.verifiedDate = verifiedDate;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "NonCompanyUser [id=" + id + ", user_id=" + user_id + ", user_pswd=" + user_pswd + ", loggin_fail_count="
				+ loggin_fail_count + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate + ", verifiedBy=" + verifiedBy + ", verifiedDate="
				+ verifiedDate + ", Status=" + Status + "]";
	}

	
}
