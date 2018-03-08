<%-- 
    Document   : order
    Created on : 04-03-2018, 10:38:13
    Author     : adamlass
--%>
<%@page import="entities.CupCake"%>
<%@page import="interfaces.Item"%>
<%@page import="entities.Order"%>
<%@page import="dao.Order_dao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="table.css" rel="stylesheet" type="text/css"/>
        <% int idorders = Integer.parseInt(request.getParameter("idorders"));
            Order_dao odao = new Order_dao();
            Order order = odao.getOrder(idorders);
        %>
        <title>Order <%= idorders%></title>
        <link href="styling.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
               <%@include file="menubar.jsp" %>
        <h1>Order <%= idorders%></h1>
        <form action="user.jsp">
            <input type="submit" value="back">
        </form>
        <br>
        <h4>Info</h4>
        <table width="30%">
            <tr>
                <td>Owner</td>
                <td><% if(order.getOwner() != null){ out.print(order.getOwner().getName());} %></td>
            </tr>
            <tr>
                <td>Date</td>
                <td><%= order.getDateFormat() %></td>
            </tr>
            <tr>
                <th>Full Price</th>
                <th><%= order.getPrice()%> DKK</th>
            </tr>

        </table>

        <h4>Items</h4>

        <table width="70%">
            <tr>
                <th>Item</th>
                <th>Price</th>
            </tr>

            <% for (CupCake cupcake : order.getOrderLines()) {%>

            <tr>
                <td>
                    <table width="100%">
                        <tr>
                            <td><%= cupcake.getTopping().getName() %></td>
                            <td><%= cupcake.getTopping().getPrice() %> DKK</td>
                        </tr>
                        <tr>
                            <td><%= cupcake.getButtom().getName() %></td>
                            <td><%= cupcake.getButtom().getPrice() %> DKK</td>
                        </tr>
                    </table>
                    
                </td>
                
                <td><%= cupcake.getPrice() %> DKK</td>
                
            </tr>
            

            <% }%>
        </table>


    </body>
</html>
