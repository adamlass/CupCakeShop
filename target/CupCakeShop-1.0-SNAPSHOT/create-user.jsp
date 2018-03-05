<%-- 
    Document   : create-user
    Created on : 27-02-2018, 14:23:23
    Author     : adamlass
--%>

<%@page import="entities.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create user</title>
        <link href="styling.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>Create user</h1>

        <form action="createControl" method="post">
            <%@include  file="loginform.jsp" %>
        </form>
        
        
        <%
            try {
                String username = ((User) session.getAttribute("user")).getName();
                if (request.getParameter("status").equals("true")) {
                    out.print("User " + username +" was created");
                } else if (request.getParameter("status").equals("false")) {
                    out.print("User already exists");
                }
            } catch (Exception e) {
                
            };
            

        %>
        <br>
        <form action="login.jsp" method="post">
                <button type="submit">Log in<br>with account</button>
            </form>
    </body>
</html>
