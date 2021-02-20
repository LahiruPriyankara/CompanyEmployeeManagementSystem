package com.company.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CUD_VISITOR_DATA")
public class VisitorData implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    
    @Column(name = "COMPANY_EMP_ID")
    private String companyEmpId;
    
    @Column(name = "VISITOR_PASS_ID")
    private String visitorPassId;
    
    @Column(name = "VISITOR_NIC_NUM")
    private String visitorNicNum;
    
    @Column(name = "CREATED_BY")
    private Integer createdBy;
    
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyEmpId() {
		return companyEmpId;
	}

	public void setCompanyEmpId(String companyEmpId) {
		this.companyEmpId = companyEmpId;
	}

	
	public String getVisitorPassId() {
		return visitorPassId;
	}

	public void setVisitorPassId(String visitorPassId) {
		this.visitorPassId = visitorPassId;
	}

	public String getVisitorNicNum() {
		return visitorNicNum;
	}

	public void setVisitorNicNum(String visitorNicNum) {
		this.visitorNicNum = visitorNicNum;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "VisitorData [id=" + id +" , companyEmpId="+companyEmpId+ ", visitorPassId=" + visitorPassId + ", visitorNicNum=" + visitorNicNum
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + "]";
	}
    
    
    
    

}
