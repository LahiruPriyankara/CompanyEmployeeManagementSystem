<%-- 
    Document   : existingEmpDetails
    Created on : Dec 22, 2020, 11:09:18 AM
    Author     : sits_lahirupr
--%>

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
%>

<div style="text-align: center;background-image: url('${pageContext.request.contextPath}/ui/images/deailsBackGround.jpg');">
    <img style="box-shadow: 0 0 2px 2px #331400; border-radius: 50%;margin-right: 2px" alt="plusMark" width="200" height="200" src="${pageContext.request.contextPath}/ui/images/userDefault.jpg"/>
</div>
<hr>
<table class="table table-bordered">
    <thead style="background-color: #331400;color: #ffffff">
        <tr>
            <th style="width: 40%"></th>
            <th style="width: 60%">VALUE</th>
        </tr>
    </thead>
    <tbody>
        <tr>                            
            <td>&nbsp;<b>USER ID</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getFdUserName()%></td>
        </tr>
        <tr>                            
            <td>&nbsp;<b>USER FIRST NAME</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getFdUserFirstName()%></td>
        </tr><tr>                            
            <td>&nbsp;<b>USER LAST NAME</b></td>
            <td>&nbsp;&nbsp;&nbsp;<%=model.getFdUserLastName()%></td>
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
