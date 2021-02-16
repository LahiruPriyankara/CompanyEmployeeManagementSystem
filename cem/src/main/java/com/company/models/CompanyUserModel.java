/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.models;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;

import com.company.common.ApplicationConstants;
import com.company.common.SBLException;
import com.company.dto.CompanyUserMaster;
import com.company.dto.CompanyUserTmp;

/**
 *
 * @author sits_lahirupr
 */
public class CompanyUserModel implements Cloneable {

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public CompanyUserModel() {
    }

    private int companyUserTmpId;
    private int companyUserMasterId;

    private String companyUserEmpId = "";
    private Serializable companyUserProfImg;
    private String companyUserFirstName = "";
    private String companyUserMiddleName = "";
    private String companyUserLastName = "";
    private String companyUserGender = "";
    private String companyUserGrade = "";
    private String companyUserDestination = "";
    private String companyUserDivId = "";
    private String companyUserDepName = "";
    private String companyUserFloor = "";
    private String companyUserJobDesc = "";
    private String companyUserDepExten = "";
    private String companyUserOfficeMobile = "";
    private String companyUserOfficeExten = "";
    private String companyUserOfficeEmail = "";
    private String companyUserContactPersonName = "";
    private String companyUserContactPersonMobile = "";
    private String companyUserContactPersonExten = "";
    private String companyUserRemarks = "";
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

    private String base64Image;

    public String getBase64Image() throws SQLException, IOException {
        try {
            if (companyUserProfImg == null) {
                return null;
            }
            byte[] imageBytes = (byte[]) companyUserProfImg;
            this.base64Image = Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            return null;
        }
        return this.base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public int getCompanyUserTmpId() {
        return companyUserTmpId;
    }

    public void setCompanyUserTmpId(int companyUserTmpId) {
        this.companyUserTmpId = companyUserTmpId;
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

        if (!type.equalsIgnoreCase(ApplicationConstants.TEMP_DATA) && !type.equalsIgnoreCase(ApplicationConstants.MASTER_DATA)) {
            throw new SBLException("Invalid table type recived.type : " + type);
        }

        if (type.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)) {
            CompanyUserTmp dbut = new CompanyUserTmp();

            dbut.setCompanyUserTmpId(companyUserTmpId);
            dbut.setCompanyUserMasterId(companyUserMasterId);
            dbut.setCompanyUserEmpId(companyUserEmpId);
            dbut.setCompanyUserProfImg(companyUserProfImg);
            dbut.setCompanyUserFirstName(companyUserFirstName);
            dbut.setCompanyUserMiddleName(companyUserMiddleName);
            dbut.setCompanyUserLastName(companyUserLastName);
            dbut.setCompanyUserGender(companyUserGender);
            dbut.setCompanyUserGrade(companyUserGrade);
            dbut.setCompanyUserDestination(companyUserDestination);
            dbut.setCompanyUserDivId(companyUserDivId);
            dbut.setCompanyUserDepName(companyUserDepName);
            dbut.setCompanyUserFloor(companyUserFloor);
            dbut.setCompanyUserJobDesc(companyUserJobDesc);
            dbut.setCompanyUserDepExten(companyUserDepExten);
            dbut.setCompanyUserOfficeMobile(companyUserOfficeMobile);
            dbut.setCompanyUserOfficeExten(companyUserOfficeExten);
            dbut.setCompanyUserOfficeEmail(companyUserOfficeEmail);
            dbut.setCompanyUserContactPersonName(companyUserContactPersonName);
            dbut.setCompanyUserContactPersonMobile(companyUserContactPersonMobile);
            dbut.setCompanyUserContactPersonExten(companyUserContactPersonExten);
            dbut.setCompanyUserRemarks(companyUserRemarks);
            dbut.setCreatedBy(createdBy);
            dbut.setCreatedDate(createdDate);
            dbut.setModifiedBy(modifiedBy);
            dbut.setModifiedDate(modifiedDate);
            dbut.setVerifiedBy(verifiedBy);
            dbut.setVerifiedDate(verifiedDate);
            dbut.setActionType(actionType);
            dbut.setRecStatus(recStatus);
            dbut.setUserStatus(userStatus);
            dbut.setAuthComment(authComment);

            return dbut;
        } else {
            CompanyUserMaster dbut = new CompanyUserMaster();

            dbut.setCompanyUserMasterId(companyUserMasterId);
            dbut.setCompanyUserEmpId(companyUserEmpId);
            dbut.setCompanyUserProfImg(companyUserProfImg);
            dbut.setCompanyUserFirstName(companyUserFirstName);
            dbut.setCompanyUserMiddleName(companyUserMiddleName);
            dbut.setCompanyUserLastName(companyUserLastName);
            dbut.setCompanyUserGender(companyUserGender);
            dbut.setCompanyUserGrade(companyUserGrade);
            dbut.setCompanyUserDestination(companyUserDestination);
            dbut.setCompanyUserDivId(companyUserDivId);
            dbut.setCompanyUserDepName(companyUserDepName);
            dbut.setCompanyUserFloor(companyUserFloor);
            dbut.setCompanyUserJobDesc(companyUserJobDesc);
            dbut.setCompanyUserDepExten(companyUserDepExten);
            dbut.setCompanyUserOfficeMobile(companyUserOfficeMobile);
            dbut.setCompanyUserOfficeExten(companyUserOfficeExten);
            dbut.setCompanyUserOfficeEmail(companyUserOfficeEmail);
            dbut.setCompanyUserContactPersonName(companyUserContactPersonName);
            dbut.setCompanyUserContactPersonMobile(companyUserContactPersonMobile);
            dbut.setCompanyUserContactPersonExten(companyUserContactPersonExten);
            dbut.setCompanyUserRemarks(companyUserRemarks);
            dbut.setCreatedBy(createdBy);
            dbut.setCreatedDate(createdDate);
            dbut.setModifiedBy(modifiedBy);
            dbut.setModifiedDate(modifiedDate);
            dbut.setVerifiedBy(verifiedBy);
            dbut.setVerifiedDate(verifiedDate);
            dbut.setUserStatus(userStatus);

            return dbut;
        }
    }

