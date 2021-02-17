<%-- 
    Document   : include-body
    Created on : Dec 21, 2020, 3:35:15 PM
    Author     : sits_lahirupr
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url var="images" value="/resources/images"/>


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
            <a id="existingTab" href="#" onclick="clickOnTab('<%=sURLPrefix%>/CommonEntererAuth/ExistingCommonEntererAuths')"><b><i>Existing</i></b></a>
        </li>
        <li>
            <a id="pendingTab" href="#" onclick="clickOnTab('<%=sURLPrefix%>/CommonEntererAuth/PendingCommonEntererAuths')"><b><i>Pending</i></b></a>
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
                                <span class="glyphicon glyphicon-list-alt detailsIcon" style="color: #3399ff;" onclick="getDetails('<%=model.getFdUserMasterId()%>', '<%=sURLPrefix%>/FrontDeskUser/ExistingFrontDeskUserDetails')"></span>
                                <span class="glyphicon glyphicon-ok successIcon" style="color: #009933;" onclick="saveModifiedFdUser('<%=model.getFdUserMasterId()%>', '<%=sURLPrefix%>/FrontDeskUser.SaveModifiedFdUser')"></span>
                                <span class="glyphicon glyphicon-repeat resetIcon" style="color: #600000;margin-right: 5px" onclick="resetPassword('<%=model.getFdUserMasterId()%>', '<%=ApplicationConstants.FD_USER%>')"></span>
                            </td>
                            <td><%=model.getFdUserMasterId()%></td>
                            <td><%=model.getFdUserName()%></td>
                            <td><%=model.getFdUserFirstName()%></td>
                            <td><%=model.getFdUserLastName()%></td>
                            <td><%=ApplicationConstants.securepassStatusDesc(model.getSecurepassUserStatus())%></td>  
                            <td>
                                <select id="status<%=model.getFdUserMasterId()%>" name="status<%=model.getFdUserMasterId()%>"  onchange="toggleActivation('')" style="width: 40%;">
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
        <span class="glyphicon glyphicon-repeat" style="color: #600000;margin-right: 5px">  </span><i style="color: #600000;margin-right: 5px">Reset password</i>  
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
        $("#cmnUsr").css('background', '#006080');

    });

</script>