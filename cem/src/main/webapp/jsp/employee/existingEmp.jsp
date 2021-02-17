<%-- 
    Document   : include-body
    Created on : Dec 21, 2020, 3:35:15 PM
    Author     : sits_lahirupr
--%>
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
    ObjectManager objManager1 = new ObjectManager(session);
    String sSession1 = session.getId();
    String sURLPrefix1 = request.getContextPath();

    Map<String, CompanyUserModel> ActiveUsers = objManager1.get("cemActiveUsers") != null ? (HashMap) objManager1.get("cemActiveUsers") : new HashMap();
    Map<String, CompanyUserModel> InactiveUsers = objManager1.get("cemInactiveUsers") != null ? (HashMap) objManager1.get("cemInactiveUsers") : new HashMap();
    Map<String, CompanyUserModel> UsersMisMatchWithUPM = objManager1.get("cemUsersMisMatchWithCOM_SERVICE") != null ? (HashMap) objManager1.get("cemUsersMisMatchWithCOM_SERVICE") : new HashMap();
    Map<String, CompanyUserModel> NotAvailableUsers = objManager1.get("cemNotAvailableUsers") != null ? (HashMap) objManager1.get("cemNotAvailableUsers") : new HashMap();
    Map<String, DivInfo> divInfoMap = objManager1.get("divInfoMap") != null ? (HashMap) objManager1.get("divInfoMap") : new HashMap();
    String criteria = objManager1.get("criteria") != null ? (String) objManager1.get("criteria") : "";

    List<CompanyUserModel> ActiveUsersList = new ArrayList<>(ActiveUsers.values());
    List<CompanyUserModel> InactiveUsersList = new ArrayList<>(InactiveUsers.values());
    List<CompanyUserModel> UsersMisMatchWithUPMList = new ArrayList<>(UsersMisMatchWithUPM.values());
    List<CompanyUserModel> NotAvailableUsersList = new ArrayList<>(NotAvailableUsers.values());
    List<DivInfo> divInfoList = new ArrayList<>(divInfoMap.values());

    List<String> grades = ApplicationConstants.USER_GRADES;
    List<String> gender = ApplicationConstants.USER_GENDER;

    int size = ActiveUsersList.size() + InactiveUsersList.size() + UsersMisMatchWithUPMList.size() + NotAvailableUsersList.size();
    CompanyUserModel model = new CompanyUserModel();
    String rowBackGroundColor = "#FFFFFF";
    int count = 0;
    //System.out.print("ActiveUsersList.size() : " + ActiveUsersList.size());
    //System.out.print("InactiveUsersList.size() : " + InactiveUsersList.size());
    //System.out.print("UsersMisMatchWithUPMList.size() : " + UsersMisMatchWithUPMList.size());
    //System.out.print("NotAvailableUsersList.size() : " + NotAvailableUsersList.size());
%>

<div>
    <ul class="nav nav-tabs"> 
        <li class="active">            
            <a id="existingTab" href="#" onclick="clickOnTab('<%=sURLPrefix1%>/CompanyEmployee/ExistingEmp')"><b><i>Existing</i></b></a>
        </li>
        <li>
            <a id="pendingTab" href="#" onclick="clickOnTab('<%=sURLPrefix1%>/CompanyEmployee/PendingEmp')"><b><i>Pending</i></b></a>
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
        <button type="button" onclick="getFilterData('<%=sURLPrefix1%>/CompanyEmployee/SearchEmp')" class="btn btn-primary">Search</button> 
    </div>
    <div class="col-sm-1 col-md-1"></div>
</div>
<%}%>

