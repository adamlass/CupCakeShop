<%-- 
    Document   : user
    Created on : 27-02-2018, 20:01:16
    Author     : adamlass
--%>

<%@page import="servlet.MakeAdmin"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Collection"%>
<%@page import="dao.user_dao"%>
<%@page import="entities.User"%>
<%@page import="comparators.usernamecomparator" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>


        <link href="styling.css" rel="stylesheet" type="text/css"/>
        <link href="table.css" rel="stylesheet" type="text/css"/>


        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>User - <%
            MakeAdmin.updateUser(request);

            User user = ((User) session.getAttribute("user"));
            out.print(user.getName());
            %>
        </title>

    </head>
    <body>
        <%@include file="menubar.jsp" %>
        <h1> 
            <%
                out.print(user.getName());
            %>
        </h1>

     
        <br>
        <form action="orders.jsp" method="get">
            <input type="submit" name="submit" value="See orders for <%= user.getName()%>">
        </form>


        <div>
            <h4>Info:</h4>

            <font color="<%= user.getColor()%>">
            <a><%
                out.print("Balance: " + user.getBalance() + " DKK");
                %></a>
            </font>


            <br>
            <br>
            <a>Add to balance</a>

            <form action="AddToBalance" method="post">
                <input type="text" name="amount">DKK
                <input type="submit" value="Add to balance">
                <%
                request.getSession().setAttribute("balmoduser", user); %>
            </form>

            <a>
                <%
                    try {
                        String status = request.getParameter("status");
                        if (status.equals("true")) {
                            out.print("Funds were added to account");
                        } else if (status.equals("false")) {
                            out.print("Transaction failed!");
                        } else if (status.equals("invalid")) {
                            out.print("Amount can't be 0 or negative");
                        }
                    } catch (Exception e) {

                    }
                %>



            </a>
        </div>








        <!--If user is admin-->

        <% if (user.isAdmin()) {
        %>
        <h1>ADMIN:</h1>
        <form action="allorders.jsp" method="get">
            <input type="submit" name="submit" value="See ALL orders">
        </form>

        <%@include file="addcupcake.jsp" %>

        <br>

        <table width="100%">
            <tr>
                <th>Username</th>
                <th>isAdmin</th>
                <th>Balance</th>
                <th>Change role</th>
                <th>Delete</th>

            </tr>
            <%
                user_dao userdao = new user_dao();
                List<User> users = userdao.getAll();
                Collections.sort(users, new usernamecomparator());

                for (User u : users) {
            %>

            <tr>
                <td><%= u.getName()%></td>
                <td><%= u.isAdmin()%></td>
                <td><%= u.getBalance() + " DKK"%></td>

                <!--Make admin-->

                <td>
                    <form action="MakeAdmin" method="post">
                        <input type="hidden" name="adminname" value="<%= u.getName()%>"/>
                        <input type="submit" value="<%
                            if (u.isAdmin()) {
                                out.print("Remove admin");
                            } else {
                                out.print("Make admin");
                            }
                               %>">

                    </form>
                </td>
                <td>
                    <form action="DeleteUser" method="post">
                        <input type="hidden" name="username" value="<%= u.getName()%>"/>
                        <input type="submit" value="Delete">

                    </form>
                </td>


            </tr>

            <%
                }

            %>
        </table>  
        <%        }
        %>



    </body>
</html>
