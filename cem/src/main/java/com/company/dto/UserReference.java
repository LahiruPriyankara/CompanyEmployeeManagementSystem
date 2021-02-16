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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sits_lahirupr
 */
@Entity
@Table(name = "CUD_USER_REFERENCE")
public class UserReference implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "REFERENCE_ID")
    private int referenceId;
    @Column(name = "USER_ID")
    private int userId;
    @Column(name = "LOGGING_COUNT")
    private Integer loggingCount;
    @Column(name = "FIRST_LOGIN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstLoginDate;
    @Column(name = "LAST_LOGIN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;

    public UserReference() {
    }

    public UserReference(int referenceId) {
        this.referenceId = referenceId;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getLoggingCount() {
        return loggingCount;
    }

    public void setLoggingCount(Integer loggingCount) {
        this.loggingCount = loggingCount;
    }

    public Date getFirstLoginDate() {
        return firstLoginDate;
    }

    public void setFirstLoginDate(Date firstLoginDate) {
        this.firstLoginDate = firstLoginDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
/*
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (referenceId != null ? referenceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserReference)) {
            return false;
        }
        UserReference other = (UserReference) object;
        if ((this.referenceId == null && other.referenceId != null) || (this.referenceId != null && !this.referenceId.equals(other.referenceId))) {
            return false;
        }
        return true;
    }
*/
	@Override
	public String toString() {
		return "UserReference [referenceId=" + referenceId + ", userId=" + userId + ", loggingCount=" + loggingCount
				+ ", firstLoginDate=" + firstLoginDate + ", lastLoginDate=" + lastLoginDate + "]";
	}

    
    
}
