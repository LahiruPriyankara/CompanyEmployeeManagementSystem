<%-- 
    Author     : lahiru priyankara
--%>
<%@page import="com.company.dto.VisitorData"%>
<%@page import="com.company.common.APPUtills"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ include file="../includes/include-initial-variables.jsp"%>
<%@ include file="../includes/include-notifications.jsp"%>  
<style> 
    input {
        width: 50%;
        height: 20px;
    }
</style>
<% List<VisitorData> dataList =  objManager.get("dataList") != null ? (ArrayList)objManager.get("dataList"): new ArrayList(); %>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div id="grideDiv"> 
            <div style="overflow-y: scroll;overflow-x: scroll; height:700px;border-radius: 5px">
                <br>
                <table class="table-bordered table-hover" id="mainTable">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>COMPANY UER ID</th>
                            <th>ISSUED PASS</th>
                            <th>NIC NUMBER</th>
                            <th>CREATED BY</th>
                            <th>CREATED DATE</th>                     
                        </tr>
                    </thead>
                    <tbody>
                    <%if(dataList.size()>0){ %>
                        <%for (VisitorData data: dataList) {%>
                        <tr>
                            <td><%=APPUtills.getString(data.getId()+"")%></td>
                            <td><%=data.getCompanyEmpId() %></td>
                            <td><%=data.getVisitorPassId()%></td>
                            <td><%=data.getVisitorNicNum() %></td>
                            <td><%=APPUtills.getString(data.getCreatedBy().toString())%></td>
                            <td><%=APPUtills.formatDate(data.getCreatedDate(), APPUtills.DATE_TIME_SHORT_FORMAT)%></td>                             
                        </tr> 
                        <%}
                       } %>
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
        $("#visitor").css('background', '#006080');

    });
</script>
