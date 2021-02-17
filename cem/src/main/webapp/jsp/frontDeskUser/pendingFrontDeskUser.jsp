<%-- 
    Document   : include-body
    Created on : Dec 21, 2020, 3:35:15 PM
    Author     : sits_lahirupr
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url var="images" value="/resources/images"/>


<%@page import="com.company.common.APPUtills"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.company.models.FdUserModel"%>
<%@page import="com.company.models.FdUserModel"%>
<%@page import="com.company.common.ApplicationConstants"%>
<%@ include file="../includes/include-initial-variables.jsp"%>
<%@ include file="../includes/include-notifications.jsp"%> 
<%
    Map<Integer, FdUserModel> dfumsMap = objManager.get("frontDeskUsers") != null ? (Map<Integer, FdUserModel>) objManager.get("frontDeskUsers") : new HashMap();
    List<FdUserModel> objList = new ArrayList<>(dfumsMap.values());
    boolean isAuthorizer = true;
%>
<div>
    <ul class="nav nav-tabs"> 
        <li>
            <a id="existingTab" href="#" onclick="clickOnTab('<%=sURLPrefix%>/FrontDeskUser/ExistingFrontDeskUsers')"><b><i>Existing</i></b></a>
        </li>
        <li class="active">
            <a id="pendingTab" href="#" onclick="clickOnTab('<%=sURLPrefix%>/FrontDeskUser.PendingFrontDeskUsers')"><b><i>Pending</i></b></a>
        </li>
    </ul>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div id="tableDiv"> 
            <div style="overflow-y: scroll;overflow-x: scroll; height:700px;border-radius: 5px">
                <br>
                <table class="table table-bordered table-hover" id="mainTable">
                    <thead>
                        <tr>
                            <th style="width: 120px"></th>
                            <th>ID</th>
                            <th>USER NAME</th>
                            <th>FIRST NAME</th>
                            <th>LAST NAME</th>
                            <th>ACTION TYPE</th>
                            <th>RECORD STATUS</th>
                            <th>SECUREPASS STATUS</th>
                            <th>STATUS</th>                           
                        </tr>
                    </thead>
                    <tbody>
                        <%for (FdUserModel model : objList) {
                                if (model.getSecurepassUserStatus().equalsIgnoreCase(ApplicationConstants.SECUREPASS_USER_SET_PASSWORD)) {
                        %>
                        <tr style="color: #4dc3ff;">
                            <td style="text-align: right">
                                <span class="glyphicon glyphicon-list-alt detailsIcon" style="color: #3399ff;" onclick="getDetails('<%=model.getFdUserTmpId()%>', '<%=sURLPrefix%>/FrontDeskUser/PendingFrontDeskUserDetails')"></span>

                                <%if (!APPUtills.isThisStringValid(model.getRecStatus())) {%>
                                <span class="glyphicon glyphicon-ok successIcon" style="color: #009933;" onclick="saveFdUserForPasswordVerification('<%=model.getFdUserTmpId()%>', '<%=sURLPrefix%>/FrontDeskUser/SaveNewFrontDeskUserPassword')"></span>
                                <%} else if (model.getRecStatus().equalsIgnoreCase(ApplicationConstants.RECORD_STATUS_PENDING)) {%>
                                <span class="glyphicon glyphicon-ok successIcon" style="color: #009933;" onclick="verifyFdUserForPasswordVerification('<%=model.getFdUserTmpId()%>', '<%=sURLPrefix%>/FrontDeskUser/VerifyFrontDeskUserPassword')"></span>
                                <%}%>

                            </td>
                            <td><%=model.getFdUserTmpId()%></td>
                            <td><%=APPUtills.getString(model.getFdUserName())%></td>
                            <td><%=APPUtills.getString(model.getFdUserFirstName())%></td>
                            <td><%=APPUtills.getString(model.getFdUserLastName())%></td>
                            <td><%=ApplicationConstants.actionTypeDesc(model.getActionType())%></td>
                            <td><%=ApplicationConstants.recordStatusDesc(model.getRecStatus())%></td>
                            <td><%=ApplicationConstants.securepassStatusDesc(model.getSecurepassUserStatus())%></td>    
                            <td><%=ApplicationConstants.statusDesc(model.getUserStatus())%></td>                            
                        </tr> 

                        <%} else if (model.getRecStatus().equalsIgnoreCase(ApplicationConstants.RECORD_STATUS_REJECT) && model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_NEW)) {%>
                        <tr style="color: #ff704d">
                            <td style="text-align: right">
                                <span class="glyphicon glyphicon-list-alt detailsIcon" style="color: #3399ff;" onclick="getDetails('<%=model.getFdUserTmpId()%>', '<%=sURLPrefix%>'/FrontDeskUser/PendingFrontDeskUserDetails)"></span>
                                <span class="glyphicon glyphicon-trash removeIcon" style="color: #e62e00;" onclick="removeChanges('<%=model.getFdUserTmpId()%>', '<%=sURLPrefix%>/FrontDeskUser/RemoveFrontDeskUser')"></span>
                                <span class="glyphicon glyphicon-ok successIcon" style="color: #009933;" onclick="saveFdUser('<%=model.getFdUserTmpId()%>', '<%=sURLPrefix %>/FrontDeskUser/SaveReModifiedFrontDeskUser')"></span>
                            </td>
                            <td><%=model.getFdUserTmpId()%></td>
                            <td><input type="text" class="fieldData" id="userName<%=model.getFdUserTmpId()%>" name="userName<%=model.getFdUserTmpId()%>" value="<%=APPUtills.getString(model.getFdUserName())%>" placeholder="USER NAME"></td>
                            <td><input type="text" class="fieldData" id="userFirstName<%=model.getFdUserTmpId()%>" name="userFirstName<%=model.getFdUserTmpId()%>" value="<%=APPUtills.getString(model.getFdUserFirstName())%>" placeholder="FIRST NAME"></td>
                            <td><input type="text" class="fieldData" id="userLastName<%=model.getFdUserTmpId()%>" name="userLastName<%=model.getFdUserTmpId()%>" value="<%=APPUtills.getString(model.getFdUserLastName())%>" placeholder="LAST NAME"></td>
                            <td><%=ApplicationConstants.actionTypeDesc(model.getActionType())%></td>
                            <td><%=ApplicationConstants.recordStatusDesc(model.getRecStatus())%></td>
                            <td><%=ApplicationConstants.securepassStatusDesc(model.getSecurepassUserStatus())%></td> 
                            <td><%=ApplicationConstants.statusDesc(model.getUserStatus())%></td>                            
                        </tr> 
                        <%} else {%>
                        <tr <%if (model.getRecStatus().equalsIgnoreCase(ApplicationConstants.RECORD_STATUS_REJECT)) {%>style="color: #ff704d;background-color: #ffc2b3"<%}%>>
                            <td style="text-align: right">
                                <span class="glyphicon glyphicon-list-alt detailsIcon" style="color: #3399ff;" onclick="getDetails('<%=model.getFdUserTmpId()%>', '<%=sURLPrefix%>/FrontDeskUser/PendingFrontDeskUserDetails')"></span>
                                <%if (model.getRecStatus().equalsIgnoreCase(ApplicationConstants.RECORD_STATUS_REJECT)) {%>
                                <span class="glyphicon glyphicon-trash removeIcon" style="color: #e62e00;" onclick="removeChanges('<%=model.getFdUserTmpId()%>', '<%=sURLPrefix%>/FrontDeskUser/RemoveFrontDeskUser')"></span>
                                <%} else {%>
                                <span class="glyphicon glyphicon-remove cancelIcon" style="color: #800000;" onclick="rejectChanges('<%=model.getFdUserTmpId()%>', '<%=sURLPrefix%>'/FrontDeskUser/RejectFrontDeskUser)"></span>
                                <span class="glyphicon glyphicon-ok successIcon" style="color: #009933;" onclick="verifyChanges('<%=model.getFdUserTmpId()%>', '<%=sURLPrefix%>/FrontDeskUser/VerifyFrontDeskUser')"></span>
                                <%}%>
                            </td>
                            <td><%=model.getFdUserTmpId()%></td>
                            <td><%=APPUtills.getString(model.getFdUserName())%></td>
                            <td><%=APPUtills.getString(model.getFdUserFirstName())%></td>
                            <td><%=APPUtills.getString(model.getFdUserLastName())%></td>
                            <td><%=ApplicationConstants.actionTypeDesc(model.getActionType())%></td>
                            <td><%=ApplicationConstants.recordStatusDesc(model.getRecStatus())%></td>
                            <td><%=ApplicationConstants.securepassStatusDesc(model.getSecurepassUserStatus())%></td> 
                            <td><%=ApplicationConstants.statusDesc(model.getUserStatus())%></td>                            
                        </tr> 
                        <%}
                            }%>
                    </tbody>
                </table>
            </div>
        </div>    
    </div>
