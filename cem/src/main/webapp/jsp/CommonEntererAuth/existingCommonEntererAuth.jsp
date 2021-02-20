<%-- 
    Document   : include-body
    Created on : Dec 21, 2020, 3:35:15 PM
    Author     : sits_lahirupr
--%>
<%@page import="com.company.models.CommonUserModel"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url var="images" value="/resources/images"/>

<%@page import="com.company.common.APPUtills"%>
<%@page import="com.company.models.DivInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.company.models.CompanyUserModel"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.company.common.ObjectManager"%>
<%@page import="com.company.common.ApplicationConstants"%>
<%@ include file="../includes/include-initial-variables.jsp"%>
<%@ include file="../includes/include-notifications.jsp"%>   
 <style> 
    input {
        width: 100px;
        height: 20px;
    }
    input.depEmpCheckBox[type=checkbox]{
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
	Map<Integer, CommonUserModel> modelMap = objManager.get("commonUserMap") != null ? (HashMap) objManager.get("commonUserMap") : new HashMap();
	List<CommonUserModel> objList = new ArrayList<>(modelMap.values());

    CompanyUserModel model= objManager.get("companyUserModel") != null ? (CompanyUserModel) objManager.get("companyUserModel") : null;
    String criteria = objManager.get("criteria") != null ? (String) objManager.get("criteria") : "";
    
    Map<String, DivInfo> divInfoMap = objManager.get("divInfoMap") != null ? (HashMap) objManager.get("divInfoMap") : new HashMap();
    List<DivInfo> divInfoList = new ArrayList<>(divInfoMap.values());
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

<%if (true) {%>
<div class="row">
    <div class="col-sm-6 col-md-6"><span style="color:#999999"><i><b>Results for : </b><%=criteria%></i></span></div>
    <div class="col-sm-2 col-md-2">        
        <select id="sol" name="sol" class="depList" style="background-color: #f2f2f2;width: 100%;padding: 5px 10px;margin: 2px 0;display: inline-block;border: 1px solid #ccc;border-radius: 4px;box-sizing: border-box;">
            <option value=""> --SELECT-- </option>
            <%for (DivInfo info : divInfoList) {%>
            <option value="<%=info.getDivId()%>"> <%=info.getName()%> </option>
            <%}%>
        </select>
       <!-- <span class="glyphicon glyphicon-sort-by-attributes" id="depOrder"></span>-->
    </div>
    <div class="col-sm-2 col-md-2">
        <input type="text" class="form-control" id="empId" name="empId" aria-describedby="emailHelp" placeholder="Employee ID.." style="background-color: #f2f2f2">
    </div>
    <div class="col-sm-1 col-md-1"> 
        <button type="button" onclick="getFilterData('<%=sURLPrefix%>/CommonEntererAuth/SearchCommonEntererAuths')" class="btn btn-primary">Search</button> 
    </div>
    <div class="col-sm-1 col-md-1"></div>
</div>
<%}%>

<hr>
<form id="CompanyUser" method="post" class="form-horizontal" action="<%=sURLPrefix%>/CompanyEmployee/SaveBulkEmp" enctype="multipart/form-data">
    <div class="row">
        <div class="col-sm-12 col-md-12">
            
                    <br>
                    <table class="table-bordered table-hover" id="mainTable">
                        <thead>
                            <tr>
                                <th></th>
                                <th>IMAGE</th>
                                <th>ID</th>
                                <th>NAME</th>
                                <th>GRADE</th>                            
                                <th>DIV ID</th>
                                <th>DEPARTMENT</th>
                                <th>DESIGNATION</th>                                
                                <th>STATUS</th>
                            </tr>
                        </thead>
                        <tbody>
                        <%if(model!=null){ %>
                             <tr>
                               <td><span class="glyphicon glyphicon-list-alt detailsIcon" style="color: #3399ff;" onclick="getDetails('<%=model.getCompanyUserEmpId()%>', '<%=sURLPrefix%>/CommonEntererAuth/ExistingCommonEntererAuthsDetails')"></span></td>
                                <td>
                                    <%if (APPUtills.isThisStringValid(model.getBase64Image())) {%>
                                    <img src="data:image/jpg;base64,<%=model.getBase64Image()%>" style="border-radius: 50%;margin-right: 2px" alt="prof pic" width="25" height="25"/>
                                    <%} else {%>
                                    <img src="${images}/userDefault.jpg" style="border-radius: 50%;margin-right: 2px" alt="prof pic" width="25" height="25"/>
                                    <%}%>
                                </td>
                                <td><%=APPUtills.getString(model.getCompanyUserEmpId())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserFirstName())+" "+APPUtills.getString(model.getCompanyUserMiddleName())+" "+APPUtills.getString(model.getCompanyUserLastName())%></td>
                                <td><%=model.getCompanyUserGrade()%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserDivId())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserDepName())%></td>
                                <td><%=APPUtills.getString(model.getCompanyUserDestination())%></td>
                                <td><%=ApplicationConstants.statusDesc(model.getUserStatus())%></td>
                             </tr> 
                        <%} %>
                          
                        </tbody>
                    </table> 
        </div>
    </div>
    <hr> 
    <div class="row">
        <div class="col-sm-8 col-md-8"></div>
        <div class="col-sm-2 col-md-2">
            <select id="userRoleType" name="userRoleType" class="userRoleType" style="width: 100p%;height: 30px;border-radius: 2px" >
                 <option value=""> --SELECT-- </option>
                  <option value="<%=ApplicationConstants.USER_ROLE_COMMON_ENTERER%>"> COMMON_ENTERER </option>
                  <option value="<%=ApplicationConstants.USER_ROLE_COMMON_AUTHORIZER%>"> COMMON_AUTHORIZER </option>
	        </select>
        </div>
        <div class="col-sm-2 col-md-2">
            <div style="float: right;">
                <button id="saveBtn" type="button" onclick="CreateCommonEneterAuth('<%=sURLPrefix%>/CommonEntererAuth/CreateCommonEneterAuth')" class="btn btn-success">Save</button> 
            </div>
        </div>
    </div>
