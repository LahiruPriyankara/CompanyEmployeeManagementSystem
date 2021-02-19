<%-- 
    Document   : comnViewBankEmployeeDetails
    Created on : Jan 27, 2021, 4:51:22 PM
    Author     : sits_lahirupr
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url var="images" value="/resources/images"/>

<%@page import="com.company.common.APPUtills"%>
<%@page import="com.company.common.ApplicationConstants"%>
<%@page import="com.company.models.CompanyUserModel"%>
<%@ include file="../includes/include-initial-variables.jsp"%>
<%@ include file="../includes/include-notifications.jsp"%>  

<style> 
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
    CompanyUserModel modelFromDVM = objManager.get("modelFromCEM") != null ? (CompanyUserModel) objManager.get("modelFromCEM") : new CompanyUserModel();
    CompanyUserModel modelFromUPM = objManager.get("modelFromCOM_SERVICE") != null ? (CompanyUserModel) objManager.get("modelFromCOM_SERVICE") : new CompanyUserModel();
    //System.out.println("modelFromDVM.getBase64Image() : "+modelFromDVM.getBase64Image());
%>

<div style="text-align: center;background-image: url('${images}/deailsBackGround.JPG');">
    
    <%if (APPUtills.isThisStringValid(modelFromDVM.getBase64Image())) {%>
    <img src="data:image/png;base64,<%=modelFromDVM.getBase64Image()%>" style="box-shadow: 0 0 2px 2px #331400; border-radius: 50%;margin-right: 2px" alt="prof pic" width="200" height="200"/>
    <%} else {%>
    <img src="${images}/userDefault.jpg" style="box-shadow: 0 0 2px 2px #331400; border-radius: 50%;margin-right: 2px" alt="prof pic" width="200" height="200"/>
    <%}%>

</div>

<hr>
<table class="table-bordered table-hover" id="detailsTable">
    <thead>
        <tr>
            <th style="width: 30%"></th>
            <th style="width: 35%">DVM</th> 
            <th style="width: 35%">UPM</th> 
        </tr>
    </thead>
    <tbody> 
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(modelFromDVM.getCompanyUserEmpId(),modelFromUPM.getCompanyUserEmpId())) {%>background-color: #ffad99<%}%>"><b><b>EMP ID</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserEmpId())%></td>
            <td style='color: #3399ff'><%=APPUtills.getString(modelFromUPM.getCompanyUserEmpId())%></td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(modelFromDVM.getCompanyUserFirstName(),modelFromUPM.getCompanyUserFirstName())) {%>background-color: #ffad99<%}%>"><b>FIRST NAME</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserFirstName())%></td>
            <td style='color: #3399ff'><%=APPUtills.getString(modelFromUPM.getCompanyUserFirstName())%></td>
        </tr>
        <tr>                            
            <td><b>MIDDLE NAME</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserMiddleName())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(modelFromDVM.getCompanyUserLastName(),modelFromUPM.getCompanyUserLastName())) {%>background-color: #ffad99<%}%>"><b>LAST NAME</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserLastName())%></td>
            <td style='color: #3399ff'><%=APPUtills.getString(modelFromUPM.getCompanyUserLastName())%></td>
        </tr>
        <tr>                            
            <td><b>GENDER</b></td>
            <td><%=ApplicationConstants.genderTypeDesc(modelFromDVM.getCompanyUserGender())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>GRADE</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserGrade())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>DESIGNATION</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserDestination())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(modelFromDVM.getCompanyUserDivId(),modelFromUPM.getCompanyUserDivId())) {%>background-color: #ffad99<%}%>"><b>SOL ID</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserDivId())%></td>
            <td style='color: #3399ff'><%=APPUtills.getString(modelFromUPM.getCompanyUserDivId())%></td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(modelFromDVM.getCompanyUserDepName(),modelFromUPM.getCompanyUserDepName())) {%>background-color: #ffad99<%}%>"><b>DEP NAME</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserDepName())%></td>
            <td style='color: #3399ff'><%=APPUtills.getString(modelFromUPM.getCompanyUserDepName())%></td>
        </tr>
        <tr>                            
            <td><b>FLOOR</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserFloor())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>JOB</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserJobDesc())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>DEP EXTENTION</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserDepExten())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>MOBILE</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserOfficeMobile())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>EXTENTION</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserOfficeExten())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>EMAIL</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserOfficeEmail())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>CONT PERSION</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserContactPersonName())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>CONT PERSION MOBILE</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserContactPersonMobile())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>CONT PERSION EXTEN</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserContactPersonExten())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>REMARKS</b></td>
            <td><%=APPUtills.getString(modelFromDVM.getCompanyUserRemarks())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>STATUS</b></td>
            <td><%=ApplicationConstants.statusDesc(modelFromDVM.getUserStatus())%></td>
            <td></td>
        </tr>
    </tbody>
</table>

