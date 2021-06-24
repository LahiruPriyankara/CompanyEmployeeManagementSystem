<%-- 
    Author     : lahiru priyankara
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url var="images" value="/resources/images"/>


<%@page import="com.company.common.APPUtills"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.company.models.CompanyUserModel"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.company.common.ApplicationConstants"%>
<%@ include file="../includes/include-initial-variables.jsp"%>
<%@ include file="../includes/include-notifications.jsp"%> 

<style>
    input.depEmpDetails[type=text] {
        width: 100%;
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }
    input.depEmpDetailsCheckBox[type=checkbox]{
        /*opacity: 0;
        position: absolute;
        left: -9999px;
        z-index: 12;*/
        width: 18px;
        height: 18px;
        cursor: pointer;
    }

</style>

<%
    // ObjectManager objManager1 = new ObjectManager(session);
    //String sSession1 = session.getId();
    // String sURLPrefix1 = request.getContextPath() + "/ActionController?";
    Map<Integer, CompanyUserModel> dvmUsersMatchWithUPM = objManager.get("cemUsersMatchWithCOM_SERVICE") != null ? (Map<Integer, CompanyUserModel>) objManager.get("cemUsersMatchWithCOM_SERVICE") : new HashMap();
    List<CompanyUserModel> dvmUsersMatchWithUPMList = new ArrayList<>(dvmUsersMatchWithUPM.values());
    Map<String, CompanyUserModel> dvmUsersMisMatchWithUPM = objManager.get("cemUsersMisMatchWithCOM_SERVICE") != null ? (HashMap) objManager.get("cemUsersMisMatchWithCOM_SERVICE") : new HashMap();
    List<CompanyUserModel> dvmUsersMisMatchWithUPMList = new ArrayList<>(dvmUsersMisMatchWithUPM.values());

    List<String> grades = ApplicationConstants.USER_GRADES;
    List<String> gender = ApplicationConstants.USER_GENDER;

    boolean isAuthorizer = true;
%>



<div>
    <ul class="nav nav-tabs"> 
        <li>
            <a id="existingTab" href="#" onclick="clickOnTab('<%=sURLPrefix%>/CompanyDepEmployee/ExistingEmp')"><b><i>Existing</i></b></a>
        </li>
        <li class="active">
            <a id="pendingTab" href="#" onclick="clickOnTab('<%=sURLPrefix%>/CompanyDepEmployee/PendingEmp')"><b><i>Pending</i></b></a>
        </li>
    </ul>