    public static CompanyUserModel objectToModel(Object obj, String type) throws Exception {
        CompanyUserModel model = new CompanyUserModel();
        try {
            if (obj == null) {
                throw new SBLException("Object Conversion error.Please try again.");
            } else if (!type.equalsIgnoreCase(ApplicationConstants.TEMP_DATA) && !type.equalsIgnoreCase(ApplicationConstants.MASTER_DATA)) {
                throw new SBLException("Invalid table type recived.type : " + type);
            }
            if (type.equalsIgnoreCase(ApplicationConstants.TEMP_DATA)) {
                CompanyUserTmp dbut = (CompanyUserTmp) obj;

                model.setCompanyUserMasterId(dbut.getCompanyUserMasterId());
                model.setCompanyUserEmpId(dbut.getCompanyUserEmpId());
                model.setCompanyUserProfImg(dbut.getCompanyUserProfImg());
                model.setCompanyUserFirstName(dbut.getCompanyUserFirstName());
                model.setCompanyUserMiddleName(dbut.getCompanyUserMiddleName());
                model.setCompanyUserLastName(dbut.getCompanyUserLastName());
                model.setCompanyUserGender(dbut.getCompanyUserGender());
                model.setCompanyUserGrade(dbut.getCompanyUserGrade());
                model.setCompanyUserDestination(dbut.getCompanyUserDestination());
                model.setCompanyUserDivId(dbut.getCompanyUserDivId());
                model.setCompanyUserDepName(dbut.getCompanyUserDepName());
                model.setCompanyUserFloor(dbut.getCompanyUserFloor());
                model.setCompanyUserJobDesc(dbut.getCompanyUserJobDesc());
                model.setCompanyUserDepExten(dbut.getCompanyUserDepExten());
                model.setCompanyUserOfficeMobile(dbut.getCompanyUserOfficeMobile());
                model.setCompanyUserOfficeExten(dbut.getCompanyUserOfficeExten());
                model.setCompanyUserOfficeEmail(dbut.getCompanyUserOfficeEmail());
                model.setCompanyUserContactPersonName(dbut.getCompanyUserContactPersonName());
                model.setCompanyUserContactPersonMobile(dbut.getCompanyUserContactPersonMobile());
                model.setCompanyUserContactPersonExten(dbut.getCompanyUserContactPersonExten());
                model.setCompanyUserRemarks(dbut.getCompanyUserRemarks());
                model.setCreatedBy(dbut.getCreatedBy());
                model.setCreatedDate(dbut.getCreatedDate());
                model.setModifiedBy(dbut.getModifiedBy());
                model.setModifiedDate(dbut.getModifiedDate());
                model.setVerifiedBy(dbut.getVerifiedBy());
                model.setVerifiedDate(dbut.getVerifiedDate());
                model.setUserStatus(dbut.getUserStatus());

                model.setCompanyUserTmpId(dbut.getCompanyUserTmpId());
                model.setAuthComment(dbut.getAuthComment());
                model.setActionType(dbut.getActionType());
                model.setRecStatus(dbut.getRecStatus());

                return model;
            } else {
                CompanyUserMaster dbut = (CompanyUserMaster) obj;

                model.setCompanyUserMasterId(dbut.getCompanyUserMasterId());
                model.setCompanyUserEmpId(dbut.getCompanyUserEmpId());
                model.setCompanyUserProfImg(dbut.getCompanyUserProfImg());
                model.setCompanyUserFirstName(dbut.getCompanyUserFirstName());
                model.setCompanyUserMiddleName(dbut.getCompanyUserMiddleName());
                model.setCompanyUserLastName(dbut.getCompanyUserLastName());
                model.setCompanyUserGender(dbut.getCompanyUserGender());
                model.setCompanyUserGrade(dbut.getCompanyUserGrade());
                model.setCompanyUserDestination(dbut.getCompanyUserDestination());
                model.setCompanyUserDivId(dbut.getCompanyUserDivId());
                model.setCompanyUserDepName(dbut.getCompanyUserDepName());
                model.setCompanyUserFloor(dbut.getCompanyUserFloor());
                model.setCompanyUserJobDesc(dbut.getCompanyUserJobDesc());
                model.setCompanyUserDepExten(dbut.getCompanyUserDepExten());
                model.setCompanyUserOfficeMobile(dbut.getCompanyUserOfficeMobile());
                model.setCompanyUserOfficeExten(dbut.getCompanyUserOfficeExten());
                model.setCompanyUserOfficeEmail(dbut.getCompanyUserOfficeEmail());
                model.setCompanyUserContactPersonName(dbut.getCompanyUserContactPersonName());
                model.setCompanyUserContactPersonMobile(dbut.getCompanyUserContactPersonMobile());
                model.setCompanyUserContactPersonExten(dbut.getCompanyUserContactPersonExten());
                model.setCompanyUserRemarks(dbut.getCompanyUserRemarks());
                model.setCreatedBy(dbut.getCreatedBy());
                model.setCreatedDate(dbut.getCreatedDate());
                model.setModifiedBy(dbut.getModifiedBy());
                model.setModifiedDate(dbut.getModifiedDate());
                model.setVerifiedBy(dbut.getVerifiedBy());
                model.setVerifiedDate(dbut.getVerifiedDate());
                model.setUserStatus(dbut.getUserStatus());

                return model;
            }

        } catch (Exception e) {
            throw new SBLException("Object Conversion error.Please try again.");
        }
    }

