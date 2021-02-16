
<%@page import="com.company.models.CompanyUserModel"%>
<%-- 
    Document   : pendingEmpDetails
    Created on : Dec 22, 2020, 11:08:45 AM
    Author     : sits_lahirupr
--%>

<%@page import="com.company.common.APPUtills"%>
<%@page import="com.company.models.FdUserModel"%>
<%@page import="com.company.common.ApplicationConstants"%>
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
    CompanyUserModel DVMmodelMaster = objManager.get("DVMmodelMaster") != null ? (CompanyUserModel) objManager.get("DVMmodelMaster") : new CompanyUserModel();
    CompanyUserModel DVMmodelTemp = objManager.get("DVMmodelTemp") != null ? (CompanyUserModel) objManager.get("DVMmodelTemp") : new CompanyUserModel();
    CompanyUserModel UPMmodel = objManager.get("UPMmodel") != null ? (CompanyUserModel) objManager.get("UPMmodel") : new CompanyUserModel();

    //System.out.println("DVMmodelMaster.getBase64Image() : "+DVMmodelMaster.getBase64Image());
    //System.out.println("DVMmodelMaster.getBase64Image() : "+DVMmodelMaster.getBase64Image()==null);
    //System.out.print(DVMmodelTemp.toString());

%>

<div style="text-align: center;background-image: url('${pageContext.request.contextPath}/ui/images/deailsBackGround.JPG');">
    <%if (APPUtills.isThisStringValid(DVMmodelMaster.getBase64Image())) {%>
    <img src="data:image/png;base64,<%=DVMmodelMaster.getBase64Image()%>" style="box-shadow: 0 0 2px 2px #331400; border-radius: 50%;margin-right: 2px" alt="prof pic" width="200" height="200"/>
    <%} else {%>
    <img src="${pageContext.request.contextPath}/ui/images/userDefault.jpg" style="box-shadow: 0 0 2px 2px #331400; border-radius: 50%;margin-right: 2px" alt="prof pic" width="200" height="200"/>
    <%}%>
</div>

