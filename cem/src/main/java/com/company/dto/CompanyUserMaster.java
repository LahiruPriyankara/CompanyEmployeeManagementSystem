/* 
    Author     : lahiru priyankara
*/
package com.company.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "CUD_COMPANY_USER_MASTER")
public class CompanyUserMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Id
	@Column(name = "COMPANY_USER_MASTER_ID")
	private int companyUserMasterId;
	@Size(max = 30)
	@Column(name = "COMPANY_USER_EMP_ID")
	private String companyUserEmpId;
	@Lob
	@Column(name = "COMPANY_USER_PROF_IMG")
	private Serializable companyUserProfImg;
	@Size(max = 30)
	@Column(name = "COMPANY_USER_FIRST_NAME")
	private String companyUserFirstName;
	@Size(max = 30)
	@Column(name = "COMPANY_USER_MIDDLE_NAME")
	private String companyUserMiddleName;
	@Size(max = 30)
	@Column(name = "COMPANY_USER_LAST_NAME")
	private String companyUserLastName;
	@Size(max = 3)
	@Column(name = "COMPANY_USER_GENDER")
	private String companyUserGender;
	@Size(max = 20)
	@Column(name = "COMPANY_USER_GRADE")
	private String companyUserGrade;
	@Size(max = 30)
	@Column(name = "COMPANY_USER_DESTINATION")
	private String companyUserDestination;
	@Size(max = 6)
	@Column(name = "COMPANY_USER_DIV_ID")
	private String companyUserDivId;
	@Size(max = 50)
	@Column(name = "COMPANY_USER_DEP_NAME")
	private String companyUserDepName;
	@Size(max = 3)
	@Column(name = "COMPANY_USER_FLOOR")
	private String companyUserFloor;
	@Size(max = 100)
	@Column(name = "COMPANY_USER_JOB_DESC")
	private String companyUserJobDesc;
	@Size(max = 6)
	@Column(name = "COMPANY_USER_DEP_EXTEN")
	private String companyUserDepExten;
	@Size(max = 15)
	@Column(name = "COMPANY_USER_OFFICE_MOBILE")
	private String companyUserOfficeMobile;
	@Size(max = 6)
	@Column(name = "COMPANY_USER_OFFICE_EXTEN")
	private String companyUserOfficeExten;
	@Size(max = 30)
	@Column(name = "COMPANY_USER_OFFICE_EMAIL")
	private String companyUserOfficeEmail;
	@Size(max = 50)
	@Column(name = "COMPANY_USER_CONTACT_PERSON_NAME")
	private String companyUserContactPersonName;
	@Size(max = 15)
	@Column(name = "COMPANY_USER_CONTACT_PERSON_MOBILE")
	private String companyUserContactPersonMobile;
	@Size(max = 6)
	@Column(name = "COMPANY_USER_CONTACT_PERSON_EXTEN")
	private String companyUserContactPersonExten;
	@Size(max = 30)
	@Column(name = "COMPANY_USER_REMARKS")
	private String companyUserRemarks;
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

	public CompanyUserMaster() {
	}

	public int getCompanyUserMasterId() {
		return companyUserMasterId;
	}

	public void setCompanyUserMasterId(int companyUserMasterId) {
		this.companyUserMasterId = companyUserMasterId;
	}

	public String getCompanyUserEmpId() {
		return companyUserEmpId;
	}

	public void setCompanyUserEmpId(String companyUserEmpId) {
		this.companyUserEmpId = companyUserEmpId;
	}

	public Serializable getCompanyUserProfImg() {
		return companyUserProfImg;
	}

	public void setCompanyUserProfImg(Serializable companyUserProfImg) {
		this.companyUserProfImg = companyUserProfImg;
	}

	public String getCompanyUserFirstName() {
		return companyUserFirstName;
	}

	public void setCompanyUserFirstName(String companyUserFirstName) {
		this.companyUserFirstName = companyUserFirstName;
	}

	public String getCompanyUserMiddleName() {
		return companyUserMiddleName;
	}

	public void setCompanyUserMiddleName(String companyUserMiddleName) {
		this.companyUserMiddleName = companyUserMiddleName;
	}

	public String getCompanyUserLastName() {
		return companyUserLastName;
	}

	public void setCompanyUserLastName(String companyUserLastName) {
		this.companyUserLastName = companyUserLastName;
	}

	public String getCompanyUserGender() {
		return companyUserGender;
	}

	public void setCompanyUserGender(String companyUserGender) {
		this.companyUserGender = companyUserGender;
	}

	public String getCompanyUserGrade() {
		return companyUserGrade;
	}

	public void setCompanyUserGrade(String companyUserGrade) {
		this.companyUserGrade = companyUserGrade;
	}

	public String getCompanyUserDestination() {
		return companyUserDestination;
	}

	public void setCompanyUserDestination(String companyUserDestination) {
		this.companyUserDestination = companyUserDestination;
	}

	public String getCompanyUserDivId() {
		return companyUserDivId;
	}

	public void setCompanyUserDivId(String companyUserDivId) {
		this.companyUserDivId = companyUserDivId;
	}

	public String getCompanyUserDepName() {
		return companyUserDepName;
	}

	public void setCompanyUserDepName(String companyUserDepName) {
		this.companyUserDepName = companyUserDepName;
	}

	public String getCompanyUserFloor() {
		return companyUserFloor;
	}

	public void setCompanyUserFloor(String companyUserFloor) {
		this.companyUserFloor = companyUserFloor;
	}

	public String getCompanyUserJobDesc() {
		return companyUserJobDesc;
	}

	public void setCompanyUserJobDesc(String companyUserJobDesc) {
		this.companyUserJobDesc = companyUserJobDesc;
	}

	public String getCompanyUserDepExten() {
		return companyUserDepExten;
	}

	public void setCompanyUserDepExten(String companyUserDepExten) {
		this.companyUserDepExten = companyUserDepExten;
	}

	public String getCompanyUserOfficeMobile() {
		return companyUserOfficeMobile;
	}

	public void setCompanyUserOfficeMobile(String companyUserOfficeMobile) {
		this.companyUserOfficeMobile = companyUserOfficeMobile;
	}

	public String getCompanyUserOfficeExten() {
		return companyUserOfficeExten;
	}

	public void setCompanyUserOfficeExten(String companyUserOfficeExten) {
		this.companyUserOfficeExten = companyUserOfficeExten;
	}

	public String getCompanyUserOfficeEmail() {
		return companyUserOfficeEmail;
	}

	public void setCompanyUserOfficeEmail(String companyUserOfficeEmail) {
		this.companyUserOfficeEmail = companyUserOfficeEmail;
	}

	public String getCompanyUserContactPersonName() {
		return companyUserContactPersonName;
	}

	public void setCompanyUserContactPersonName(String companyUserContactPersonName) {
		this.companyUserContactPersonName = companyUserContactPersonName;
	}

	public String getCompanyUserContactPersonMobile() {
		return companyUserContactPersonMobile;
	}

	public void setCompanyUserContactPersonMobile(String companyUserContactPersonMobile) {
		this.companyUserContactPersonMobile = companyUserContactPersonMobile;
	}

	public String getCompanyUserContactPersonExten() {
		return companyUserContactPersonExten;
	}

	public void setCompanyUserContactPersonExten(String companyUserContactPersonExten) {
		this.companyUserContactPersonExten = companyUserContactPersonExten;
	}

	public String getCompanyUserRemarks() {
		return companyUserRemarks;
	}

	public void setCompanyUserRemarks(String companyUserRemarks) {
		this.companyUserRemarks = companyUserRemarks;
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

	/*
	 * @Override public int hashCode() { int hash = 0; hash +=
	 * (companyUserMasterId != null ? companyUserMasterId.hashCode() : 0);
	 * return hash; }
	 * 
	 * @Override public boolean equals(Object object) { // TODO: Warning - this
	 * method won't work in the case the id fields are not set if (!(object
	 * instanceof CompanyUserMaster)) { return false; } CompanyUserMaster other
	 * = (CompanyUserMaster) object; if ((this.companyUserMasterId == null &&
	 * other.companyUserMasterId != null) || (this.companyUserMasterId != null
	 * && !this.companyUserMasterId.equals(other.companyUserMasterId))) { return
	 * false; } return true; }
	 */
	@Override
	public String toString() {
		return "CompanyUserMaster{" + "companyUserMasterId=" + companyUserMasterId + ", companyUserEmpId="
				+ companyUserEmpId + ", companyUserProfImg=" + companyUserProfImg + ", companyUserFirstName="
				+ companyUserFirstName + ", companyUserMiddleName=" + companyUserMiddleName + ", companyUserLastName="
				+ companyUserLastName + ", companyUserGender=" + companyUserGender + ", companyUserGrade="
				+ companyUserGrade + ", companyUserDestination=" + companyUserDestination + ", companyUserDivId="
				+ companyUserDivId + ", companyUserDepName=" + companyUserDepName + ", companyUserFloor="
				+ companyUserFloor + ", companyUserJobDesc=" + companyUserJobDesc + ", companyUserDepExten="
				+ companyUserDepExten + ", companyUserOfficeMobile=" + companyUserOfficeMobile
				+ ", companyUserOfficeExten=" + companyUserOfficeExten + ", companyUserOfficeEmail="
				+ companyUserOfficeEmail + ", companyUserContactPersonName=" + companyUserContactPersonName
				+ ", companyUserContactPersonMobile=" + companyUserContactPersonMobile
				+ ", companyUserContactPersonExten=" + companyUserContactPersonExten + ", companyUserRemarks="
				+ companyUserRemarks + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", modifiedBy="
				+ modifiedBy + ", modifiedDate=" + modifiedDate + ", verifiedBy=" + verifiedBy + ", verifiedDate="
				+ verifiedDate + ", userStatus=" + userStatus + '}';
	}

}