<hr>
<form id="CompanyUser" method="post" class="form-horizontal" action="<%=sURLPrefix1%>/CompanyEmployee/SaveBulkEmp" enctype="multipart/form-data">
    <div class="row">
        <div class="col-sm-12 col-md-12">
            <div id="grideDiv"> 
                <div style="overflow-y: scroll;overflow-x: scroll; height:700px;border-radius: 5px">
                    <br>
                    <table class="table-bordered table-hover" id="mainTable">
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
                                <th>STATUS</th>
                                <th style="display: none">......</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (int i = 0; i < size; i++) {
                                    if (i < NotAvailableUsersList.size()) {//to hold Users who are not in .
                                        rowBackGroundColor = "#3399ff";
                                        model = NotAvailableUsersList.get(count);
                                    } else if (i < (NotAvailableUsersList.size() + UsersMisMatchWithUPMList.size())) {//to hold Users who are not match with UPM.
                                        if (i == NotAvailableUsersList.size()) {
                                            count = 0;
                                        }
                                        rowBackGroundColor = "#e68a00";
                                        model = UsersMisMatchWithUPMList.get(count);
                                    } else if (i < (NotAvailableUsersList.size() + UsersMisMatchWithUPMList.size() + ActiveUsersList.size())) {// Active User.
                                        if (i == (NotAvailableUsersList.size() + UsersMisMatchWithUPMList.size())) {
                                            count = 0;
                                        }
                                        rowBackGroundColor = "#33cc33";
                                        model = ActiveUsersList.get(count);
                                    } else if (i < (NotAvailableUsersList.size() + UsersMisMatchWithUPMList.size() + ActiveUsersList.size() + InactiveUsersList.size())) {// Inactive User.
                                        if (i == (NotAvailableUsersList.size() + UsersMisMatchWithUPMList.size() + ActiveUsersList.size())) {
                                            count = 0;
                                        }
                                        rowBackGroundColor = "#e62e00";
                                        model = InactiveUsersList.get(count);
                                    }
                                    count++;
                            %> 
                            <tr>
                                <td style="background-color: <%=rowBackGroundColor%>"><input type="checkbox" class="depEmpCheckBox" onclick="clickOnCheckBox(this, '<%=model.getCompanyUserEmpId()%>', '<%=model.getActionType()%>')" style=""/></td>
                                <td style="text-align: right">
                                    <span class="glyphicon glyphicon-list-alt detailsIcon" style="color: #3399ff;" onclick="getExistingCompanyUserDetails(<%=model.getCompanyUserEmpId()%>, '<%=sURLPrefix1%>/CompanyEmployee/ExistingEmpDetails')"></span>
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
                                    <select id="gender<%=model.getCompanyUserEmpId()%>" name="gender<%=model.getCompanyUserEmpId()%>" style="border-color: #ff9999" class="<%=model.getCompanyUserEmpId()%>" disabled="true">
                                        <%for (String g : gender) {%>
                                        <option value="<%=g%>" <%=model.getCompanyUserGender().equalsIgnoreCase(g) ? "selected" : ""%>> <%=ApplicationConstants.genderTypeDesc(g)%></option>
                                        <%}%>
                                    </select>

                                </td>
                                <td>
                                    <select id="grade<%=model.getCompanyUserEmpId()%>" name="grade<%=model.getCompanyUserEmpId()%>" style="border-color: #ff9999" class="<%=model.getCompanyUserEmpId()%>" disabled="true">
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
                                <td>
                                    <%if (model.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_NEW)) {%>
                                    <select id="status<%=model.getCompanyUserEmpId()%>" name="status<%=model.getCompanyUserEmpId()%>" style="border-color: #ff9999" class="<%=model.getCompanyUserEmpId()%>" disabled="true">
                                        <option value="<%=ApplicationConstants.STATUS_ACTIVE%>" selected><%=ApplicationConstants.statusDesc(ApplicationConstants.STATUS_ACTIVE)%></option>
                                    </select>
                                    <%} else {%>
                                    <select id="status<%=model.getCompanyUserEmpId()%>" name="status<%=model.getCompanyUserEmpId()%>" style="border-color: #ff9999" class="<%=model.getCompanyUserEmpId()%>" disabled="true">
                                        <option value="<%=ApplicationConstants.STATUS_ACTIVE%>" <%=model.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_ACTIVE) ? "selected" : ""%>><%=ApplicationConstants.statusDesc(ApplicationConstants.STATUS_ACTIVE)%></option>
                                        <option value="<%=ApplicationConstants.STATUS_INACTIVE%>" <%=model.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_INACTIVE) ? "selected" : ""%>><%=ApplicationConstants.statusDesc(ApplicationConstants.STATUS_INACTIVE)%></option>
                                    </select>
                                    <%}%>

                                </td>
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
        <div class="col-sm-10 col-md-10">
            <span class="glyphicon glyphicon-list-alt" style="color: #3399ff;margin-right: 5px"> </span> <i style="color: #3399ff;margin-right: 5px">View Details</i> |

            <span style='font-size:20px;color: #3399ff'>&#9724;</span><span style="color: #3399ff;padding-right: 12px;padding-left: 12px;"><i>Only in UPM</i></span> |

            <span style='font-size:20px;color: #e68a00'>&#9724;</span><span style="color: #e68a00;padding-right: 12px;padding-left: 12px;"><i>Mismatch with UPM</i></span> |

            <span style='font-size:20px;color: #33cc33'>&#9724;</span><span style="color: #33cc33;padding-right: 12px;padding-left: 12px;"><i> Active</i></span> |

            <span style='font-size:20px;color: #e62e00'>&#9724;</span><span style="color: #e62e00;margin-right: 12px;padding-left: 12px"><i> Inactiv</i>e</span>
        </div>
        <div class="col-sm-2 col-md-2">
            <div style="float: right;">
                <button id="saveBtn" type="button" onclick="SaveBankEmp()" class="btn btn-success">Save</button> 
            </div>
        </div>
    </div>
