
package com.company.models;

import java.util.Date;
import com.company.common.ApplicationConstants;
import com.company.dto.FdUserMaster;
import com.company.dto.FdUserTmp;
import com.company.common.SBLException;

/* 
Author     : lahiru priyankara
*/
public class FdUserModel implements Cloneable {

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public FdUserModel() {
	}

	public FdUserModel(String fdUserName, String fdUserFirstName, String fdUserLastName, String userStatus,
			String actionType, String securepassUserStatus) {
		this.fdUserName = fdUserName;
		this.fdUserFirstName = fdUserFirstName;
		this.fdUserLastName = fdUserLastName;
		this.userStatus = userStatus;
		this.actionType = actionType;
		this.securepassUserStatus = securepassUserStatus;
	}

	private int fdUserTmpId = 0;
	private int fdUserMasterId = 0;
	private String fdUserName = "";
	private String fdUserFirstName = "";
	private String fdUserLastName = "";
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
	private String securepassUserStatus = "";

	public int getFdUserTmpId() {
		return fdUserTmpId;
	}

	public void setFdUserTmpId(int fdUserTmpId) {
		this.fdUserTmpId = fdUserTmpId;
	}

	public int getFdUserMasterId() {
		return fdUserMasterId;
	}

	public void setFdUserMasterId(int fdUserMasterId) {
		this.fdUserMasterId = fdUserMasterId;
	}

	public String getFdUserName() {
		return fdUserName;
	}

	public void setFdUserName(String fdUserName) {
		this.fdUserName = fdUserName;
	}

	public String getFdUserFirstName() {
		return fdUserFirstName;
	}

	public void setFdUserFirstName(String fdUserFirstName) {
		this.fdUserFirstName = fdUserFirstName;
	}

	public String getFdUserLastName() {
		return fdUserLastName;
	}

	public void setFdUserLastName(String fdUserLastName) {
		this.fdUserLastName = fdUserLastName;
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

	public String getSecurepassUserStatus() {
		return securepassUserStatus;
	}

	public void setSecurepassUserStatus(String securepassUserStatus) {
		this.securepassUserStatus = securepassUserStatus;
	}

	public Object modelToObject(String type) throws SBLException {

		if (!type.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)
				&& !type.equalsIgnoreCase(ApplicationConstants.MASTER_DATA)) {
			throw new SBLException("Invalid table type recived.type : " + type);
		}

		if (type.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)) {
			FdUserTmp dfut = new FdUserTmp();

			dfut.setFdUserTmpId(fdUserTmpId);
			dfut.setFdUserMasterId(fdUserMasterId);
			dfut.setFdUserName(fdUserName);
			dfut.setFdUserFirstName(fdUserFirstName);
			dfut.setFdUserLastName(fdUserLastName);
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
			dfut.setSecurepassUserStatus(securepassUserStatus);

			return dfut;

		} else {
			FdUserMaster dfut = new FdUserMaster();

			dfut.setFdUserMasterId(fdUserMasterId);
			dfut.setFdUserName(fdUserName);
			dfut.setFdUserFirstName(fdUserFirstName);
			dfut.setFdUserLastName(fdUserLastName);
			dfut.setCreatedBy(createdBy);
			dfut.setCreatedDate(createdDate);
			dfut.setModifiedBy(modifiedBy);
			dfut.setModifiedDate(modifiedDate);
			dfut.setVerifiedBy(verifiedBy);
			dfut.setVerifiedDate(verifiedDate);
			dfut.setUserStatus(userStatus);
			dfut.setSecurepassUserStatus(securepassUserStatus);

			return dfut;
		}
	}

	public static FdUserModel objectToModel(Object obj, String type) throws Exception {
		FdUserModel model = new FdUserModel();
		try {
			if (obj == null) {
				throw new SBLException("Object Conversion error.Please try again.");
			}
			if (!type.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)
					&& !type.equalsIgnoreCase(ApplicationConstants.MASTER_DATA)) {
				throw new SBLException("Invalid table type recived.type : " + type);
			}
			if (type.equalsIgnoreCase(ApplicationConstants.MASTER_DATA)) {
				FdUserMaster dfums = (FdUserMaster) obj;
				// model.setFdUserTmpId(dfumsTemp.getFdUserTmpId());
				model.setFdUserMasterId(dfums.getFdUserMasterId());
				model.setFdUserName(dfums.getFdUserName());
				model.setFdUserFirstName(dfums.getFdUserFirstName());
				model.setFdUserLastName(dfums.getFdUserLastName());
				model.setCreatedBy(dfums.getCreatedBy());
				model.setCreatedDate(dfums.getCreatedDate());
				model.setModifiedBy(dfums.getModifiedBy());
				model.setModifiedDate(dfums.getModifiedDate());
				model.setVerifiedBy(dfums.getVerifiedBy());
				model.setVerifiedDate(dfums.getVerifiedDate());
				model.setUserStatus(dfums.getUserStatus());
				model.setSecurepassUserStatus(dfums.getSecurepassUserStatus());
			} else if (type.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)) {
				FdUserTmp dfums = (FdUserTmp) obj;
				model.setFdUserTmpId(dfums.getFdUserTmpId());
				model.setFdUserMasterId(dfums.getFdUserMasterId());
				model.setFdUserName(dfums.getFdUserName());
				model.setFdUserFirstName(dfums.getFdUserFirstName());
				model.setFdUserLastName(dfums.getFdUserLastName());
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
				model.setSecurepassUserStatus(dfums.getSecurepassUserStatus());
			}

		} catch (Exception e) {
			throw new SBLException("Object Conversion error.Please try again.");
		}

		return model;
	}

	@Override
	public String toString() {
		return "FdUserModel{" + "fdUserTmpId=" + fdUserTmpId + ", fdUserMasterId=" + fdUserMasterId + ", fdUserName="
				+ fdUserName + ", fdUserFirstName=" + fdUserFirstName + ", fdUserLastName=" + fdUserLastName
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + ", verifiedBy=" + verifiedBy + ", verifiedDate=" + verifiedDate
				+ ", actionType=" + actionType + ", recStatus=" + recStatus + ", userStatus=" + userStatus
				+ ", authComment=" + authComment + ", securepassUserStatus=" + securepassUserStatus + '}';
	}

}
