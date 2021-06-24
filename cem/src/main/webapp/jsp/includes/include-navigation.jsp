<%-- 
    Author     : lahiru priyankara
--%>

<%@page import="com.company.models.UserData"%>
<%@page import="com.company.common.ObjectManager"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url var="images" value="/resources/images"/>

<%@page import="com.company.common.ApplicationConstants"%>

<%
        ObjectManager objManager0 = new ObjectManager(session);

        UserData userData0 = objManager0.get("userData") != null ? (UserData) objManager0.get("userData") : new UserData();
        
        String sURLPrefixx = request.getContextPath();
    %>

<nav class="navbar navbar-default" style="background-color: #3399ff">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="#" id="pageTitle" onclick="clickOnTab('<%=sURLPrefixx%>/authonticate/LoadDashboard')" style="text-decoration:none">
                <img style=" border-radius: 10%;margin-right: 2px" alt="plusMark" width="50" height="50" src="${images}/compayLogo.png"/>
                <span style="color: #FFFFFF;"><b><i>&nbsp;&nbsp;&nbsp;Employee Directory &nbsp;&nbsp;&nbsp;</i></b></span>
            </a>

        </div>
        <ul class="nav navbar-nav">
            <li>
	            <a class="titleLink" href="#" id="home" onclick="clickOnTab('<%=sURLPrefixx%>/authonticate/LoadDashboard')" style="background-color: #006080;margin-right: 1px;color: #ffffff;width: 150px;text-align: center">
	            	<b>Home</b>
	            </a>
            </li>
            <li>
	            <a class="titleLink" href="#" id="allEmployee" onclick="clickOnTab('<%=sURLPrefixx%>/CommonView/ExistingEmp')" style="background-color: #1a8cff;margin-right: 1px;color: #ffffff;width: 150px;text-align: center">
	            	<b>ALL EMPLOYEE</b>
	            </a>
            </li>
            <%if(!userData0.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_COMPANY_VIWER)&&!userData0.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_FD_USER)){ %>
            <%if (userData0.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_COMMON_ENTERER) || userData0.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_COMMON_AUTHORIZER)||userData0.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_BRANCH_ENTERER) || userData0.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_BRANCH_AUTHORIZER)) { %>
            <li>
	            <a class="titleLink" href="#" id="depEmp" onclick="clickOnTab('<%=sURLPrefixx%>/CompanyDepEmployee/ExistingEmp')" style="background-color: #1a8cff;margin-right: 1px;color: #ffffff;width: 150px;text-align: center">
	            	<b>DEP EMPLOYEE</b>
	            </a>
            </li>
            <%}%>
            
            <%if (userData0.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_COMMON_ENTERER) || userData0.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_COMMON_AUTHORIZER)) { %>            
            <li>
	            <a class="titleLink" href="#" id="cmnUsr" onclick="clickOnTab('<%=sURLPrefixx%>/CommonEntererAuth/ExistingCommonEntererAuths')" style="background-color: #1a8cff;margin-right: 1px;color: #ffffff;width: 150px;text-align: center">
	            	<b>COMMON USER</b>
	            </a>
            </li>            
            
            <li>
	            <a class="titleLink" href="#" id="fdUsr" onclick="clickOnTab('<%=sURLPrefixx%>/FrontDeskUser/ExistingFrontDeskUsers')" style="background-color: #1a8cff;margin-right: 1px;color: #ffffff;width: 150px;text-align: center">
	            	<b>FD USER</b>
	            </a>
            </li>
            <%} %>
            <%} %>
            
            <%if(!userData0.getUSER_ROLE().equalsIgnoreCase(ApplicationConstants.USER_ROLE_FD_USER)){ %>            
            <li>
	            <a class="titleLink" href="#" id="visitor" onclick="clickOnTab('<%=sURLPrefixx%>/CommonView/GetAllVisitorData')" style="background-color: #1a8cff;margin-right: 1px;color: #ffffff;width: 150px;text-align: center">
	            	<b>VISITOR</b>
	            </a>
            </li>
            <%} %>
            <!-- <li><a class="titleLink" href="#" onclick="clickOnTab('4')" style="background-color: #ff751a;margin-right: 1px;color: #ffffff;width: 150px;text-align: center"><b>Password Reset</b></a></li> -->
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="<%=sURLPrefixx%>/authonticate/UserLogout" style="background-color: #1a8cff;margin-right: 1px;color: #ffffff"><span class="glyphicon glyphicon-log-out"></span> Log Out</a></li>
        </ul>
    </div>
</nav>