</div>
<form id="CompanyUser" method="post" class="form-horizontal" action="<%= sURLPrefix%>/CompanyEmployee/SaveBulkEmp" enctype="multipart/form-data">
    <div class="row">
        <div class="col-sm-12 col-md-12">
            <div id="tableDiv"> 
                <div style="overflow-y: scroll;overflow-x: scroll; height:700px;border-radius: 5px">
                    <br>
                    <table class="table table-bordered table-hover" id="mainTable">
                        <thead>
                            <tr>
                                <th><!--checkBox--></th>
                                <th></th>
                                <th></th>
                                <th>ID</th>
                                <th>IMAGE</th>
                                <th>FIRST NAME</th>
                                <th>MIDDLE NAME</th>
                                <th>LAST NAME</th>
                                <th>GENDER</th>
                                <th>GRADE</th>                            
                                <th>SOL</th>
                                <th>DEPARTMENT</th>
                                <th>DESIGNATION</th>
                                <th>FLOOR</th>
                                <th>JOB</th>
                                <th>DEP EXTENTION</th>
                                <th>MOBILE</th>
                                <th>EXTENTION</th>
                                <th>EMAIL</th>
                                <th>NEXT PERSON NAME</th>
                                <th>NEXT PERSON MOBILE</th>
                                <th>NEXT PERSON EXTENTION</th>
                                <th>REMARKS</th>
                                <th>RECORD STATUS</th>
                                <th>STATUS</th>
                                <th style="display: none">Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%for (CompanyUserModel model : dvmUsersMatchWithUPMList) {
                                    //System.out.println("IMAGE : " + model.getBase64Image());
                                    if (model.getRecStatus().equalsIgnoreCase(ApplicationConstants.RECORD_STATUS_PENDING)) {
                            %>
                            <tr class="<%=model.getCompanyUserEmpId()%>">
                                <td  style="background-color:#3399ff">
                                    <%if(userData.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_COMMON_AUTHORIZER) || userData.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_BRANCH_AUTHORIZER)){ %>
                                    <input type="checkbox" class="depEmpDetailsCheckBox" onclick="clickOnCheckBox(this, '<%=model.getCompanyUserEmpId()%>', '<%=model.getRecStatus()%>', '')"/>
                                    <%}%>
                                </td>
                                <td style="text-align: right">
                                    <span class="glyphicon glyphicon-list-alt detailsIcon" style="color: #3399ff;" onclick="getPendingCompanyUserDetails('<%=model.getCompanyUserEmpId()%>','<%=model.getCompanyUserDivId() %>', '<%=sURLPrefix%>/CompanyEmployee/PendingEmpDetails')"></span>
                                </td>
                                <td>
                                    <%if (APPUtills.isThisStringValid(model.getBase64Image())) {%>
                                    <img src="data:image/jpg;base64,<%=model.getBase64Image()%>" style="border-radius: 50%;margin-right: 2px" alt="prof pic" width="25" height="25"/>
                                    <%} else {%>
                                    <img src="${images}/userDefault.jpg" style="border-radius: 50%;margin-right: 2px" alt="prof pic" width="25" height="25"/>
                                    <%}%>
                                </td>
                                <td><%=APPUtills.getString(model.getCompanyUserEmpId())%></td>
                                <td>-</td>
                                <td style="text-align: left"><%=APPUtills.getString(model.getCompanyUserFirstName())%></td>
                                <td style="text-align: left"><%=APPUtills.getString(model.getCompanyUserMiddleName())%></td>
                                <td style="text-align: left"><%=APPUtills.getString(model.getCompanyUserLastName())%></td>
                                <td style="text-align: left"><%=ApplicationConstants.genderTypeDesc(model.getCompanyUserGender())%></td>
                                <td style="text-align: left"><%=ApplicationConstants.genderTypeDesc(model.getCompanyUserGrade())%></td>                                
                                <td><%=APPUtills.getString(model.getCompanyUserDivId())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserDepName())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserDestination())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserFloor())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserJobDesc())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserDepExten())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserOfficeMobile())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserOfficeExten())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserOfficeEmail())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserContactPersonName())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserContactPersonMobile())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserContactPersonExten())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserRemarks())%></td>
                                <td><%=ApplicationConstants.recordStatusDesc(model.getRecStatus())%></td>
                                <td><%=ApplicationConstants.statusDesc(model.getUserStatus())%></td>

                                <td style="display: none"><%=model.getCompanyUserEmpId()%>-<%=model.getCompanyUserFirstName()%>-<%=model.getCompanyUserMiddleName()%>-<%=model.getCompanyUserLastName()%>-<%=model.getCompanyUserGender()%>-<%=model.getCompanyUserGrade()%>-<%=model.getCompanyUserDestination()%>-<%=model.getCompanyUserFloor()%>-<%=model.getCompanyUserJobDesc()%>-<%=model.getCompanyUserDepExten()%>-<%=model.getCompanyUserOfficeMobile()%>-<%=model.getCompanyUserOfficeExten()%>-<%=model.getCompanyUserOfficeEmail()%>-<%=model.getCompanyUserContactPersonName()%>-<%=model.getCompanyUserContactPersonMobile()%>-<%=model.getCompanyUserContactPersonExten()%>-<%=model.getUserStatus()%></td>
                            </tr> 
                            <%} else {%>
                            <tr class="<%=model.getCompanyUserEmpId()%>">
                                <td  style="background-color: #e62e00">
                                    <%if(userData.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_COMMON_ENTERER) || userData.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_BRANCH_ENTERER)){ %>
                					<input type="checkbox" class="depEmpDetailsCheckBox" onclick="clickOnCheckBox(this, '<%=model.getCompanyUserEmpId()%>', '<%=model.getRecStatus()%>', '')"/>
                                    <%}%>
                                </td>
                                <td style="text-align: right">
                                    <span class="glyphicon glyphicon-list-alt detailsIcon" style="color: #3399ff;" onclick="getPendingCompanyUserDetails('<%=model.getCompanyUserEmpId()%>','<%=model.getCompanyUserDivId() %>', '<%=sURLPrefix%>/CompanyEmployee/PendingEmpDetails')"></span>
                                </td>
                                <td>
                                    <%if (APPUtills.isThisStringValid(model.getBase64Image())) {%>
                                    <img src="data:image/jpg;base64,<%=model.getBase64Image()%>" style="border-radius: 50%;margin-right: 2px" alt="prof pic" width="25" height="25"/>
                                    <%} else {%>
                                    <img src="${images}/userDefault.jpg" style="border-radius: 50%;margin-right: 2px" alt="prof pic" width="25" height="25"/>
                                    <%}%>
                                </td>
                                <td><%=APPUtills.getString(model.getCompanyUserEmpId())%></td>
                                <td><input type="file" id="profPic<%=model.getCompanyUserEmpId()%>" name="profPic<%=model.getCompanyUserEmpId()%>" class="<%=model.getCompanyUserEmpId()%>" style="border-color: red" disabled="true"></td>
                                <td style="text-align: left"><%=APPUtills.getString(model.getCompanyUserFirstName())%></td>
                                <td><input type="text" id="mName<%=model.getCompanyUserEmpId()%>" name="mName<%=model.getCompanyUserEmpId()%>" class="<%=model.getCompanyUserEmpId()%>" value="<%=APPUtills.getString(model.getCompanyUserMiddleName())%>" readonly="true"></td>
                                <td style="text-align: left"><%=APPUtills.getString(model.getCompanyUserLastName())%></td>
                                <td>
                                    <select id="gender<%=model.getCompanyUserEmpId()%>" name="gender<%=model.getCompanyUserEmpId()%>" style="border-color: #ff9999;height: 25px" class="<%=model.getCompanyUserEmpId()%>" disabled="true">
                                        <%for (String g : gender) {%>
                                        <option value="<%=g%>" <%=model.getCompanyUserGender().equalsIgnoreCase(g) ? "selected" : ""%>> <%=ApplicationConstants.genderTypeDesc(g)%></option>
                                        <%}%>
                                    </select>

                                </td>
                                <td>
                                    <select id="grade<%=model.getCompanyUserEmpId()%>" name="grade<%=model.getCompanyUserEmpId()%>" style="border-color: #ff9999;height: 25px" class="<%=model.getCompanyUserEmpId()%>" disabled="true">
                                        <%for (String g : grades) {%>
                                        <option value="<%=g%>" <%=model.getCompanyUserGrade().equalsIgnoreCase(g) ? "selected" : ""%>> <%=g%></option>
                                        <%}%>
                                    </select>
                                </td>
                                <td><%=APPUtills.getString(model.getCompanyUserDivId())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserDepName())%></td>
                                <td><input type="text" id="designation<%=model.getCompanyUserEmpId()%>" name="designation<%=model.getCompanyUserEmpId()%>" class="<%=model.getCompanyUserEmpId()%>" value="<%=APPUtills.getString(model.getCompanyUserDestination())%>" readonly="true"></td>                            
                                <td><input type="text" id="floor<%=model.getCompanyUserEmpId()%>" name="floor<%=model.getCompanyUserEmpId()%>" class="<%=model.getCompanyUserEmpId()%>" value="<%=APPUtills.getString(model.getCompanyUserFloor())%>" readonly="true" style="border-color: #ff9999"></td>
                                <td><input type="text" id="jobdesc<%=model.getCompanyUserEmpId()%>" name="jobdesc<%=model.getCompanyUserEmpId()%>" class="<%=model.getCompanyUserEmpId()%>" value="<%=APPUtills.getString(model.getCompanyUserJobDesc())%>" readonly="true"></td>                            
                                <td><input type="text" id="depExtention<%=model.getCompanyUserEmpId()%>" name="depExtention<%=model.getCompanyUserEmpId()%>" class="<%=model.getCompanyUserEmpId()%>" value="<%=APPUtills.getString(model.getCompanyUserDepExten())%>" readonly="true" style="border-color: #ff9999"></td>
                                <td><input type="text" id="empMobile<%=model.getCompanyUserEmpId()%>" name="empMobile<%=model.getCompanyUserEmpId()%>" class="<%=model.getCompanyUserEmpId()%>" value="<%=APPUtills.getString(model.getCompanyUserOfficeMobile())%>" readonly="true"></td>
                                <td><input type="text" id="empExtention<%=model.getCompanyUserEmpId()%>" name="empExtention<%=model.getCompanyUserEmpId()%>" class="<%=model.getCompanyUserEmpId()%>" value="<%=APPUtills.getString(model.getCompanyUserOfficeExten())%>" readonly="true" style="border-color: #ff9999"></td>
                                <td><input type="text" id="empEmail<%=model.getCompanyUserEmpId()%>" name="empEmail<%=model.getCompanyUserEmpId()%>" class="<%=model.getCompanyUserEmpId()%>" value="<%=APPUtills.getString(model.getCompanyUserOfficeEmail())%>" readonly="true" style="border-color: #ff9999"></td>
                                <td><input type="text" id="nextPersonName<%=model.getCompanyUserEmpId()%>" name="nextPersonName<%=model.getCompanyUserEmpId()%>" class="<%=model.getCompanyUserEmpId()%>" value="<%=APPUtills.getString(model.getCompanyUserContactPersonName())%>" readonly="true" style="border-color: #ff9999"></td>
                                <td><input type="text" id="nextPersonMobile<%=model.getCompanyUserEmpId()%>" name="nextPersonMobile<%=model.getCompanyUserEmpId()%>" class="<%=model.getCompanyUserEmpId()%>" value="<%=APPUtills.getString(model.getCompanyUserContactPersonMobile())%>" readonly="true"></td>
                                <td><input type="text" id="nextPersonExtention<%=model.getCompanyUserEmpId()%>" name="nextPersonExtention<%=model.getCompanyUserEmpId()%>" class="<%=model.getCompanyUserEmpId()%>" value="<%=APPUtills.getString(model.getCompanyUserContactPersonExten())%>" readonly="true" style="border-color: #ff9999"></td>
                                <td><input type="text" id="remark<%=model.getCompanyUserEmpId()%>" name="remark<%=model.getCompanyUserEmpId()%>" class="<%=model.getCompanyUserEmpId()%>" value="<%=APPUtills.getString(model.getCompanyUserRemarks())%>" readonly="true"></td>
                                <td><%=ApplicationConstants.recordStatusDesc(model.getRecStatus())%></td>
                                <td>
                                    <%if (model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_NEW)) {%>
                                    <select id="status<%=model.getCompanyUserEmpId()%>" name="status<%=model.getCompanyUserEmpId()%>" style="border-color: #ff9999;height: 25px" class="<%=model.getCompanyUserEmpId()%>" disabled="true">
                                        <option value="<%=ApplicationConstants.STATUS_ACTIVE%>" selected><%=ApplicationConstants.statusDesc(ApplicationConstants.STATUS_ACTIVE)%></option>
                                    </select>
                                    <%} else {%>
                                    <select id="status<%=model.getCompanyUserEmpId()%>" name="status<%=model.getCompanyUserEmpId()%>" style="border-color: #ff9999;height: 25px" class="<%=model.getCompanyUserEmpId()%>" disabled="true">
                                        <option value="<%=ApplicationConstants.STATUS_ACTIVE%>" <%=model.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_ACTIVE) ? "selected" : ""%>><%=ApplicationConstants.statusDesc(ApplicationConstants.STATUS_ACTIVE)%></option>
                                        <option value="<%=ApplicationConstants.STATUS_INACTIVE%>" <%=model.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_INACTIVE) ? "selected" : ""%>><%=ApplicationConstants.statusDesc(ApplicationConstants.STATUS_INACTIVE)%></option>
                                    </select>
                                    <%}%>
                                </td>
                                <td style="display: none"><%=model.getCompanyUserEmpId()%>-<%=model.getCompanyUserFirstName()%>-<%=model.getCompanyUserMiddleName()%>-<%=model.getCompanyUserLastName()%>-<%=model.getCompanyUserGender()%>-<%=model.getCompanyUserGrade()%>-<%=model.getCompanyUserDestination()%>-<%=model.getCompanyUserFloor()%>-<%=model.getCompanyUserJobDesc()%>-<%=model.getCompanyUserDepExten()%>-<%=model.getCompanyUserOfficeMobile()%>-<%=model.getCompanyUserOfficeExten()%>-<%=model.getCompanyUserOfficeEmail()%>-<%=model.getCompanyUserContactPersonName()%>-<%=model.getCompanyUserContactPersonMobile()%>-<%=model.getCompanyUserContactPersonExten()%>-<%=model.getUserStatus()%></td>
                            </tr> 
                            <%}
                                }%>
                            <%for (CompanyUserModel model : dvmUsersMisMatchWithUPMList) {%> 
                            <tr class="<%=model.getCompanyUserEmpId()%>">
                                <td style="background-color: #e68a00">
                                    <%if(userData.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_COMMON_ENTERER) || userData.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_BRANCH_ENTERER)){ %>
                                    <input type="checkbox" class="depEmpDetailsCheckBox" onclick="clickOnCheckBox(this, '<%=model.getCompanyUserEmpId()%>', '<%=ApplicationConstants.RECORD_STATUS_REJECT%>', '0')"/>
                                    <%}%>
                                </td>
                                <td style="text-align: right">
                                    <span class="glyphicon glyphicon-list-alt detailsIcon" style="color: #3399ff;" onclick="getPendingCompanyUserDetails('<%=model.getCompanyUserEmpId()%>','<%=model.getCompanyUserDivId() %>', '<%=sURLPrefix%>/CompanyEmployee/PendingEmpDetails')"></span>
                                </td>
                                <td>
                                    <%if (APPUtills.isThisStringValid(model.getBase64Image())) {%>
                                    <img src="data:image/jpg;base64,<%=model.getBase64Image()%>" style="border-radius: 50%;margin-right: 2px" alt="prof pic" width="25" height="25"/>
                                    <%} else {%>
                                    <img src="${images}/userDefault.jpg" style="border-radius: 50%;margin-right: 2px" alt="prof pic" width="25" height="25"/>
                                    <%}%>
                                </td>
                                <td><%=APPUtills.getString(model.getCompanyUserEmpId())%></td>
                                <td>-</td>
                                <td style="text-align: left"><%=APPUtills.getString(model.getCompanyUserFirstName())%></td>
                                <td style="text-align: left"><%=APPUtills.getString(model.getCompanyUserMiddleName())%></td>
                                <td style="text-align: left"><%=APPUtills.getString(model.getCompanyUserLastName())%></td>
                                <td style="text-align: left"><%=ApplicationConstants.genderTypeDesc(model.getCompanyUserGender())%></td>
                                <td style="text-align: left"><%=ApplicationConstants.genderTypeDesc(model.getCompanyUserGrade())%></td>                                
                                <td><%=APPUtills.getString(model.getCompanyUserDivId())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserDepName())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserDestination())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserFloor())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserJobDesc())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserDepExten())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserOfficeMobile())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserOfficeExten())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserOfficeEmail())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserContactPersonName())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserContactPersonMobile())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserContactPersonExten())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserRemarks())%></td>
                                <td><%=ApplicationConstants.recordStatusDesc(model.getRecStatus())%></td>
                                <td><%=ApplicationConstants.statusDesc(model.getUserStatus())%></td>

                                <td style="display: none"><%=model.getCompanyUserEmpId()%>-<%=model.getCompanyUserFirstName()%>-<%=model.getCompanyUserMiddleName()%>-<%=model.getCompanyUserLastName()%>-<%=model.getCompanyUserGender()%>-<%=model.getCompanyUserGrade()%>-<%=model.getCompanyUserDestination()%>-<%=model.getCompanyUserFloor()%>-<%=model.getCompanyUserJobDesc()%>-<%=model.getCompanyUserDepExten()%>-<%=model.getCompanyUserOfficeMobile()%>-<%=model.getCompanyUserOfficeExten()%>-<%=model.getCompanyUserOfficeEmail()%>-<%=model.getCompanyUserContactPersonName()%>-<%=model.getCompanyUserContactPersonMobile()%>-<%=model.getCompanyUserContactPersonExten()%>-<%=model.getUserStatus()%></td>
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
        <div class="col-sm-12 col-md-4"> </div>
        <div class="col-sm-12 col-md-8">
            <div style="float: right;">   
                <textarea id="rejectReason" name="rejectReason" rows="2" cols="100" style="resize: none;margin-right: 20px" placeholder="Reason for reject..."></textarea>
            </div>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-sm-12 col-md-4">
            <span class="glyphicon glyphicon-list-alt" style="color: #3399ff;margin-right: 5px"> </span> <i style="color: #3399ff;margin-right: 5px">View Details</i> |
            
            <span style='font-size:20px;color: #3399ff'>&#9724;</span><span style="color: #3399ff;padding-right: 12px;padding-left: 12px;"><i>Pending</i></span>|

            <span style='font-size:20px;color: #e62e00'>&#9724;</span><span style="color: #e62e00;margin-right: 12px;padding-left: 12px;"><i>Reject</i></span>|

            <span style='font-size:20px;color: #e68a00'>&#9724;</span><span style="color: #e68a00;padding-right: 12px;padding-left: 12px;"><i>Mismatch with UPM</i></span> 

        </div>
        <div class="col-sm-12 col-md-8">
            <div style="float: right;"> 
               <%if(userData.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_COMMON_ENTERER) || userData.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_BRANCH_ENTERER)){ %>
                <input id="deleteBtn" type="button" class="btn btn-danger" onclick="deleteCompanyUser('<%=sURLPrefix%>/CompanyEmployee/RemoveEmp')" value="Delete" style="width: 120px"/>
              	<input id="saveBtnn" type="button" class="btn btn-success" onclick="SaveEmp('<%=sURLPrefix%>/CompanyEmployee/SaveEmp')" value="Save" style="width: 120px"/>
                <%} %>
                
                <%if(userData.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_COMMON_AUTHORIZER) || userData.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_BRANCH_AUTHORIZER)){ %>
                <input id="rejectBtn" type="button" class="btn btn-warning" onclick="rejectCompanyUser('<%=sURLPrefix%>/CompanyEmployee/RejectEmp')" value="Reject" style="width: 120px"/>                
              	<input id="verifyBtn" type="button" class="btn btn-success" onclick="verifyCompanyUser('<%=sURLPrefix%>/CompanyEmployee/VerifyEmp')" value="Verify" style="width: 120px"/> 
				 <%} %>              
        
            </div>
        </div>
    </div>
