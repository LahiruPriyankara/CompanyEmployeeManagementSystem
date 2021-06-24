package com.company.service.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name="company_user")
public class CompanyUser {
	
	public CompanyUser() {}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private int id;

	@Column(name = "COMPANY_USER_ID")
	private String user_id;

	@Column(name = "COMPANY_USER_PSWD")
	private String user_pswd;
	
	@Column(name = "COMPANY_USER_FIRST_NAME")
	private String user_first_name;

	@Column(name = "COMPANY_USER_LAST_NAME")
	private String user_last_name;

	@Column(name = "COMPANY_USER_DEP_ID")
	private String user_dep_id;
	
	@Column(name = "COMPANY_USER_DEP_NAME")
	private String user_dep_name;
	
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
	
	@Column(name = "USER_ROLE")
	private int userRole;

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

	public String getUser_first_name() {
		return user_first_name;
	}

	public void setUser_first_name(String user_first_name) {
		this.user_first_name = user_first_name;
	}

	public String getUser_last_name() {
		return user_last_name;
	}

	public void setUser_last_name(String user_last_name) {
		this.user_last_name = user_last_name;
	}

	public String getUser_dep_id() {
		return user_dep_id;
	}

	public void setUser_dep_id(String user_dep_id) {
		this.user_dep_id = user_dep_id;
	}

	public String getUser_dep_name() {
		return user_dep_name;
	}

	public void setUser_dep_name(String user_dep_name) {
		this.user_dep_name = user_dep_name;
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

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "CompanyUser [id=" + id + ", user_id=" + user_id + ", user_pswd=" + user_pswd + ", user_first_name="
				+ user_first_name + ", user_last_name=" + user_last_name + ", user_dep_id=" + user_dep_id
				+ ", user_dep_name=" + user_dep_name + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", verifiedBy=" + verifiedBy
				+ ", verifiedDate=" + verifiedDate + ", Status=" + Status + ", userRole=" + userRole + "]";
	}
	
	
}