</form>
<script>
    //var department = ["Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Anguilla", "Antigua &amp; Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia &amp; Herzegovina", "Botswana", "Brazil", "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central Arfrican Republic", "Chad", "Chile", "China", "Colombia", "Congo", "Cook Islands", "Costa Rica", "Cote D Ivoire", "Croatia", "Cuba", "Curacao", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji", "Finland", "France", "French Polynesia", "French West Indies", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guam", "Guatemala", "Guernsey", "Guinea", "Guinea Bissau", "Guyana", "Haiti", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Jamaica", "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauro", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russia", "Rwanda", "Saint Pierre &amp; Miquelon", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "St Kitts &amp; Nevis", "St Lucia", "St Vincent", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor L'Este", "Togo", "Tonga", "Trinidad &amp; Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks &amp; Caicos", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States of America", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Virgin Islands (US)", "Yemen", "Zambia", "Zimbabwe"];

    $(document).ready(function () {
        $(".titleLink").css('background', '#1a8cff');
        $("#depEmp").css('background', '#006080');

        $('#mainTable').DataTable({
            //"sPaginationType": "full_numbers",
            //"bJQueryUI": true,
            "dom": '<lf<t>ip>',
            //"lengthMenu": [[15, 50, 100, -1], [15, 50, 100, "All"]]
        });

        /*$("#departmentList").autocomplete({
         source: department
         });*/

        $('.depList').click(function () {
            var options = $('select.depList option');
            var arr = options.map(function (_, o) {
                return {
                    t: $(o).text(),
                    v: o.value
                };
            }).get();
            arr.sort(function (o1, o2) {
                return o1.t > o2.t ? 1 : o1.t < o2.t ? -1 : 0;
            });
            options.each(function (i, o) {
                //console.log(i);
                o.value = arr[i].v;
                $(o).text(arr[i].t);
            });
        });

    });


    function saveModifiedFdUser(id, uri) {//existing tab
        var status = document.getElementById("status" + id).value;
        //alert("Are you want to accept..! " + id + " - " + status);

        if (uri !== "") {
            hidePage();
            $.post(uri, {id: id, status: status}, function (data) {
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

    var map_add_new = new Map();
    var map_add_modify = new Map();
    var newIds = "";
    var modifyIds = "";
    function clickOnCheckBox(val, clName, actionType) {
        console.log("clName : " + clName)
        if (val.checked) {
            if (actionType === '<%=ApplicationConstants.ACTION_TYPE_NEW%>') {
                map_add_new.set(clName, clName);
            } else if (actionType === '<%=ApplicationConstants.ACTION_TYPE_MODIFY%>') {
                map_add_modify.set(clName, clName);
            }
            $("." + clName + "").closest('tr').css('background-color', '#ffd1b3')
            $("." + clName + "").prop("readonly", false);
            $("#status" + clName + "").prop("disabled", false);
            $("#grade" + clName + "").prop("disabled", false);
            $("#gender" + clName + "").prop("disabled", false);
            $("#profPic" + clName + "").prop("disabled", false);
        } else {
            if (actionType === '<%=ApplicationConstants.ACTION_TYPE_NEW%>') {
                map_add_new.delete(clName);
            } else if (actionType === '<%=ApplicationConstants.ACTION_TYPE_MODIFY%>') {
                map_add_modify.delete(clName);
            }
            $("." + clName + "").closest('tr').css('background-color', '')
            $("." + clName + "").prop("readonly", true);
            $("#status" + clName + "").prop("disabled", true);
            $("#grade" + clName + "").prop("disabled", true);
            $("#gender" + clName + "").prop("disabled", true);
            $("#profPic" + clName + "").prop("disabled", true);
        }
    }

    function SaveBankEmp() {
        console.log("map_add_new.size : " + map_add_new.size + " , map_add_modify.size : " + map_add_modify.size);
        if (map_add_new.size === 0 && map_add_modify.size === 0) {
            alert("Please select some users to save.");
        } else {
            var newKey = Array.from(map_add_new.keys());
            for (var i = 0; i < newKey.length; i++) {
                if (i === 0) {
                    newIds = newKey[i];
                } else {
                    newIds = newIds + "," + newKey[i];
                }
            }
            var modifyKey = Array.from(map_add_modify.keys());
            for (var i = 0; i < modifyKey.length; i++) {
                if (i === 0) {
                    modifyIds = modifyKey[i];
                } else {
                    modifyIds = modifyIds + "," + modifyKey[i];
                }
            }
            console.log("newIds : " + newIds);
            console.log("modifyIds : " + modifyIds);


            var form = document.getElementById('CompanyUser');
            var input1 = document.createElement('input');

            input1 = document.createElement('input');
            input1.setAttribute("type", "hidden");
            input1.setAttribute("id", "newIds");
            input1.setAttribute("name", "newIds");
            input1.setAttribute("value", newIds);
            form.appendChild(input1);

            var input2 = document.createElement('input');
            input2 = document.createElement('input');
            input2.setAttribute("type", "hidden");
            input2.setAttribute("id", "modifyIds");
            input2.setAttribute("name", "modifyIds");
            input2.setAttribute("value", modifyIds);
            form.appendChild(input2);

            hidePage();
            $("#CompanyUser").submit();
            //showPage();
        }

    }

    function getFilterData(uri) {
        var sol = document.getElementById('sol').value;
        var empId = document.getElementById('empId').value;
        if (sol === "" && empId === "") {
            alert("Please give your condition..");
        } else if (uri !== "") {
            hidePage();
            $.post(uri, {sol: sol, empId: empId}, function (data) {
                $('#pageBody').empty();
                $('#pageBody').append(data);
                showPage();
            });
        }
    }



</script>