<hr>
<span style='font-size:20px;color: #3399ff'>&#9724;</span><span style="color: #3399ff;padding-right: 12px;padding-left: 12px;">UPM value</span>
<table class="table-bordered table-hover" id="detailsTable">
    <thead>
        <tr>
            <th style="width: 30%"></th>
            <th style="width: 35%">NEW VALUE</th>
            <th style="width: 35%">OLD VALUE</th>            
        </tr>
    </thead>
    <tbody> 
        <%if (DVMmodelTemp.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_NEW)) {%>
        <tr>                            
            <td><b>EMP ID</b></td>
            <td>
                <%=DVMmodelTemp.getCompanyUserEmpId()%>
                <div style="float: right">
                    <span style="color: #006bb3;font-size: 10px"><i><%=APPUtills.getString(UPMmodel.getCompanyUserEmpId())%></i></span>
                </div>
            </td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>IMAGE</b></td>
            <td><img src="data:image/jpg;base64,<%=DVMmodelTemp.getBase64Image()%>" style="border-radius: 50%;margin-right: 2px" alt="prof pic" width="25" height="25"/></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>FIRST NAME</b></td>
            <td>
                <%=APPUtills.getString(DVMmodelTemp.getCompanyUserFirstName())%>
                <div style="float: right">
                    <span style="color: #006bb3;font-size: 10px"><i><%=APPUtills.getString(UPMmodel.getCompanyUserFirstName())%></i></span>
                </div>                
            </td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>MIDDLE NAME</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserMiddleName())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>LAST NAME</b></td>
            <td>
                <%=APPUtills.getString(DVMmodelTemp.getCompanyUserLastName())%>
                <div style="float: right">
                    <span style="color: #006bb3;font-size: 10px"><i><%=APPUtills.getString(UPMmodel.getCompanyUserLastName())%></i></span>
                </div> 
            </td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>GENDER</b></td>
            <td><%=ApplicationConstants.genderTypeDesc(DVMmodelTemp.getCompanyUserGender())%></td>
            <td></td>
        </tr>


        <tr>                            
            <td><b>GRADE</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserGrade())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>DESIGNATION</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserDestination())%></td>
            <td></td>
        </tr>

        <tr>                            
            <td><b>SOL ID</b></td>
            <td>
                <%=APPUtills.getString(DVMmodelTemp.getCompanyUserDivId()) %>
                <div style="float: right">
                    <span style="color: #006bb3;font-size: 10px<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserDivId(),UPMmodel.getCompanyUserDivId())) {%>;background-color: #ffcc00;<%}%>">
                        <i>&nbsp;
                            <%if(APPUtills.isThisStringValid(UPMmodel.getCompanyUserDivId())){%>
                            <%=UPMmodel.getCompanyUserDivId()%>
                            <%}else{%>
                            This user is in another department in UPM.
                            <%}%>
                            &nbsp;
                        </i></span>
                </div> 
            </td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>DEP NAME</b></td>
            <td>
                <%=APPUtills.getString(DVMmodelTemp.getCompanyUserDepName())%>
                <div style="float: right">
                    <span style="color: #006bb3;font-size: 10px"><i><%=APPUtills.getString(UPMmodel.getCompanyUserDepName())%></i></span>
                </div> 
            </td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>FLOOR</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserFloor())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>JOB</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserJobDesc())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>DEP EXTENTION</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserDepExten())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>MOBILE</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserOfficeMobile())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>EXTENTION</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserOfficeExten())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>EMAIL</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserOfficeEmail()) %></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>NEXT PERSON</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserContactPersonName())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>NEXT PERSON MOBILE</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserContactPersonMobile())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>NEXT PERSON EXTEN</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserContactPersonExten())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>REMARKS</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserRemarks())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>ACTION TYPE</b></td>
            <td><%=ApplicationConstants.actionTypeDesc(DVMmodelTemp.getActionType())%></td>
            <td></td>
        </tr>
        <tr>                            
            <td><b>STATUS</b></td>
            <td><%=ApplicationConstants.statusDesc(DVMmodelTemp.getUserStatus())%></td>
            <td></td>
        </tr>
        
        
        <%} else if (DVMmodelTemp.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_MODIFY)) {%>

        <tr>                            
            <td><b>EMP ID</b></td>
            <td>
                <%=DVMmodelTemp.getCompanyUserEmpId()%>
                <div style="float: right">
                    <span style="color: #006bb3;font-size: 10px"><i><%=APPUtills.getString(UPMmodel.getCompanyUserEmpId())%></i></span>
                </div>
            </td>
            <td><%=APPUtills.getString(DVMmodelMaster.getCompanyUserEmpId())%></td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getBase64Image(),DVMmodelMaster.getBase64Image())) {%>background-color: #ffad99<%}%>"><b>IMAGE</b></td>
            <td><img src="data:image/jpg;base64,<%=DVMmodelTemp.getBase64Image()%>" style="border-radius: 50%;margin-right: 2px" alt="prof pic" width="25" height="25"/></td>
            <td><img src="data:image/jpg;base64,<%=DVMmodelMaster.getBase64Image()%>" style="border-radius: 50%;margin-right: 2px" alt="prof pic" width="25" height="25"/></td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserFirstName(),DVMmodelMaster.getCompanyUserFirstName())) {%>background-color: #ffad99<%}%>"><b>FIRST NAME</b></td>
            <td>
                <%=APPUtills.getString(DVMmodelTemp.getCompanyUserFirstName())%>
                <div style="float: right">
                    <span style="color: #006bb3;font-size: 10px"><i><%=APPUtills.getString(UPMmodel.getCompanyUserFirstName())%></i></span>
                </div> 
            </td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserFirstName())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserMiddleName(),DVMmodelMaster.getCompanyUserMiddleName())) {%>background-color: #ffad99<%}%>"><b>MIDDLE NAME</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserMiddleName())%></td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserMiddleName())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserLastName(),DVMmodelMaster.getCompanyUserLastName())) {%>background-color: #ffad99<%}%>"><b>LAST NAME</b></td>
            <td>
                <%=APPUtills.getString(DVMmodelTemp.getCompanyUserLastName())%>
                <div style="float: right">
                    <span style="color: #006bb3;font-size: 10px"><i><%=APPUtills.getString(UPMmodel.getCompanyUserLastName())%></i></span>
                </div>
            </td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserLastName())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserEmpId(),DVMmodelMaster.getCompanyUserEmpId())) {%>background-color: #ffad99<%}%>"><b>GENDER</b></td>
            <td><%=ApplicationConstants.genderTypeDesc(DVMmodelTemp.getCompanyUserGender())%></td>
            <td>
                <%=ApplicationConstants.genderTypeDesc(DVMmodelMaster.getCompanyUserGender())%>
            </td>
        </tr>


        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserGrade(),DVMmodelMaster.getCompanyUserGrade())) {%>background-color: #ffad99<%}%>"><b>GRADE</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserGrade())%></td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserGrade()) %>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserDestination(),DVMmodelMaster.getCompanyUserDestination())) {%>background-color: #ffad99<%}%>"><b>DESIGNATION</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserDestination()) %></td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserDestination()) %>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserDivId(),DVMmodelMaster.getCompanyUserDivId())) {%>background-color: #ffad99<%}%>"><b>SOL ID</b></td>
            <td>
                <%=APPUtills.getString(DVMmodelTemp.getCompanyUserDivId())%>
                <div style="float: right">
                    <span style="color: #006bb3;font-size: 10px<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserDivId(), UPMmodel.getCompanyUserDivId())) {%>;background-color: #ffcc00;<%}%>"><i>&nbsp;<%=APPUtills.getString(UPMmodel.getCompanyUserDivId())%>&nbsp;</i></span>
                </div>
            </td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserDivId())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserDepName(),DVMmodelMaster.getCompanyUserDepName())) {%>background-color: #ffad99<%}%>"><b>DEP NAME</b></td>
            <td>
                <%=APPUtills.getString(DVMmodelTemp.getCompanyUserDepName())%>
                <div style="float: right">
                    <span style="color: #006bb3;font-size: 10px"><i><%=APPUtills.getString(UPMmodel.getCompanyUserDepName())%></i></span>
                </div>
            </td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserDepName())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserFloor(),DVMmodelMaster.getCompanyUserFloor())) {%>background-color: #ffad99<%}%>"><b>FLOOR</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserFloor())%></td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserFloor())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserJobDesc(),DVMmodelMaster.getCompanyUserJobDesc())) {%>background-color: #ffad99<%}%>"><b>JOB</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserJobDesc())%></td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserJobDesc())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserDepExten(),DVMmodelMaster.getCompanyUserDepExten())) {%>background-color: #ffad99<%}%>"><b>DEP EXTENTION</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserDepExten())%></td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserDepExten())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserOfficeMobile(),DVMmodelMaster.getCompanyUserOfficeMobile())) {%>background-color: #ffad99<%}%>"><b>MOBILE</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserOfficeMobile())%></td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserOfficeMobile())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserOfficeExten(),DVMmodelMaster.getCompanyUserOfficeExten())) {%>background-color: #ffad99<%}%>"><b>EXTENTION</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserOfficeExten())%></td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserOfficeExten())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserOfficeEmail(),DVMmodelMaster.getCompanyUserOfficeEmail())) {%>background-color: #ffad99<%}%>"><b>EMAIL</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserOfficeEmail())%></td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserOfficeEmail())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserContactPersonName(),DVMmodelMaster.getCompanyUserContactPersonName())) {%>background-color: #ffad99<%}%>"><b>NEXT PERSON</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserContactPersonName())%></td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserContactPersonName())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserContactPersonMobile(),DVMmodelMaster.getCompanyUserContactPersonMobile())) {%>background-color: #ffad99<%}%>"><b>NEXT PERSON MOBILE</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserContactPersonMobile())%></td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserContactPersonMobile())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserContactPersonExten(),DVMmodelMaster.getCompanyUserContactPersonExten())) {%>background-color: #ffad99<%}%>"><b>NEXT PERSON EXTEN</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserContactPersonExten())%></td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserContactPersonExten())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getCompanyUserRemarks(),DVMmodelMaster.getCompanyUserRemarks())) {%>background-color: #ffad99<%}%>"><b>REMARKS</b></td>
            <td><%=APPUtills.getString(DVMmodelTemp.getCompanyUserRemarks())%></td>
            <td>
                <%=APPUtills.getString(DVMmodelMaster.getCompanyUserRemarks())%>
            </td>
        </tr>
        <tr>                            
            <td style="<%if (!APPUtills.isEqual(DVMmodelTemp.getUserStatus(),DVMmodelMaster.getUserStatus())) {%>background-color: #ffad99<%}%>"><b>STATUS</b></td>
            <td><%=ApplicationConstants.statusDesc(DVMmodelTemp.getUserStatus())%></td>
            <td>
                <%=ApplicationConstants.statusDesc(DVMmodelMaster.getUserStatus())%>
            </td>
        </tr>
        <%}%>
        <tr>                            
            <td  colspan="3" style="background-color:#331400;opacity: 0.5" ></td>
        </tr>
        <tr>                            
            <td><b>ACTION TYPE</b></td>
            <td colspan="2" style="color: #004080"><%=ApplicationConstants.actionTypeDesc(DVMmodelTemp.getActionType())%></td>
        </tr>
        <tr>                            
            <td><b>RECORD STATUS</b></td>
            <td colspan="2" style="color: #004080"><%=ApplicationConstants.recordStatusDesc(DVMmodelTemp.getRecStatus())%></td>
        </tr>
        <tr>                            
            <td><b>ACTION TAKEN BY</b></td>
            <td colspan="2" style="color: #004080"><%=DVMmodelTemp.getModifiedBy() == null ? "" : DVMmodelTemp.getModifiedBy()%></td>
        </tr>
        <tr>                            
            <td><b>ACTION TAKEN DATE</b></td>
            <td colspan="2" style="color: #004080"><%=DVMmodelTemp.getModifiedDate() == null ? "" : APPUtills.formatDate(DVMmodelTemp.getModifiedDate(),APPUtills.DATE_TIME_SHORT_FORMAT)%></td>
        </tr>
         <tr>                            
            <td><b>VERIFIED BY</b></td>
            <td colspan="2" style="color: #004080"><%=DVMmodelTemp.getModifiedBy() == null ? "" : DVMmodelTemp.getVerifiedBy()%></td>
        </tr>
        <tr>                            
            <td><b>VERIFIED DATE</b></td>
            <td colspan="2" style="color: #004080"><%=DVMmodelTemp.getVerifiedDate()== null ? "" : APPUtills.formatDate(DVMmodelTemp.getVerifiedDate(), APPUtills.DATE_TIME_SHORT_FORMAT)%></td>
        </tr>
        <tr style="background-color: #ffebe6">                            
            <td><b>REJECT REASON</b></td>
            <td colspan="2" style="color: #004080"><%if(DVMmodelTemp.getRecStatus().equalsIgnoreCase(ApplicationConstants.RECORD_STATUS_REJECT)){%><b><i><%=DVMmodelTemp.getAuthComment() == null ? "" : DVMmodelTemp.getAuthComment()%></i></b><%}%></td>
        </tr>

    </tbody>
</table>
