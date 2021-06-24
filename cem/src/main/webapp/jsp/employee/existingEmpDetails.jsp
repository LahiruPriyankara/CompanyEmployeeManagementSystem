<%-- 
    Author     : lahiru priyankara
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url var="images" value="/resources/images"/>

<%@page import="com.company.models.CompanyUserModel"%>
<%@page import="com.company.common.ApplicationConstants"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.company.common.APPUtills"%>
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
        font-size: 12px;
    }
    select.depEmpDetails {
        width: 100%;
        padding: 5px 10px;
        margin: 2px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
        font-size: 12px;
    }
    .labelTxt{
        font-size: 12px;
    }

    /* input[type=text], select {
         width: 100%;
         padding: 12px 20px;
         margin: 8px 0;
         display: inline-block;
         border: 1px solid #ccc;
         border-radius: 4px;
         box-sizing: border-box;
     }

    input[type=submit] {
        width: 100%;
        background-color: #4CAF50;
        color: white;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    input[type=submit]:hover {
        background-color: #45a049;
    }
        div {
            border-radius: 5px;
            background-color: #f2f2f2;
            padding: 20px;
        }
    */
</style>

<%
    CompanyUserModel DVMmodelMaster = objManager.get("CEMmodelMaster") != null ? (CompanyUserModel) objManager.get("CEMmodelMaster") : new CompanyUserModel();
    CompanyUserModel UPMmodel = objManager.get("COM_SERVICEmodel") != null ? (CompanyUserModel) objManager.get("COM_SERVICEmodel") : new CompanyUserModel();

    List<String> grades = ApplicationConstants.USER_GRADES;
    List<String> gender = ApplicationConstants.USER_GENDER;
%>

<div style="text-align: center;background-image: url('${images}/deailsBackGround.jpg');">
    <%if (APPUtills.isThisStringValid(DVMmodelMaster.getBase64Image())) {%>
    <img src="data:image/png;base64,<%=DVMmodelMaster.getBase64Image()%>" style="box-shadow: 0 0 2px 2px #331400; border-radius: 50%;margin-right: 2px" alt="prof pic" width="200" height="200"/>
    <%} else {%>
    <img src="${images}/userDefault.jpg" style="box-shadow: 0 0 2px 2px #331400; border-radius: 50%;margin-right: 2px" alt="prof pic" width="200" height="200"/>
    <%}%>
</div>
<hr>

