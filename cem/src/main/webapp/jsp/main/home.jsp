<%-- 
    Author     : lahiru priyankara
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page import="com.company.models.UserData"%>
<%@page import="com.company.common.ApplicationConstants"%>
<%@page import="com.company.common.ObjectManager"%>
<%@page import="com.company.common.APPUtills"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<spring:url var="images" value="/resources/images"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@ include file="../includes/include-css-js.jsp"%> 
        <!-- Initial variables --> 
        <%@ include file="../includes/include-initial-variables.jsp"%>
    </head>
    <%        
    String userType = objManager.get("userType") != null ? (String) objManager.get("userType") : "";
    objManager.remove("userType");
    %>
    <body>
        <button type="button" id="btnForModel" data-toggle="modal" data-target="#myModal" hidden="true">Open Modal</button>
        <div id="modelBox">
            <!-- Modal -->
            <%@ include file="../includes/include-modelbox.jsp"%>
        </div>

        <div  class="container-fluid">  
            <%@ include file="../includes/include-navigation.jsp"%>
            <div class="row">
                <div class="col-sm-1 col-md-1"></div>
                <div class="col-sm-10 col-md-10">                    
                    <div style="float: right;margin-right: 10px">
                        <span  style="color: #333333;">
                            <%if (APPUtills.isThisStringValid(userData.getBase64Image())) {%>
                            <img src="data:image/jpg;base64,<%=userData.getBase64Image()%>" style="box-shadow: 0 0 2px 2px #331400;border-radius: 50%;margin-right: 2px" alt="prof pic" width="25" height="25"/>
                            <%} else {%>
                            <img src="${images}/userDefault.jpg" style="box-shadow: 0 0 2px 2px #331400;border-radius: 50%;margin-right: 2px" alt="prof pic" width="25" height="25"/>
                            <%}%>

                            &nbsp;&nbsp;&nbsp;
                            <span class="glyphicon glyphicon-user"></span>                             
                            User : <%=userData.getFIRST_NAME() + " " + userData.getLAST_NAME()%> | 
                            <%if (userData.getUSER_TYPE().equalsIgnoreCase(ApplicationConstants.BANK_EMPLOYEE)) {%>
                            &nbsp;&nbsp;Emp ID : <%=userData.getUSER_ID()%> - <%=userData.getAD_USER_ID()%> |  DEPARTMENT ID : <%=userData.getDIV_CODE()%> | ROLE : <%=ApplicationConstants.getRoleDesc(userData.getUSER_ROLE())%> |
                            <%} else {%>
                            &nbsp;&nbsp;Emp ID : <%=userData.getUSER_NAME()%> | ROLE : <%=ApplicationConstants.getRoleDesc(userData.getUSER_ROLE())%> |
                            <%}%>
                            &nbsp;&nbsp;Date : <span id="dateLabel"></span>                          
                        </span>
                    </div>


                    <div id="pageBody">
                        <%if (userType.equalsIgnoreCase(ApplicationConstants.BANK_DEP_USER)) {%>
                        <%@ include file="../employee/existingEmp.jsp"%>   
                        <%} else {%>
                        <%@ include file="../includes/include-dashboard.jsp"%>   
                        <%}%>

                    </div>

                    <br><br><br>

                    <%@ include file="../includes/include-footer.jsp"%>
                </div>
                <div class="col-sm-1 col-md-1"></div>
            </div>
            <!-- Page loading div -->
            <%@ include file="../includes/pageLoading.jsp"%>
        </div>
    </body>
</html>
<!-- INDEX PAGE scripts -->
<script src="<%=request.getContextPath()%>/resources/js/custom/homePageScript.js"></script>
<script>
    showPage();
    var date = new Date();
    document.getElementById("dateLabel").innerHTML = date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate();
</script> 
