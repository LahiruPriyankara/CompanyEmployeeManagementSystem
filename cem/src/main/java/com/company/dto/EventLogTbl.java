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
@Table(name = "CUD_EVENT_LOG_TBL")
public class EventLogTbl implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private int eventId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EVENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventDate;
    @Size(max = 50)
    @Column(name = "EVENT_TYPE")
    private String eventType;
    @Size(max = 100)
    @Column(name = "EVENT_ACTION")
    private String eventAction;
    @Size(max = 500)
    @Column(name = "EVENT_DESC")
    private String eventDesc;
    @Size(max = 50)
    @Column(name = "USER_ID")
    private String userId;
    @Size(max = 100)
    @Column(name = "SESSION_ID")
    private String sessionId;
    @Size(max = 16)
    @Column(name = "IP_ADDRESS")
    private String ipAddress;
    @Size(max = 500)
    @Column(name = "CLIENT_INFO")
    private String clientInfo;
    @Size(max = 2)
    @Column(name = "EVENT_STATUS")
    private String eventStatus;
    @Size(max = 4000)
    @Column(name = "OLD_VALUE")
    private String oldValue;
    @Size(max = 4000)
    @Column(name = "NEW_VALUE")
    private String newValue;

    public EventLogTbl() {
    }

    public EventLogTbl(int eventId) {
        this.eventId = eventId;
    }

    public EventLogTbl(int eventId, Date eventDate) {
        this.eventId = eventId;
        this.eventDate = eventDate;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventAction() {
        return eventAction;
    }

    public void setEventAction(String eventAction) {
        this.eventAction = eventAction;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
/*
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventLogTbl)) {
            return false;
        }
        EventLogTbl other = (EventLogTbl) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }
*/
    @Override
    public String toString() {
        return "EventLogTbl{" + "eventId=" + eventId + ", eventDate=" + eventDate + ", eventType=" + eventType + ", eventAction=" + eventAction + ", eventDesc=" + eventDesc + ", userId=" + userId + ", sessionId=" + sessionId + ", ipAddress=" + ipAddress + ", clientInfo=" + clientInfo + ", eventStatus=" + eventStatus + ", oldValue=" + oldValue + ", newValue=" + newValue + '}';
    }
    
    
    
}
