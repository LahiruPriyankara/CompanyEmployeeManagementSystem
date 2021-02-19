package com.company.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.company.common.ApplicationConstants;
import com.company.common.SBLException;
import com.company.dto.CommonUserMaster;
import com.company.dto.CommonUserTmp;

public class CommonUserModel {

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public CommonUserModel() {
	}

	public CommonUserModel(String cmnUserId, String cmnUserRole, String userStatus,String actionType){
		this.cmnUserId = cmnUserId;
		this.cmnUserRole = cmnUserRole;
		this.userStatus = userStatus;
		this.actionType = actionType;
	}

	private int cmnUserTmpId = 0;
	private int cmnUserMasterId = 0;
	private String cmnUserId = "";
	private String cmnUserRole = "";
	private Integer createdBy = 0;
	private Date createdDate = new Date();
	private Integer modifiedBy = 0;
	private Date modifiedDate = new Date();
	private Integer verifiedBy = 0;
	private Date verifiedDate = new Date();
	private String actionType = "";
	private String recStatus = "";
	private String userStatus = "";
	private String authComment = "";

	public int getCmnUserTmpId() {
		return cmnUserTmpId;
	}

	public void setCmnUserTmpId(int cmnUserTmpId) {
		this.cmnUserTmpId = cmnUserTmpId;
	}

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

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getRecStatus() {
		return recStatus;
	}

	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getAuthComment() {
		return authComment;
	}

	public void setAuthComment(String authComment) {
		this.authComment = authComment;
	}

	
	
	public Object modelToObject(String type) throws SBLException {

		if (!type.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)&& !type.equalsIgnoreCase(ApplicationConstants.MASTER_DATA)) {
			throw new SBLException("Invalid table type recived.type : " + type);
		}

		if (type.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)) {
			CommonUserTmp dfut = new CommonUserTmp();

			dfut.setCmnUserTmpId(cmnUserTmpId);
			dfut.setCmnUserMasterId(cmnUserMasterId);
			dfut.setCmnUserId(cmnUserId);
			dfut.setCmnUserRole(cmnUserRole);
			dfut.setCreatedBy(createdBy);
			dfut.setCreatedDate(createdDate);
			dfut.setModifiedBy(modifiedBy);
			dfut.setModifiedDate(modifiedDate);
			dfut.setVerifiedBy(verifiedBy);
			dfut.setVerifiedDate(verifiedDate);
			dfut.setActionType(actionType);
			dfut.setRecStatus(recStatus);
			dfut.setUserStatus(userStatus);
			dfut.setAuthComment(authComment);

			return dfut;

		} else {
			CommonUserMaster dfut = new CommonUserMaster();

			dfut.setCmnUserMasterId(cmnUserMasterId);
			dfut.setCmnUserId(cmnUserId);
			dfut.setCmnUserRole(cmnUserRole);
			dfut.setCreatedBy(createdBy);
			dfut.setCreatedDate(createdDate);
			dfut.setModifiedBy(modifiedBy);
			dfut.setModifiedDate(modifiedDate);
			dfut.setVerifiedBy(verifiedBy);
			dfut.setVerifiedDate(verifiedDate);
			dfut.setUserStatus(userStatus);

			return dfut;
		}
	}

	public static CommonUserModel objectToModel(Object obj, String type) throws Exception {
		CommonUserModel model = new CommonUserModel();
		try {
			if (obj == null) {
				throw new SBLException("Object Conversion error.Please try again.");
			}
			if (!type.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)&& !type.equalsIgnoreCase(ApplicationConstants.MASTER_DATA)) {
				throw new SBLException("Invalid table type recived.type : " + type);
			}
			if (type.equalsIgnoreCase(ApplicationConstants.MASTER_DATA)) {
				CommonUserMaster dfums = (CommonUserMaster) obj;
				// model.setFdUserTmpId(dfumsTemp.getFdUserTmpId());
				model.setCmnUserMasterId(dfums.getCmnUserMasterId());
				model.setCmnUserId(dfums.getCmnUserId());
				model.setCmnUserRole(dfums.getCmnUserRole());
				model.setCreatedBy(dfums.getCreatedBy());
				model.setCreatedDate(dfums.getCreatedDate());
				model.setModifiedBy(dfums.getModifiedBy());
				model.setModifiedDate(dfums.getModifiedDate());
				model.setVerifiedBy(dfums.getVerifiedBy());
				model.setVerifiedDate(dfums.getVerifiedDate());
				model.setUserStatus(dfums.getUserStatus());
			} else if (type.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)) {
				CommonUserTmp dfums = (CommonUserTmp) obj;
				model.setCmnUserTmpId(dfums.getCmnUserTmpId());
				model.setCmnUserMasterId(dfums.getCmnUserMasterId());
				model.setCmnUserId(dfums.getCmnUserId());
				model.setCmnUserRole(dfums.getCmnUserRole());
				model.setCreatedBy(dfums.getCreatedBy());
				model.setCreatedDate(dfums.getCreatedDate());
				model.setModifiedBy(dfums.getModifiedBy());
				model.setModifiedDate(dfums.getModifiedDate());
				model.setVerifiedBy(dfums.getVerifiedBy());
				model.setVerifiedDate(dfums.getVerifiedDate());
				model.setActionType(dfums.getActionType());
				model.setRecStatus(dfums.getRecStatus());
				model.setUserStatus(dfums.getUserStatus());
				model.setAuthComment(dfums.getAuthComment());
			}

		} catch (Exception e) {
			throw new SBLException("Object Conversion error.Please try again.");
		}

		return model;
	}

	
	@Override
	public String toString() {
		return "CommonUserModel [cmnUserTmpId=" + cmnUserTmpId + ", cmnUserMasterId=" + cmnUserMasterId + ", cmnUserId="
				+ cmnUserId + ", cmnUserRole=" + cmnUserRole + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", verifiedBy="
				+ verifiedBy + ", verifiedDate=" + verifiedDate + ", actionType=" + actionType + ", recStatus="
				+ recStatus + ", userStatus=" + userStatus + ", authComment=" + authComment + "]";
	}
}
