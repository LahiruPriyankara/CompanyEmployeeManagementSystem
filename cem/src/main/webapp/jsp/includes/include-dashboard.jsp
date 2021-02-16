<%@ include file="include-notifications.jsp"%>    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url var="images" value="/resources/images"/>
<div class="row" style="margin-top: 5%">
    <hr>
    <div class="col-sm-1 col-md-1"></div>
    <div class="col-sm-4 col-md-4">
        <img style="" alt="plusMark" width="100%" height="100%" src="${images}/emp-mng.jpg"/>

        <div style="background-color: black;height: 120px">
            <br>
            <p style="color: #ffffff">&nbsp;&nbsp;&nbsp;Sampath Bank PLC,</p>
            <p style="color: #ffffff">&nbsp;&nbsp;&nbsp;110 Sir James Peiris Mawatha,</p>
            <p style="color: #ffffff">&nbsp;&nbsp;&nbsp;Colombo 02.</p>
        </div>
    </div>
    <div class="col-sm-6 col-md-6">
        <div style="background-color: black;height: 40px;text-align: center">
            <h4 style="color: #ffffff;padding-top: 10px">Digital Employee Directory for Visitor Management</h4>
        </div>
        <br>
        <div style="text-align: center">
            <dl>
                <dt>About Bank</dt>
                <dd>- Sampath Bank PLC is a licensed commercial bank incorporated in Sri Lanka in 1987 with 229 branches and 373 ATMs island wide.</dd>
                <br>
                <dt>What is this system ?</dt>
                <dd>- This employee management system consists of crucial work-related and important personal information about an employee. In a nutshell, it is an online inventory of all employees of an organization. Employees are the strength of any organization, and it is more so in case of a growing business.</dd>

            </dl>

        </div>

    </div>
    <div class="col-sm-1 col-md-1"></div>
</div>
<hr>
<script>

    $(document).ready(function () {
        $(".titleLink").css('background', '#1a8cff');
        $("#home").css('background', '#006080');
    });

</script>