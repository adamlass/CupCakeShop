<%-- 
    Document   : configurator
    Created on : 27-02-2018, 21:56:58
    Author     : adamlass
--%>

<%@page import="entities.CakePart"%>
<%@page import="dao.CupCake_dao"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
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
        <link href="styling_1.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Configurator</title>
        <link href="menustyle.css" rel="stylesheet" type="text/css"/>
  

        <!--                <link href="table.css" rel="stylesheet" type="text/css"/>
                        <link href="styling.css" rel="stylesheet" type="text/css"/>-->
    </head>
    <body>
        <%@include file="menubar.jsp" %>
        <div class="col-sm-6">
            <h1>CupCake Configurator</h1>
            
            <!--<div id="piccake">-->
                <img  src="images/cupcakes-slider-image.png" alt=""/>

            <!--</div>-->


        </div>
        <div class="col-sm-6">
            <%
                CupCake_dao dao = new CupCake_dao();
                List<CakePart> toppings = dao.getParts("topping");
                List<CakePart> buttoms = dao.getParts("buttom");
            %> 

            <form action="AddToCart" method="get">

                <div class="row">
                    <div class="col-sm-4">
                        <h2>Toppings</h2>
                        <%
                            for (int i = 0; i < toppings.size(); i++) {

                        %>
                        <input type="radio" name="topping" value="<%= "" + i%>"/><% out.print("" + toppings.get(i).getName() + " - Price: " + toppings.get(i).getPrice() + " DKK"); %><br>
                        <%
                            }
                        %>
                    </div>

                    <div class="col-sm-4">

                        <h2>Buttoms</h2>
                        <%
                            for (int i = 0; i < buttoms.size(); i++) {
                        %>



                        <input type="radio" name="buttom" value="<%= "" + i%>"/><% out.print("" + buttoms.get(i).getName() + " - Price: " + buttoms.get(i).getPrice() + " DKK");
                        %><br>
                        <%
                            }
                        %>
                        <div class="col-sm-2">
                            <input  type="submit" name="submit" value="Add to cart">
                        </div>

                        </form>
                    </div>
                </div>
        </div>

        <%@include file="ShoppingCart.jsp" %>

        <div class="col-sm-6">
            <%  try {
                if (request.getParameter("purchase").equals("true")) { %>
            <h4>Transaction complete<br>Thank you for the purchase!</h4>
            <form action="orders.jsp" method="post">
                <button type="submit">See orders</button>
            </form>

            <% } else if (request.getParameter("purchase").equals("nef")) {
            %> <h4>Not enough funds!<br>Please add funds to your bank account!<br></h4>
            <form action="user.jsp" method="post">
                <button type="submit">Add funds now</button>
            </form>
            <%
            } else {
            %> <h4>Order Purchase failed</h4><%
                    }
                } catch (Exception e) {

                }
            %>
        </div>




    </body>
</html>
