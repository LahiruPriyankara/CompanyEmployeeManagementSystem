<%-- 
    Document   : allExistingEmp
    Created on : Dec 24, 2020, 8:31:24 AM
    Author     : sits_lahirupr
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url var="images" value="/resources/images"/>

<%@page import="com.company.common.APPUtills"%>
<%@page import="com.company.common.ApplicationConstants"%>
<%@page import="com.company.models.CompanyUserModel"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@ include file="../includes/include-initial-variables.jsp"%>
<%@ include file="../includes/include-notifications.jsp"%>

<%
    Map<String, CompanyUserModel> dvmUsers = objManager.get("companyEmployeesMap") != null ? (HashMap) objManager.get("companyEmployeesMap") : new HashMap();
    List<CompanyUserModel> dvmUsersList = new ArrayList<>(dvmUsers.values());
%>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div id="tableDiv"> 
            <div>
                <br>
                <table class="table table-bordered table-hover" id="mainTable">
                    <thead>
                        <tr>
                            <th  style="color: #331400"></th>
                            <th>IMAGE</th>
                            <th>ID</th>
                            <th>NAME</th> 
                            <th>DEPARTMENT</th>
                            <th>GRADE</th>
                            <th>DESIGNATION</th>
                            <th>FLOOR</th>
                            <th>EXTENTION</th>
                            <th>MOBILE</th>
                            <th>DEP EXTENTION</th>
                            <th style="display: none">EMAIL</th>
                            <th style="display: none">NEXT PERSON NAME</th>
                            <th style="display: none">NEXT PERSON MOBILE</th>
                            <th style="display: none">NEXT PERSON EXTENTION</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (CompanyUserModel model : dvmUsersList) {%>
                        <tr>
                            <td style="text-align: right">
                                <span class="glyphicon glyphicon-list-alt detailsIcon" style="color: #3399ff;" onclick="getDetails('<%=model.getCompanyUserEmpId()%>', '<%=sURLPrefix%>/CommonView/EmployeeDetailsForCommon')"></span>
                            </td>
                            <td>
                                <%if (APPUtills.isThisStringValid(model.getBase64Image())) {%>
                                <img src="data:image/jpg;base64,<%=model.getBase64Image()%>" style="border-radius: 50%;margin-right: 2px" alt="pro pic" width="25" height="25"/>
                                <%} else {%>
                                <img src="${images}/userDefault.jpg" style="border-radius: 50%;margin-right: 2px" alt="pro pic" width="25" height="25"/>
                                <%}%>                                
                            </td>
                            <td><%=APPUtills.getString(model.getCompanyUserEmpId())%></td>
                            <td style="text-align: left"><%=APPUtills.getString(model.getCompanyUserFirstName())%>&nbsp;<%=APPUtills.getString(model.getCompanyUserMiddleName())%>&nbsp;<%=APPUtills.getString(model.getCompanyUserLastName())%></td>
                            <td><%=APPUtills.getString(model.getCompanyUserDepName())%></td>
                            <td><%=APPUtills.getString(model.getCompanyUserGrade())%></td>
                            <td><%=APPUtills.getString(model.getCompanyUserDestination())%></td>
                            <td><%=APPUtills.getString(model.getCompanyUserFloor())%></td>
                            <td><%=APPUtills.getString(model.getCompanyUserOfficeExten())%></td>
                            <td><%=APPUtills.getString(model.getCompanyUserOfficeMobile())%></td>                            
                            <td><%=APPUtills.getString(model.getCompanyUserDepExten())%></td>
                            <td style="display: none"><%=APPUtills.getString(model.getCompanyUserOfficeEmail())%></td>
                            <td style="display: none"><%=APPUtills.getString(model.getCompanyUserContactPersonName())%></td>
                            <td style="display: none"><%=APPUtills.getString(model.getCompanyUserContactPersonMobile())%></td>
                            <td style="display: none"><%=APPUtills.getString(model.getCompanyUserContactPersonExten())%></td>
                        </tr>  
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<hr> 
<div class="row">
    <div class="col-sm-6 col-md-6">
        <span class="glyphicon glyphicon-list-alt" style="color: #3399ff;margin-right: 5px"> </span> <i style="color: #3399ff;margin-right: 5px">View Details</i> 
    </div>
    <div class="col-sm-6 col-md-6"></div>
</div>



<script>
    $(document).ready(function () {
        $('#mainTable').DataTable({
            //"sPaginationType": "full_numbers",
            //"bJQueryUI": true,
            "dom": '<lf<t>ip>',
            "lengthMenu": [[15, 50, 100, -1], [15, 50, 100, "All"]]
        });
        $(".titleLink").css('background', '#1a8cff');
        $("#allEmployee").css('background', '#006080');
    });
</script>