<form id="bankUserDetails" method="post" class="form-horizontal" action="<%= sURLPrefix%>/CompanyEmployee/SaveSingleEmp" enctype="multipart/form-data">
    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label style="color: #ccc;"><i>basic info</i></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <hr>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Employee ID</i><span style="color: red;">*</span></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="empId<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="empId<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="empoyee id.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserEmpId())%>" <%if (!APPUtills.isEqual(DVMmodelMaster.getCompanyUserEmpId(),UPMmodel.getCompanyUserEmpId())) {%>style="background-color:#ffad99" <%} else {%>style="background-color:#cccccc" readonly <%}%> >
            <div style="float: right"><span style="color: #006bb3;font-size: 10px"><i>UPM VALUE : <%=APPUtills.getString(UPMmodel.getCompanyUserEmpId())%></i></span></div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Image</i></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input type="file" id="profPic<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="profPic<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="image.." style="width: 100%;padding: 20px 20px;display: inline-block;border: 1px solid #ccc;border-radius: 4px;box-sizing: border-box;">
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>First Name</i><span style="color: red;">*</span></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="fName<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="fName<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="first name.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserFirstName())%>" <%if (!APPUtills.isEqual(DVMmodelMaster.getCompanyUserFirstName(),UPMmodel.getCompanyUserFirstName())) {%>style="background-color:#ffad99" <%} else {%>style="background-color:#cccccc" readonly <%}%> >
            <div style="float: right"><span style="color: #006bb3;font-size: 10px"><i>UPM VALUE : <%=APPUtills.getString(UPMmodel.getCompanyUserFirstName())%></i></span></div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Middle Name</i></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="mName<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="mName<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="middle name.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserMiddleName())%>">
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Last Name</i><span style="color: red;">*</span></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="lName<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="lName<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="last name.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserLastName())%>" <%if (!APPUtills.isEqual(DVMmodelMaster.getCompanyUserLastName(),UPMmodel.getCompanyUserLastName())) {%>style="background-color:#ffad99" <%} else {%>style="background-color:#cccccc" readonly <%}%> >
            <div style="float: right"><span style="color: #006bb3;font-size: 10px;"><i>UPM VALUE : <%=APPUtills.getString(UPMmodel.getCompanyUserLastName())%></i></span> </div>    
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Gender</i><span style="color: red;">*</span></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <select class="depEmpDetails" id="gender<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="gender<%=DVMmodelMaster.getCompanyUserEmpId()%>" required>
                <%for (String g : gender) {%>
                <option value="<%=g%>"> <%=ApplicationConstants.genderTypeDesc(g)%></option>
                <%}%>
            </select>
        </div>
    </div>

    <br>
    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label style="color: #ccc"><i>job related info</i></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <hr>
        </div>
    </div>


    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Grade</i><span style="color: red;">*</span></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <select class="depEmpDetails" id="grade<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="grade<%=DVMmodelMaster.getCompanyUserEmpId()%>" required>
                <%for (String g : grades) {%>
                <option value="<%=g%>"> <%=g%></option>
                <%}%>
            </select>   
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Designation</i></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="designation<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="designation<%=DVMmodelMaster.getCompanyUserEmpId()%>" value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserDestination())%>" placeholder="designation..">
        </div>
    </div>


    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Sol Id</i><span style="color: red;">*</span></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="solId<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="solId<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="sol Id.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserDivId())%>" <%if (!APPUtills.isEqual(DVMmodelMaster.getCompanyUserDivId(),UPMmodel.getCompanyUserDivId())) {%>style="background-color:#ffad99" <%} else {%>style="background-color:#cccccc" readonly <%}%> >
            <div style="float: right"><span style="color: #006bb3;font-size: 10px"><i>UPM VALUE : <%=APPUtills.getString(UPMmodel.getCompanyUserDivId())%></i></span></div>   
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Department</i><span style="color: red;">*</span></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="depName<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="depName<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="department name.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserDepName())%>" <%if (!APPUtills.isEqual(DVMmodelMaster.getCompanyUserDepName(),UPMmodel.getCompanyUserDepName())) {%>style="background-color:#ffad99" <%} else {%>style="background-color:#cccccc" readonly <%}%>>
            <div style="float: right"><span style="color: #006bb3;font-size: 10px"><i>UPM VALUE : <%=APPUtills.getString(UPMmodel.getCompanyUserDepName())%></i></span></div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Floor</i><span style="color: red;">*</span></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="floor<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="floor<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="floor.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserFloor())%>" required>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Job Dec</i></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="jobdesc<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="jobdesc<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="job descriptoin.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserJobDesc())%>">
        </div>
    </div>

    <br>
    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label style="color: #ccc"><i>contact info</i></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <hr>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Dep Extention</i><span style="color: red;">*</span></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="depExtention<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="depExtention<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="department extention.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserDepExten())%>" required>
        </div>
    </div>


    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Emp Mobile Num</i></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" class="depEmpDetails" type="text" id="empMobile<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="empMobile<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="employee mobile number.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserOfficeMobile())%>">
        </div>
    </div>


    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Emp Extention</i><span style="color: red;">*</span></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="empExtention<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="empExtention<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="employee extention.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserOfficeExten())%>" required>
        </div>
    </div>


    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Email</i><span style="color: red;">*</span></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="empEmail<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="empEmail<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="employee email.." value="<%=APPUtills.getString(APPUtills.getString(DVMmodelMaster.getCompanyUserOfficeEmail()))%>" required>
        </div>
    </div>


    <br>
    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label style="color: #ccc"><i>next person info</i></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <hr>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Name</i><span style="color: red;">*</span></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="nextPersonName<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="nextPersonName<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="next person's name.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserContactPersonName())%>" required>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Mobile</i></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" class="depEmpDetails" type="text" id="nextPersonMobile<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="nextPersonMobile<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="next person's mobile.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserContactPersonMobile())%>">
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Extention</i><span style="color: red;">*</span></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="nextPersonExtention<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="nextPersonExtention<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="next person's extention.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserContactPersonExten())%>" required>
        </div>
    </div>


    <br>
    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label style="color: #ccc"><i>other info</i></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <hr>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Remark</i></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text" id="remark<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="remark<%=DVMmodelMaster.getCompanyUserEmpId()%>" placeholder="remark.." value="<%=APPUtills.getString(DVMmodelMaster.getCompanyUserRemarks())%>">
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Action Type</i></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <input class="depEmpDetails" type="text"  value="<%=ApplicationConstants.actionTypeDesc(DVMmodelMaster.getActionType())%>" readonly="true" style="background-color: #cccccc"/>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-3 col-md-3">
            <label class="labelTxt"><i>Status</i><span style="color: red;font-size: 10px">*</span></label>
        </div>
        <div class="col-sm-9 col-md-9">
            <select class="depEmpDetails" id="status<%=DVMmodelMaster.getCompanyUserEmpId()%>" name="status<%=DVMmodelMaster.getCompanyUserEmpId()%>" required>
                <%if (DVMmodelMaster.getActionType().equalsIgnoreCase(ApplicationConstants.ACTION_TYPE_NEW)) {%>
                <option value="<%=ApplicationConstants.STATUS_ACTIVE%>"><%=ApplicationConstants.statusDesc(ApplicationConstants.STATUS_ACTIVE)%></option>
                <%} else {%>
                <option value="<%=ApplicationConstants.STATUS_ACTIVE%>" <%=DVMmodelMaster.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_ACTIVE) ? "selected" : ""%>><%=ApplicationConstants.statusDesc(ApplicationConstants.STATUS_ACTIVE)%></option>
                <option value="<%=ApplicationConstants.STATUS_INACTIVE%>" <%=DVMmodelMaster.getUserStatus().equalsIgnoreCase(ApplicationConstants.STATUS_INACTIVE) ? "selected" : ""%>><%=ApplicationConstants.statusDesc(ApplicationConstants.STATUS_INACTIVE)%></option>
                <%}%>
            </select>
        </div>
    </div>
</form>

<script>
    function saveBankUser() { 
        document.getElementById("modelCloseBtn").click();
        hidePage();
        $("#bankUserDetails").submit();        
    }
</script>