


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log in</title>
        <link href="styling.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
               <%@include file="menubar.jsp" %>
        <h1 id="cream">Log in</h1>
        <div class="centerbox">
        <form action="loginControl" method="post">
            <%@include  file="loginform.jsp" %>
        </form>
            <%
                try {
                if (request.getParameter("status").equals("true")) {
                    out.print("Log in success");
                } else if (request.getParameter("status").equals("false")) {
                    out.print("Log in failed!");
                }
                } catch (Exception e) {
                }
            %>
            <br>
            <form action="create-user.jsp" method="post">
                <button type="submit">Create Account</button>
            </form>
        </div>


    </body>
</html>
