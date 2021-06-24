<%-- 
    Author     : lahiru priyankara
--%>
<%@page import="com.company.models.CompanyUserModel"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url var="images" value="/resources/images"/>

<%@page import="com.company.common.APPUtills"%>
<%@page import="com.company.common.ApplicationConstants"%>

<%@ include file="../includes/include-initial-variables.jsp"%>
<%@ include file="../includes/include-notifications.jsp"%>  
<style> 
    input {
        width: 50%;
        height: 20px;
    }
</style>
<%CompanyUserModel model= objManager.get("companyUserModel") != null ? (CompanyUserModel) objManager.get("companyUserModel") : new CompanyUserModel();%>

<div style="text-align: center;background-image: url('${images}/deailsBackGround.jpg');">
    <%if (APPUtills.isThisStringValid(model.getBase64Image())) {%>
    <img src="data:image/png;base64,<%=model.getBase64Image()%>" style="box-shadow: 0 0 2px 2px #331400; border-radius: 50%;margin-right: 2px" alt="prof pic" width="200" height="200"/>
    <%} else {%>
    <img src="${images}/userDefault.jpg" style="box-shadow: 0 0 2px 2px #331400; border-radius: 50%;margin-right: 2px" alt="prof pic" width="200" height="200"/>
    <%}%>
</div>
<hr>
<table class="table-bordered table-hover" id="detailsTable">
    <thead>
        <tr>
            <th style="width: 30%">PROPERTY</th>
            <th style="width: 35%">VALUE</th> 
        </tr>
    </thead>
    <tbody> 
        <tr>                            
            <td><b><b>EMP ID</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserEmpId())%></td>
        </tr>
        <tr>                            
            <td><b>FIRST NAME</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserFirstName())%></td>
        </tr>
        <tr>                            
            <td><b>MIDDLE NAME</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserMiddleName())%></td>
        </tr>
        <tr>                            
            <td><b>LAST NAME</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserLastName())%></td>
        </tr>
        <tr>                            
            <td><b>GENDER</b></td>
            <td><%=ApplicationConstants.genderTypeDesc(model.getCompanyUserGender())%></td>
        </tr>
        <tr>                            
            <td><b>GRADE</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserGrade())%></td>
        </tr>
        <tr>                            
            <td><b>DESIGNATION</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserDestination())%></td>
        </tr>
        <tr>                            
            <td><b>SOL ID</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserDivId())%></td>
        </tr>
        <tr>                            
            <td><b>DEP NAME</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserDepName())%></td>
        </tr>
        <tr>                            
            <td><b>FLOOR</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserFloor())%></td>
        </tr>
        <tr>                            
            <td><b>JOB</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserJobDesc())%></td>
        </tr>
        <tr>                            
            <td><b>DEP EXTENTION</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserDepExten())%></td>
        </tr>
        <tr>                            
            <td><b>MOBILE</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserOfficeMobile())%></td>
        </tr>
        <tr>                            
            <td><b>EXTENTION</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserOfficeExten())%></td>
        </tr>
        <tr>                            
            <td><b>EMAIL</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserOfficeEmail())%></td>
        </tr>
        <tr>                            
            <td><b>CONT PERSION</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserContactPersonName())%></td>
        </tr>
        <tr>                            
            <td><b>CONT PERSION MOBILE</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserContactPersonMobile())%></td>
        </tr>
        <tr>                            
            <td><b>CONT PERSION EXTEN</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserContactPersonExten())%></td>
        </tr>
        <tr>                            
            <td><b>REMARKS</b></td>
            <td><%=APPUtills.getString(model.getCompanyUserRemarks())%></td>
        </tr>
        <tr>                            
            <td><b>STATUS</b></td>
            <td><%=ApplicationConstants.statusDesc(model.getUserStatus())%></td>
        </tr>
    </tbody>
</table>

