<%@page import="com.company.models.UserData"%>
<%@page import="com.company.common.ObjectManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <%
        ObjectManager objManager = new ObjectManager(session);

        String ctxName = getServletConfig().getServletContext().getServletContextName();
        String sSession = session.getId();
        //String sURLPrefix = "/" + ctxName + "/ActionController?";  
        String sURLPrefix = request.getContextPath();

        String tabId = objManager.get("TabId") != null ? (String) objManager.get("TabId") : "11";
        //String tabHeaderId = tabId.substring(0, 1);
        //String tabContentId = tabId.substring(1, 2);

        UserData userData = objManager.get("userData") != null ? (UserData) objManager.get("userData") : new UserData();

    %>
</head>