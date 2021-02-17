/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sits_lahirupr
 */
@Entity
@Table(name = "CUD_FD_USER_MASTER")
public class FdUserMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Column(name = "FD_USER_MASTER_ID")
    private int fdUserMasterId;
    @Size(max = 20)
    @Column(name = "FD_USER_NAME")
    private String fdUserName;
    @Size(max = 30)
    @Column(name = "FD_USER_FIRST_NAME")
    private String fdUserFirstName;
    @Size(max = 30)
    @Column(name = "FD_USER_LAST_NAME")
    private String fdUserLastName;
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
    @Size(max = 12)
    @Column(name = "SECUREPASS_USER_STATUS")
    private String securepassUserStatus;

    public FdUserMaster() {
    }

    public FdUserMaster(int fdUserMasterId) {
        this.fdUserMasterId = fdUserMasterId;
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

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getSecurepassUserStatus() {
        return securepassUserStatus;
    }

    public void setSecurepassUserStatus(String securepassUserStatus) {
        this.securepassUserStatus = securepassUserStatus;
    }
/*
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fdUserMasterId != null ? fdUserMasterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FdUserMaster)) {
            return false;
        }
        FdUserMaster other = (FdUserMaster) object;
        if ((this.fdUserMasterId == null && other.fdUserMasterId != null) || (this.fdUserMasterId != null && !this.fdUserMasterId.equals(other.fdUserMasterId))) {
            return false;
        }
        return true;
    }
*/
    @Override
    public String toString() {
        return "FdUserMaster{" + "fdUserMasterId=" + fdUserMasterId + ", fdUserName=" + fdUserName + ", fdUserFirstName=" + fdUserFirstName + ", fdUserLastName=" + fdUserLastName + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", verifiedBy=" + verifiedBy + ", verifiedDate=" + verifiedDate + ", userStatus=" + userStatus + ", securepassUserStatus=" + securepassUserStatus + '}';
    }

}
