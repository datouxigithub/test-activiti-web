<%-- 
    Document   : test
    Created on : 2017-3-27, 16:48:34
    Author     : datouxi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    request.getRequestDispatcher("rest/identity/users").forward(request, response);
%>