</form>
<br>
<script>
    $(document).ready(function () {
        $(".titleLink").css('background', '#1a8cff');
        $("#depEmp").css('background', '#006080');

        $('#mainTable').DataTable({
            //"sPaginationType": "full_numbers",
            //"bJQueryUI": true,
            "dom": '<lf<t>ip>',
            //"lengthMenu": [[15, 50, 100, -1], [15, 50, 100, "All"]]
        });
    });

    var map_verify = new Map();
    var map_reject = new Map();
    var saveIds = "";
    var verifyIds = "";
    var rejectIds = "";
    var map_delete = new Map();

    function clickOnCheckBox(val, clName, recStatusType, type) {

        /*$("#deleteBtn").prop("disabled", true);
         $("#rejectBtn").prop("disabled", true);
         $("#saveBtn").prop("disabled", true);
         $("#verifyBtn").prop("disabled", true);*/

        if (val.checked) {
            if (recStatusType === '<%=ApplicationConstants.RECORD_STATUS_PENDING%>') {
                map_verify.set(clName, clName);
            } else if (recStatusType === '<%=ApplicationConstants.RECORD_STATUS_REJECT%>') {
                map_reject.set(clName, clName);
                if (type === '0') {
                    map_delete.set(clName, clName);
                }
            }
            $("." + clName + "").closest('tr').css('background-color', '#ffd1b3')
            $("." + clName + "").prop("readonly", false);
            $("#status" + clName + "").prop("disabled", false);
            $("#grade" + clName + "").prop("disabled", false);
            $("#gender" + clName + "").prop("disabled", false);
            $("#profPic" + clName + "").prop("disabled", false);

            if (map_delete.size !== 0) {
                $("#saveBtnn").prop("disabled", true);
            } else {
                $("#saveBtnn").prop("disabled", false);
            }

        } else {
            if (recStatusType === '<%=ApplicationConstants.RECORD_STATUS_PENDING%>') {
                map_verify.delete(clName);
            } else if (recStatusType === '<%=ApplicationConstants.RECORD_STATUS_REJECT%>' || recStatusType === '0') {
                map_reject.delete(clName);
                if (type === '0') {
                    map_delete.delete(clName, clName);
                }
            }
            $("." + clName + "").closest('tr').css('background-color', '')
            $("." + clName + "").prop("readonly", true);
            $("#status" + clName + "").prop("disabled", true);
            $("#grade" + clName + "").prop("disabled", true);
            $("#gender" + clName + "").prop("disabled", true);
            $("#profPic" + clName + "").prop("disabled", true);

            if (map_delete.size !== 0) {
                $("#saveBtnn").prop("disabled", true);
            } else {
                $("#saveBtnn").prop("disabled", false);
            }
        }

    }

    function SaveEmp() {
        var size = map_reject.size;
        if (size === 0) {
            alert("Please select rows to save..! ");
        } else {
            var saveKey = Array.from(map_reject.keys());
            for (var i = 0; i < saveKey.length; i++) {
                if (i === 0) {
                    saveIds = saveKey[i];
                } else {
                    saveIds = saveIds + "," + saveKey[i];
                }
            }
            //console.log("saveIds : " + saveIds);
            var form = document.getElementById('CompanyUser');
            //var input1 = document.createElement('input');

            input1 = document.createElement('input');
            input1.setAttribute("type", "hidden");
            input1.setAttribute("id", "newIds");
            input1.setAttribute("name", "newIds");
            input1.setAttribute("value", "");
            form.appendChild(input1);

            var input2 = document.createElement('input');
            input2 = document.createElement('input');
            input2.setAttribute("type", "hidden");
            input2.setAttribute("id", "modifyIds");
            input2.setAttribute("name", "modifyIds");
            input2.setAttribute("value", saveIds);
            form.appendChild(input2);

            hidePage();
            $("#CompanyUser").submit();
            //showPage(); 
        }


    }

    function verifyCompanyUser(uri) {
        var size = map_verify.size;
        var rejectReason = $("#rejectReason").val();
        console.log(rejectReason)
        if (size === 0) {
            alert("Please select rows to verify..! ");
        } else {
            if (uri !== "") {

                var keys = Array.from(map_verify.keys());
                for (var i = 0; i < keys.length; i++) {
                    if (i === 0) {
                        verifyIds = keys[i];
                    } else {
                        verifyIds = verifyIds + "," + keys[i];
                    }
                }

                hidePage();
                $.post(uri, {ids: verifyIds, rejectReason: rejectReason}, function (data) {
                    $('#pageBody').empty();
                    $('#pageBody').append(data);
                    showPage();
                });
            }
        }

    }

    function rejectCompanyUser(uri) {
        var size = map_verify.size;
        var rejectReason = $("#rejectReason").val();
        console.log(rejectReason)
        if (size === 0) {
            alert("Please select rows to reject..! ");
        } else if (rejectReason === "") {
            alert("Comment is required to reject..! ");
        } else {
            if (uri !== "") {

                var keys = Array.from(map_verify.keys());
                for (var i = 0; i < keys.length; i++) {
                    if (i === 0) {
                        rejectIds = keys[i];
                    } else {
                        rejectIds = rejectIds + "," + keys[i];
                    }
                }

                hidePage();
                $.post(uri, {ids: rejectIds, rejectReason: rejectReason}, function (data) {
                    $('#pageBody').empty();
                    $('#pageBody').append(data);
                    showPage();
                });
            }
        }

    }

    function deleteCompanyUser(uri) {
        var size = map_reject.size;
        console.log(rejectReason)
        if (size === 0) {
            alert("Please select rows to delete..! ");
        } else {
            if (uri !== "") {

                var keys = Array.from(map_reject.keys());
                for (var i = 0; i < keys.length; i++) {
                    if (i === 0) {
                        rejectIds = keys[i];
                    } else {
                        rejectIds = rejectIds + "," + keys[i];
                    }
                }
                if (confirm("Do you want to remove..! \nWARNING: Record will be permanently deleted.")) {
                    hidePage();
                    $.post(uri, {ids: rejectIds}, function (data) {
                        $('#pageBody').empty();
                        $('#pageBody').append(data);
                        showPage();
                    });
                }

            }
        }

    }

    function getPendingCompanyUserDetails(id,depId ,uri) {
        console.log(uri);
        if (uri !== "") {
            hidePage();
            $.post(uri, {id: id,depId:depId}, function (data) {
                $('#modelDivData').empty();
                $('#modelDivData').append(data);
                showPage();
            });
            document.getElementById("btnForModel").click();
        }        
    }

</script>
