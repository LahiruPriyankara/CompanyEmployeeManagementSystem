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
</style>
<%
    FdUserModel model = objManager.get("userModel") != null ? (FdUserModel) objManager.get("userModel") : new FdUserModel();    
    FdUserModel masterModel = objManager.get("userModelFromMaster") != null ? (FdUserModel) objManager.get("userModelFromMaster") : new FdUserModel();
%>

<div style="text-align: center;background-image: url('${images}/deailsBackGround.jpg');">
    <img style="box-shadow: 0 0 2px 2px #331400; border-radius: 50%;margin-right: 2px" alt="plusMark" width="200" height="200" src="${images}/userDefault.jpg"/>
</div>
<hr>
<table class="table table-bordered">
    <thead style="background-color: #331400;color: #ffffff">
        <tr>
            <th style="width: 40%"></th>
            <th style="width: 30%">NEW VALUE</th>
            <th style="width: 30%">OLD VALUE</th>
        </tr>
    </thead>
    <tbody> 
        <tr>                            
            <td>&nbsp;<b>USER ID</b></td>
            <td <%if(model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY) && !model.getFdUserName().equalsIgnoreCase(masterModel.getFdUserName())){%>style="color: #ff704d"<%}%>>&nbsp;&nbsp;&nbsp;<%=model.getFdUserName()%></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY)?masterModel.getFdUserName():""%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>USER FIRST NAME</b></td>
            <td <%if(model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY) && !model.getFdUserFirstName().equalsIgnoreCase(masterModel.getFdUserFirstName())){%>style="color: #ff704d"<%}%>>&nbsp;&nbsp;&nbsp;<%=model.getFdUserFirstName()%></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY)?masterModel.getFdUserFirstName():""%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>USER LAST NAME</b></td>
            <td <%if(model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY) && !model.getFdUserLastName().equalsIgnoreCase(masterModel.getFdUserLastName())){%>style="color: #ff704d"<%}%>>&nbsp;&nbsp;&nbsp;<%=model.getFdUserLastName()%></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY)?masterModel.getFdUserLastName():""%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>USER FIRST STATUS</b></td>
            <td <%if(model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY) && !model.getUserStatus().equalsIgnoreCase(masterModel.getUserStatus())){%>style="color: #ff704d"<%}%>>&nbsp;&nbsp;&nbsp;<%=ApplicationConstants.statusDesc(model.getUserStatus())%></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY)?ApplicationConstants.statusDesc(masterModel.getUserStatus()):""%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>ACTION TYPE</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=ApplicationConstants.actionTypeDesc(model.getActionType())%></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY)?"-":""%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>USER CREATED BY</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getCreatedBy()%></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY)?masterModel.getCreatedBy():""%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>USER CREATED DATE</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=APPUtills.formatDate(model.getCreatedDate(), "dd/MM/yyyy")%></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY)?APPUtills.formatDate(masterModel.getCreatedDate(), "dd/MM/yyyy"):""%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>USER VERIFIED BY</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getVerifiedBy()%></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY)?masterModel.getVerifiedBy():""%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>USER VERIFIED DATE</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=APPUtills.formatDate(model.getVerifiedDate(), "dd/MM/yyyy")%></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY)?APPUtills.formatDate(masterModel.getVerifiedDate(), "dd/MM/yyyy"):""%></td>
        </tr>
        <tr style="background-color: #ffebe6">                            
            <td>&nbsp;<b>REJECT REASON</b></td>
            <td colspan="2" style="color: #004080">&nbsp;&nbsp;&nbsp;<b><i><%=model.getAuthComment()==null?"":model.getAuthComment()%></i></b></td>
        </tr>

    </tbody>
</table>
