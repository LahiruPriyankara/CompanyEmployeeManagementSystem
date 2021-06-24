<%-- 
    Author     : lahiru priyankara
--%>
<%@page import="com.company.models.CommonUserModel"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url var="images" value="/resources/images"/>

<%@page import="com.company.common.APPUtills"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.company.common.ApplicationConstants"%>

<%@ include file="../includes/include-initial-variables.jsp"%>
<%@ include file="../includes/include-notifications.jsp"%> 

<%
    Map<Integer, CommonUserModel> commonUserMap = objManager.get("commonUserMap") != null ? (Map<Integer, CommonUserModel>) objManager.get("commonUserMap") : new HashMap();
    List<CommonUserModel> objList = new ArrayList<>(commonUserMap.values());
%>
<div>
    <ul class="nav nav-tabs"> 
        <li>
            <a id="existingTab" href="#" onclick="clickOnTab('<%=sURLPrefix%>/CommonEntererAuth/ExistingCommonEntererAuths')"><b><i>Existing</i></b></a>
        </li>
        <li class="active">
            <a id="pendingTab" href="#" onclick="clickOnTab('<%=sURLPrefix%>/CommonEntererAuth/PendingCommonEntererAuths')"><b><i>Pending</i></b></a>
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
                            <th>NAME</th>
                            <th>ROLE</th>                            
                            <th>ACTION TYPE</th>
                            <th>RECORD STATUS</th>                             
                            <th>USER STATUS</th>                           
                        </tr>
                    </thead>
                    <tbody>
                        <%for (CommonUserModel model : objList) {%>
                        <tr style="<%if(model.getRecStatus().equalsIgnoreCase(ApplicationConstants.RECORD_STATUS_REJECT)){ %>color: #red;<%}%>">
                            <td style="text-align: right">
                                 <span class="glyphicon glyphicon-list-alt detailsIcon" style="color: #3399ff;" onclick="getDetails('<%=model.getCmnUserTmpId()%>', '<%=sURLPrefix%>/CommonEntererAuth/PendingCommonEntererAuthsDetals')"></span>
                                
                                <%if (model.getRecStatus().equalsIgnoreCase(ApplicationConstants.RECORD_STATUS_PENDING)) {
                                	if(userData.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_COMMON_AUTHORIZER)){ %>
                                    <span class="glyphicon glyphicon-remove cancelIcon" style="color: #800000;" onclick="rejectChanges('<%=model.getCmnUserTmpId()%>', '<%=sURLPrefix%>/CommonEntererAuth/RejectCommonEneterAuth')"></span>
                                  	<span class="glyphicon glyphicon-ok successIcon" style="color: #009933;" onclick="verifyOrDelete('<%=model.getCmnUserTmpId()%>', '<%=sURLPrefix%>/CommonEntererAuth/VerifyCommonEneterAuth')"></span>
                                   <%}
                                }%>
                                <%if (model.getRecStatus().equalsIgnoreCase(ApplicationConstants.RECORD_STATUS_REJECT)) {
                                	if(userData.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_COMMON_ENTERER)){%>
                                <span class="glyphicon glyphicon-trash removeIcon" style="color: #e62e00;" onclick="verifyOrDelete('<%=model.getCmnUserTmpId()%>', '<%=sURLPrefix%>/CommonEntererAuth/deleteCommonEneterAuth')"></span>
                                <%}
                                }%>
                            </td>
                            
                            <td><%=APPUtills.getString(model.getCmnUserId())%></td>
                            <td><%=APPUtills.getString(model.getCmnUserRole()).equalsIgnoreCase(ApplicationConstants.USER_ROLE_COMMON_ENTERER)?"COMMON_ENTERER":"COMMON_AUTHORIZER"%></td>
                            <td><%=ApplicationConstants.actionTypeDesc(model.getActionType())%></td>
                            <td><%=ApplicationConstants.actionTypeDesc(model.getActionType())%></td>
                            <td><%=ApplicationConstants.recordStatusDesc(model.getRecStatus())%></td>
                            <td><%=ApplicationConstants.statusDesc(model.getUserStatus())%></td>                  
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
        $("#cmnUsr").css('background', '#006080');
    });


    function rejectChanges(id, uri) {//only for fd pending
        var reason = $("#rejectReason").val();
        if (reason === "") {
            alert("Comment is required..!");
        } else if (confirm("Do you want to reject.")) {
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

    function verifyOrDelete(id, uri) {//pending tab
        var id = id;//document.getElementById("tempId"+id).value;
        if (id === "") {
            alert("Recived empty id..! ");
        } else {
            if (uri !== "") {
                if (confirm("Do you want to send the changes?")) {
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

    
</script>