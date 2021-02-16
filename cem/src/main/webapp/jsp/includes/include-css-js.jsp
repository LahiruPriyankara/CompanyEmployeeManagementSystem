<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url var="js" value="/resources/js"/>
<spring:url var="css" value="/resources/css"/>


<!-- BOOTSTRAP scripts -->
<link href="${css}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- INDEX PAGE style -->
<link href="${css}/custom/homePageStyle.css" rel="stylesheet">
<!-- INDEX PAGE LOADING style -->
<link href="${css}/custom/pageLoading.css" rel="stylesheet">

<link href="${css}/font-awesome/css/font-awesome.css" rel="stylesheet">
<!-- Date picker -->
<link href="${css}/datapicker/datepicker3.css" rel="stylesheet">  

<!-- JQUERY scripts -->
<script src="${js}/jquery/jquery_3.5.1.js"></script>

<script src="${css}/bootstrap/js/bootstrap.min.js"></script>

<!-- INDEX PAGE LOADING script -->
<script src="${js}/custom/pageLoading.js"></script>

<!-- for waiting popup 
<link href="<request.getContextPath()%>/ui/css/jquery-ui.css" rel="stylesheet">-->

<!-- Data Tables -->
<script src="${js}/dataTables/jquery.dataTables.js"></script>
<script src="${js}/dataTables/dataTables.bootstrap.js"></script>
<script src="${js}/dataTables/dataTables.responsive.js"></script>
<script src="${js}/dataTables/dataTables.tableTools.min.js"></script>

<!-- Data Tables Export-->
<script src="${js}/dataExportJs/dataTables.buttons.min.js"></script>
<script src="${js}/dataExportJs/buttons.flash.min.js"></script>    
<script src="${js}/dataExportJs/jszip.min.js"></script>
<script src="${js}/dataExportJs/pdfmake.min.js"></script>
<script src="${js}/dataExportJs/vfs_fonts.js"></script>    
<script src="${js}/dataExportJs/buttons.html5.min.js"></script>
<script src="${js}/dataExportJs/buttons.print.min.js"></script>        
<script src="${js}/dataExportJs/html2pdf.bundle.js"></script>

<!-- Auto complete -->
<script src="${js}/jquery/jquery.autocomplete.js"></script>
<!-- Date picker -->
<script src="${js}/datapicker/bootstrap-datepicker.js"></script>    
