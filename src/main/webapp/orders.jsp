<%-- 
    Document   : orders
    Created on : 04-03-2018, 10:24:06
    Author     : adamlass
--%>

<%@page import="entities.Order"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="dao.Order_dao"%>
<%@page import="entities.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <% User user = (User) session.getAttribute("user");%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="table.css" rel="stylesheet" type="text/css"/>
        <title><%= "" + user.getName() + "'s orders"%></title>
        <link href="styling.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1><%= "" + user.getName() + "'s orders"%></h1>

        <form action="user.jsp">
            <input type="submit" value="back">
        </form>
        <br>
        
        <table width="100%">
            <tr>
                <th>OrderNumber</th>
                <th>Items</th>
                <th>Date</th>
                <th>Price</th>
            </tr>
            <%
                Order_dao dao = new Order_dao();
                List<Order> orders = dao.userOrders(user.getName());
            %>
            <% if (orders.size() < 1) { %>
            <tr>
                <td colspan="4">No orders to show</td>
            </tr>
            <% } %>

            <%
                int totalItems = 0;
                double totalAmount = 0.0;
                for (Order order : orders) {
                    totalItems += order.getOrderLines().size();
                    totalAmount += order.getPrice();
                    
            %>
            <tr>
                <td>
                    <form action="order.jsp" method="post">
                        <input type="hidden" name="idorders" value="<%= order.getIdorders()%>">
                        <input type="submit" name="submit" value="Order <%= order.getIdorders()%>">
                    </form>

                </td>
                <td><%= order.getOrderLines().size() + " items"%></td>
                <td><%= order.getDate()%></td>
                <td><%= order.getPrice()%> DKK</td>

            </tr>

            <% }%>

            <tr>
                <th>Total:</th>
                <th><%= totalItems %> items</th>
                <th></th>
                <th><%= totalAmount %> DKK</th>
            </tr>

        </table>
    </body>
</html>
