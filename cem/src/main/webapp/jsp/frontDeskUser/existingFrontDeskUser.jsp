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
    Map<Integer, FdUserModel> dfumsMap = objManager.get("frontDeskUsers") != null ? (Map<Integer, FdUserModel>) objManager.get("frontDeskUsers") : new HashMap();
    List<FdUserModel> objList = new ArrayList<>(dfumsMap.values());
%>

<div>
    <ul class="nav nav-tabs"> 
        <li class="active">
            <a id="existingTab" href="#" onclick="clickOnTab('<%=sURLPrefix%>/FrontDeskUser/ExistingFrontDeskUsers')"><b><i>Existing</i></b></a>
        </li>
        <li>
            <a id="pendingTab" href="#" onclick="clickOnTab('<%=sURLPrefix%>/FrontDeskUser/PendingFrontDeskUsers')"><b><i>Pending</i></b></a>
        </li>
    </ul>
</div>
<br>       
<div class="row">
    <div class="col-sm-8 col-md-8"></div>
    <div class="col-sm-2 col-md-2" style="float: right;"><button id="addNewBtn" type="button" class="btn btn-primary" onclick="addNewEmployee()">Add New</button></div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div id="grideDiv"> 
            <div style="overflow-y: scroll;overflow-x: scroll; height:700px;border-radius: 5px">
                <br>
                <table class="table-bordered table-hover" id="mainTable">
                    <thead>
                        <tr>
                            <th style="width: 120px"></th>
                            <th>ID</th>
                            <th>USER NAME</th>
                            <th>FIRST NAME</th>
                            <th>LAST NAME</th>
                            <th>SECUREPASS STATUS</th>
                            <th>STATUS</th>                        
                        </tr>
                    </thead>
                    <tbody>
                        <%for (FdUserModel model : objList) {%>
                        <tr <%if (model.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_INACTIVE)) {%>style="color: #ff704d"<%}%> >
                            <td style="text-align: right">
                                <span class="glyphicon glyphicon-list-alt detailsIcon" style="color: #3399ff;" onclick="getDetails('<%=model.getFdUserMasterId()%>', '<%=sURLPrefix%>'/FrontDeskUser/ExistingFrontDeskUserDetails)"></span>
                                <span class="glyphicon glyphicon-ok successIcon" style="color: #009933;" onclick="saveModifiedFdUser('<%=model.getFdUserMasterId()%>', '<%=sURLPrefix%>'/FrontDeskUser/SaveModifiedFdUser)"></span>
                                <span class="glyphicon glyphicon-refresh resetIcon" style="color: #600000;margin-right: 5px" onclick="resetPassword('<%=model.getFdUserMasterId()%>', '<%=ApplicationConstants.FD_USER%>')"></span>
                            </td>
                            <td><%=APPUtills.getString(model.getFdUserMasterId()+"")%></td>
                            <td><%=APPUtills.getString(model.getFdUserName())%></td>
                            <td><%=APPUtills.getString(model.getFdUserFirstName())%></td>
                            <td><%=APPUtills.getString(model.getFdUserLastName())%></td>
                            <td><%=ApplicationConstants.securepassStatusDesc(model.getSecurepassUserStatus())%></td>  
                            <td>
                                <select id="status<%=model.getFdUserMasterId()%>" name="status<%=model.getFdUserMasterId()%>" style="width: 40%;">
                                    <option value="<%=ApplicationConstants.STATUS_ACTIVE%>" <%=model.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_ACTIVE) ? "selected" : ""%>><%= ApplicationConstants.statusDesc(ApplicationConstants.STATUS_ACTIVE)%> </option>
                                    <option value="<%=ApplicationConstants.STATUS_INACTIVE%>" <%=model.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_INACTIVE) ? "selected" : ""%>><%= ApplicationConstants.statusDesc(ApplicationConstants.STATUS_INACTIVE)%> </option>
                                </select>
                            </td>
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
        <span class="glyphicon glyphicon-list-alt" style="color: #3399ff;margin-right: 5px"> </span> <i style="color: #3399ff;margin-right: 5px">View Details</i>|
        <span class="glyphicon glyphicon-ok " style="color: #009933;margin-right: 5px">  </span><i style="color: #009933;margin-right: 5px">Send for authorization</i>|
        <span class="glyphicon glyphicon-refresh" style="color: #600000;margin-right: 5px">  </span><i style="color: #600000;margin-right: 5px">Reset password</i>  
    </div>
    <div class="col-sm-6 col-md-6"></div>
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

    function clickOnCheckBox(val, clName) {
        if (val.checked) {
            $("." + clName + "").prop("readonly", false);
        } else {
            $("." + clName + "").prop("readonly", true);
        }
    }


    function resetPassword(id, type) {
        alert("Do you want to reset..! " + id + " - " + type);
    }


    var isNewRowExisting = false;
    function addNewEmployee() {
        if (!isNewRowExisting) {
            $('#mainTable tr:first').after('<tr>\n\
<td  style="text-align: right"><span class="glyphicon glyphicon-ok successIcon" style="color: #009933;" onclick="saveFdUser(\'\',\'<%=sURLPrefix%>/FrontDeskUser/SaveNewFrontDeskUser\')"></span></td>\n\
<td>-<input type="hidden" class="fieldData" id="tempId" name="tempId" value="" readonly></td>\n\
<td><input type="text" class="fieldData" id="userName" name="userName" value="" placeholder="USER NAME"></td>\n\
<td><input type="text" class="fieldData" id="userFirstName" name="userFirstName" value="" placeholder="FIRST NAME"></td>\n\
<td><input type="text" class="fieldData" id="userLastName" name="userLastName" value="" placeholder="LAST NAME"></td>\n\
<td>-</td>\n\
<td>\n\
<select id="status" name="status" style="width: 40%;">\n\
<option value="<%=ApplicationConstants.STATUS_ACTIVE%> selected="true">\n\
    <%= ApplicationConstants.statusDesc(ApplicationConstants.STATUS_ACTIVE)%> \n\
</option></select>\n\
</td>\n\
<td style="display: none">a</td>\n\
</tr> ');
            isNewRowExisting = true;
        } else {
            alert("Existing record must be saved.");
        }
    }

    function saveFdUser(id, uri) {//pending tab
        var id = id;//document.getElementById("tempId"+id).value;
        var userName = document.getElementById("userName" + id).value;
        var userFirstName = document.getElementById("userFirstName" + id).value;
        var userLastName = document.getElementById("userLastName" + id).value;

        if (userName === "" || userFirstName === "" || userLastName === "") {
            alert("Please fill all input field..! ");
        } else {
            if (confirm("Do you want to save..! ")) {
                if (uri !== "") {
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
    function saveModifiedFdUser(id, uri) {
        var status = document.getElementById("status" + id).value;
        //alert("Do you want to accept..! " + id + " - " + status);
        if (confirm("Do you want to save..! ")) {
            if (uri !== "") {
                hidePage();
                $.post(uri, {id: id, status: status}, function (data) {
                    $('#pageBody').empty();
                    $('#pageBody').append(data);
                    showPage();
                });
            }
        }
    }

</script>
