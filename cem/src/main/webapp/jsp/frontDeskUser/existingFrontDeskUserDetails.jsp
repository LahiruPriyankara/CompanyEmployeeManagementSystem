<%-- 
    Document   : existingEmpDetails
    Created on : Dec 22, 2020, 11:09:18 AM
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
%>

<div style="text-align: center;background-image: url('${images}/deailsBackGround.JPG');">
    <img style="box-shadow: 0 0 2px 2px #331400; border-radius: 50%;margin-right: 2px" alt="plusMark" width="200" height="200" src="${images}/frontDesk.JPG"/>
</div>
<hr>
<table class="table table-bordered" id="detailsTable">
    <thead style="background-color: #331400;color: #ffffff">
        <tr>
            <th style="width: 40%"></th>
            <th style="width: 60%">VALUE</th>
        </tr>
    </thead>
    <tbody>
        <tr>                            
            <td>&nbsp;<b>USER ID</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=APPUtills.getString(model.getFdUserName())%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>USER FIRST NAME</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=APPUtills.getString(model.getFdUserFirstName())%></td>
        </tr><tr>                            
            <td>&nbsp;<b>USER LAST NAME</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=APPUtills.getString(model.getFdUserLastName())%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>USER FIRST STATUS</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=ApplicationConstants.statusDesc(model.getUserStatus())%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>USER CREATED BY</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getCreatedBy()%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>USER CREATED DATE</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=APPUtills.formatDate(model.getCreatedDate(), "dd/MM/yyyy")%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>USER VERIFIED BY</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getVerifiedBy()%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>USER VERIFIED DATE</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=APPUtills.formatDate(model.getVerifiedDate(), "dd/MM/yyyy")%></td>
        </tr>

    </tbody>
</table>