</form>

<hr>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div id="grideDiv"> 
            <div style="overflow-y: scroll;overflow-x: scroll; height:700px;border-radius: 5px">
                <br>
                <table class="table-bordered table-hover" id="mainTable">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>ROLE</th>                              
                            <th>STATUS</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%if(objList.size()==0){ %>
                         <tr>
                            <td colspan="3" style="text-align: center;"> no data found </td>
                         </tr> 
                    <%}else{
                    	for(CommonUserModel obj: objList) {%>
                       <tr>
                            <td><%=obj.getCmnUserId() %></td>
                            <td><%=obj.getCmnUserRole().equalsIgnoreCase("3")?" COMMON_ENTERER":"COMMON_AUTHORIZER " %></td>
                            <td><%=ApplicationConstants.statusDesc(obj.getUserStatus())%></td>
                         </tr> 
                         <%}}%>
                    </tbody>
                </table>
            </div>    
        </div>    
    </div>
</div>

<script>
    $(document).ready(function () {
    	 $(".titleLink").css('background', '#1a8cff');
         $("#cmnUsr").css('background', '#006080');
    });


    function CreateCommonEneterAuth(uri) {//existing tab
        var role = document.getElementById("userRoleType").value;
        console.log("Type : "+role);
        if (uri !== "") {
            hidePage();
            $.post(uri, {role: role}, function (data) {
                $('#pageBody').empty();
                $('#pageBody').append(data);
                showPage();
            });
        }
    }

    function getExistingCompanyUserDetails(id, uri) {
        $("#saveBtn").show();
        if (uri !== "") {
            hidePage();
            $.post(uri, {id: id}, function (data) {
                $('#modelDivData').empty();
                $('#modelDivData').append(data);
                showPage();
            });
        }

        document.getElementById("btnForModel").click();
    }

    function getFilterData(uri) {
    	var sol = document.getElementById('sol').value;
        var empId = document.getElementById('empId').value;

        console.log("sol : "+sol+" empId : "+empId);
        
        if (sol === "" || empId === "") {
            alert("Please give your condition for both..");
        } else if (uri !== "") {
            hidePage();
            $.post(uri, {sol: sol, empId: empId}, function (data) {
                $('#pageBody').empty();
                $('#pageBody').append(data);
                showPage();
            });
        }
    }


    function changeStatus(id, uri) {//pending tab
        var id = id;//document.getElementById("tempId"+id).value;
        if (id === "") {
            alert("Recived empty id..! ");
        } else {
            if (uri !== "") {
                if (confirm("Do you want to change the status?")) {
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