</div>

<hr>

<div class="row">
    <div class="col-sm-6 col-md-6">
        <span class="glyphicon glyphicon-list-alt" style="color: #3399ff;margin-right: 5px">  </span>&nbsp;<i style="color: #3399ff;margin-right: 5px">View Details</i>|
        <span class="glyphicon glyphicon-remove" style="color: #800000;margin-right: 5px">  </span>&nbsp;<i style="color: #800000;margin-right: 5px">Reject</i>|
        <span class="glyphicon glyphicon-trash" style="color: #e62e00;margin-right: 5px">  </span>&nbsp;<i style="color: #e62e00;margin-right: 5px">Delete</i>|
        <span class="glyphicon glyphicon-ok " style="color: #009933;margin-right: 5px">  </span>&nbsp;<i style="color: #009933;margin-right: 5px">Verify</i>
    </div>
    <div class="col-sm-6 col-md-6">
        <div style="float: right;">
            <textarea id="rejectReason" name="rejectReason" rows="2" cols="100" style="resize: none;margin-right: 20px;width: 100%" placeholder="Reason for reject..."></textarea>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('#mainTable').DataTable({
            //"sPaginationType": "full_numbers",
            //"bJQueryUI": true,
            "dom": '<lf<t>ip>',
            //"lengthMenu": [[15, 50, 100, -1], [15, 50, 100, "All"]]
        });
        $(".titleLink").css('background', '#1a8cff');
        $("#fdUsr").css('background', '#006080');
    });

    function saveFdUser(id, uri) {//pending tab
        var id = id;//document.getElementById("tempId"+id).value;
        var userName = document.getElementById("userName" + id).value;
        var userFirstName = document.getElementById("userFirstName" + id).value;
        var userLastName = document.getElementById("userLastName" + id).value;

        if (userName === "" || userFirstName === "" || userLastName === "") {
            alert("Please fill all input field..! ");
        } else {
            if (uri !== "") {
                if (confirm("Do you want to save.")) {
                    hidePage();
                    $.post(uri, {id: id, userName: userName, userFirstName: userFirstName, userLastName: userLastName}, function (data) {
                        $('#pageBody').empty();
                        $('#pageBody').append(data);
                        showPage();
                    });
                }
            }
        }

    }

    function verifyFdUserForPasswordVerification(id, uri) {//pending tab
        var id = id;//document.getElementById("tempId"+id).value;
        if (id === "") {
            alert("Recived empty id..! ");
        } else {
            if (uri !== "") {
                if (confirm("Do you want to verify.")) {
                    hidePage();
                    $.post(uri, {id: id}, function (data) {
                        $('#pageBody').empty();
                        $('#pageBody').append(data);
                        showPage();
                    });
                }
            }
        }

    }
    function saveFdUserForPasswordVerification(id, uri) {//pending tab
        var id = id;//document.getElementById("tempId"+id).value;
        if (id === "") {
            alert("Recived empty id..! ");
        } else {
            if (uri !== "") {
                if (confirm("Do you want to save.")) {
                    hidePage();
                    $.post(uri, {id: id}, function (data) {
                        $('#pageBody').empty();
                        $('#pageBody').append(data);
                        showPage();
                    });
                }
            }
        }

    }

    function rejectChanges(id, uri) {//only for fd pending
        var reason = $("#rejectReason").val();
        if (reason === "") {
            alert("Comment is required..!");
        } else if (confirm("Do you want to send the record for re-modification..")) {
            if (uri !== "") {
                hidePage();
                $.post(uri, {id: id, reason: reason}, function (data) {
                    $('#pageBody').empty();
                    $('#pageBody').append(data);
                    showPage();
                });
            }
        }
        //alert("Are you want to remove..! " + id + " - " + type);
    }


    function verifyChanges(id, uri) {//only for fd pending
        var reason = $("#rejectReason").val();
        if (confirm("Do you want to verify..")) {
            if (uri !== "") {
                hidePage();
                $.post(uri, {id: id, reason: reason}, function (data) {
                    $('#pageBody').empty();
                    $('#pageBody').append(data);
                    showPage();
                });
            }
        }
    }


    function removeChanges(id, uri) {//only for fd pending
        if (confirm("Do you want to remove..! \nWARNING: Record will be permanently deleted.")) {
            if (uri !== "") {
                hidePage();
                $.post(uri, {id: id}, function (data) {
                    $('#pageBody').empty();
                    $('#pageBody').append(data);
                    showPage();
                });
            }
        }
    }


</script>
