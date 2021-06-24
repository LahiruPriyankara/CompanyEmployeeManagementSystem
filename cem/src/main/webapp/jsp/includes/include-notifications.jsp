<%-- 
    Author     : lahiru priyankara
--%>

<% if (request.getAttribute("rtnMsg") != null) {%>  
<div class="alert alert-success alert-dismissible" id="alertMsg" style="margin-top: 30px">
    <p class="close">&times;</p>
    <strong>Success!</strong> <%=request.getAttribute("rtnMsg")%>
</div>
<%}%>
<% if (request.getAttribute("errMsg") != null) {%>
<div class="alert alert-danger alert-dismissible" id="alertMsg" style="margin-top: 30px">
    <p class="close">&times;</p>
    <strong>Error!</strong> <%=request.getAttribute("errMsg")%>
</div>
<%}%>

<script>
    $(document).ready(function () {
        $(".close").click(function () {
            $("#alertMsg").alert("close");
        });
    });
</script>