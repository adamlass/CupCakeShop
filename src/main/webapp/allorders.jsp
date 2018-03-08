<%-- 
    Document   : allorders
    Created on : 04-03-2018, 15:47:15
    Author     : adamlass
--%>
<%@page import="java.util.List"%>
<%@page import="entities.Order"%>
<%@page import="dao.Order_dao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
         <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>All Orders</title>
        <link href="table.css" rel="stylesheet" type="text/css"/>
        <link href="styling.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
               <%@include file="menubar.jsp" %>
        <h1>List of all orders</h1>

        <div class="col-sm-9">
        <table width="100%">
            <tr>
                <th>OrderNumber</th>
                <th>Owner</th>
                <th>Items</th>
                <th>Date</th>
                <th>Price</th>
                <th>Change price</th>
                <th>Delete</th>

            </tr>
            <%
                Order_dao odao = new Order_dao();
                List<Order> orders = odao.allOrders();
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
                    <form action="allorders.jsp" method="post">
                        <input type="hidden" name="idorders" value="<%= order.getIdorders()%>">
                        <input type="submit" name="submit" value="Order <%= order.getIdorders()%>">
                    </form>

                </td>
                <td><% if (order.getOwner() != null) {
                        out.print(order.getOwner().getName());
                    }%>
                </td>
                <td><%= order.getOrderLines().size() + " items"%></td>
                <td><%= order.getDateFormat() %></td>
                <td><%= order.getPrice()%> DKK</td>

                <td>
                    <!--change price-->
                    <form action="changeprice" method="post">
                        <input type="hidden" name="idorders" value="<%= order.getIdorders()%>">
                        <input type="text" name="newprice" value="<%= order.getPrice()%>" size="7" >
                        <input type="submit" name="submit" value="Change price">
                    </form>
                </td>
                <td>
                    <!--delete order-->
                    <form action="deleteorder" method="post">
                        <input type="hidden" name="idorders" value="<%= order.getIdorders()%>">
                        <input type="submit" name="submit" value="Delete">
                    </form>
                </td>

            </tr>

            <% }%>
            
            <tr>
                <th colspan="2">Total:</th>
                <th><%= totalItems %> items</th>
                <th></th>
                <th><%= totalAmount %> DKK</th>
                <th></th>
                <th></th>
            </tr>

        </table>
        </div>
                
                <div class="col-sm-3">
                    <%if(request.getParameter("idorders") != null){ %>
                    <%@include file="order_1.jsp"%>
                    <%} %>
                </div>
    </body>
</html>
