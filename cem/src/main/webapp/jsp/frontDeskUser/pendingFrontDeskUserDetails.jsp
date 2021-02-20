<%-- 
    Document   : pendingEmpDetails
    Created on : Dec 22, 2020, 11:08:45 AM
    Author     : sits_lahirupr
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url var="images" value="/resources/images"/>

<%@page import="com.company.common.APPUtills"%>
<%@page import="com.company.models.FdUserModel"%>
<%@page import="com.company.common.ApplicationConstants"%>
<%@ include file="../includes/include-initial-variables.jsp"%>
<%@ include file="../includes/include-notifications.jsp"%>  
<style> 
    input {
        width: 50%;
        height: 20px;
    }
    #detailsTable td {   
        padding-top: 5px;
        padding-bottom: 5px;
        padding-left: 5px;
        padding-right: 5px;
        text-align: left;
        font-size: 12px;
    }
</style>
<%
    FdUserModel model = objManager.get("userModel") != null ? (FdUserModel) objManager.get("userModel") : new FdUserModel();
    FdUserModel masterModel = objManager.get("userModelFromMaster") != null ? (FdUserModel) objManager.get("userModelFromMaster") : new FdUserModel();
%>

<div style="text-align: center;background-image: url('${images}/deailsBackGround.jpg');">
    <img style="box-shadow: 0 0 2px 2px #331400; border-radius: 50%;margin-right: 2px" alt="plusMark" width="200" height="200" src="${images}/frontDesk.JPG"/>
</div>
<hr>
<table class="table table-bordered" id="detailsTable">
    <thead style="background-color: #331400;color: #ffffff">
        <tr>
            <th style="width: 40%"></th>
            <th style="width: 30%">NEW VALUE</th>
            <th style="width: 30%">OLD VALUE</th>
        </tr>
    </thead>
    <tbody> 
        <tr>                            
            <td <%if (APPUtills.isEqual(model.getActionType(), ApplicationConstants.ACTION_TYPE_MODIFY) && !APPUtills.isEqual(model.getFdUserName(),masterModel.getFdUserName())) {%>style="background-color: #ffad99"<%}%>>&nbsp;<b>USER ID</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=APPUtills.getString(model.getFdUserName())%></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY) ? masterModel.getFdUserName() : ""%></td>
        </tr>
        <tr>                            
            <td <%if (APPUtills.isEqual(model.getActionType(),ApplicationConstants.ACTION_TYPE_MODIFY) && !APPUtills.isEqual(model.getFdUserFirstName(),masterModel.getFdUserFirstName())) {%>style="background-color: #ffad99"<%}%>>&nbsp;<b>USER FIRST NAME</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=APPUtills.getString(model.getFdUserFirstName())%></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY) ? masterModel.getFdUserFirstName() : ""%></td>
        </tr>
        <tr>                            
            <td <%if (APPUtills.isEqual(model.getActionType(),ApplicationConstants.ACTION_TYPE_MODIFY) && !APPUtills.isEqual(model.getFdUserLastName(),masterModel.getFdUserLastName())) {%>style="background-color: #ffad99"<%}%>>&nbsp;<b>USER LAST NAME</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=APPUtills.getString(model.getFdUserLastName())%></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY) ? masterModel.getFdUserLastName() : ""%></td>
        </tr>
        <tr>                            
            <td <%if (APPUtills.isEqual(model.getActionType(),ApplicationConstants.ACTION_TYPE_MODIFY) && !APPUtills.isEqual(model.getUserStatus(),masterModel.getUserStatus())) {%>style="background-color: #ffad99"<%}%>>&nbsp;<b>USER FIRST STATUS</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=ApplicationConstants.statusDesc(model.getUserStatus())%></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY) ? ApplicationConstants.statusDesc(masterModel.getUserStatus()) : ""%></td>
        </tr>
        
        <tr>                            
            <td  colspan="3" style="background-color:#331400;opacity: 0.5" ></td>
        </tr>

        <tr>                            
            <td><b>ACTION TYPE</b></td>
            <td colspan="2" style="color: #004080"><%=ApplicationConstants.actionTypeDesc(model.getActionType())%></td>
        </tr>
        <tr>                            
            <td><b>RECORD STATUS</b></td>
            <td colspan="2" style="color: #004080"><%=ApplicationConstants.recordStatusDesc(model.getRecStatus())%></td>
        </tr>
        <tr>                            
            <td><b>ACTION TAKEN BY</b></td>
            <td colspan="2" style="color: #004080"><%=model.getModifiedBy() == null ? "" : model.getModifiedBy()%></td>
        </tr>
        <tr>                            
            <td><b>VERIFIED TAKEN DATE</b></td>
            <td colspan="2" style="color: #004080"><%=model.getModifiedDate() == null ? "" : APPUtills.formatDate(model.getModifiedDate(), APPUtills.DATE_TIME_SHORT_FORMAT)%></td>
        </tr>
        <tr>                            
            <td><b>VERIFIED BY</b></td>
            <td colspan="2" style="color: #004080"><%=model.getModifiedBy() == null ? "" : model.getVerifiedBy()%></td>
        </tr>
        <tr>                            
            <td><b>VERIFIED DATE</b></td>
            <td colspan="2" style="color: #004080"><%=model.getVerifiedDate()== null ? "" : APPUtills.formatDate(model.getVerifiedDate(), APPUtills.DATE_TIME_SHORT_FORMAT)%></td>
        </tr>
        <tr style="background-color: #ffebe6">                            
            <td><b>REJECT REASON</b></td>
            <td colspan="2" style="color: #004080"><%if(model.getRecStatus().equalsIgnoreCase(ApplicationConstants.RECORD_STATUS_REJECT)){%><b><i><%=model.getAuthComment() == null ? "" : model.getAuthComment()%></i></b><%}%></td>
        </tr>
    </tbody>
</table>