    public static CompanyUserModel userDataToModel(UserData userData) throws Exception {
        CompanyUserModel model = new CompanyUserModel();
        try {
            model.setCompanyUserEmpId(userData.getAD_USER_ID());
            model.setCompanyUserFirstName(userData.getFIRST_NAME());
            model.setCompanyUserLastName(userData.getLAST_NAME());
            model.setCompanyUserDivId(userData.getDIV_CODE());
            model.setCompanyUserDepName(userData.getDIV_NAME());
            model.setCompanyUserGender("M");
            model.setCompanyUserGrade("TSA I");
            model.setUserStatus(ApplicationConstants.STATUS_ACTIVE);

        } catch (Exception e) {
            throw new SBLException("Object Conversion error.Please try again.");
        }
        return model;
    }

    @Override
    public String toString() {
        return "CompanyUserModel{" + "companyUserTmpId=" + companyUserTmpId + ", companyUserMasterId=" + companyUserMasterId + ", companyUserEmpId=" + companyUserEmpId + ", companyUserFirstName=" + companyUserFirstName + ", companyUserMiddleName=" + companyUserMiddleName + ", companyUserLastName=" + companyUserLastName + ", companyUserGender=" + companyUserGender + ", companyUserGrade=" + companyUserGrade + ", companyUserDestination=" + companyUserDestination + ", companyUserDivId=" + companyUserDivId + ", companyUserDepName=" + companyUserDepName + ", companyUserFloor=" + companyUserFloor + ", companyUserJobDesc=" + companyUserJobDesc + ", companyUserDepExten=" + companyUserDepExten + ", companyUserOfficeMobile=" + companyUserOfficeMobile + ", companyUserOfficeExten=" + companyUserOfficeExten + ", companyUserOfficeEmail=" + companyUserOfficeEmail + ", companyUserContactPersonName=" + companyUserContactPersonName + ", companyUserContactPersonMobile=" + companyUserContactPersonMobile + ", companyUserContactPersonExten=" + companyUserContactPersonExten + ", companyUserRemarks=" + companyUserRemarks + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", verifiedBy=" + verifiedBy + ", verifiedDate=" + verifiedDate + ", actionType=" + actionType + ", recStatus=" + recStatus + ", userStatus=" + userStatus + ", authComment=" + authComment +'}';
    }

}
