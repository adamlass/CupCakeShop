
<div id="scroll">
<%@page import="entities.CupCake"%>
<%@page import="entities.Order"%>
<%@page import="dao.Order_dao"%>
<% int idorders = Integer.parseInt(request.getParameter("idorders"));
   Order_dao oodao = new Order_dao();
   
   Order order = oodao.getOrder(idorders);
%>
<a>Order ID: <%= order.getIdorders() %></a><br>
<a>Owner: <% 
    if(order.getOwner() != null){ 
        out.print(order.getOwner().getName());
    } %></a><br>
<a>Items: <%= order.getOrderLines().size() %></a><br>
<a>Date: <%= order.getDateFormat() %></a><br>
<a>Price <%= order.getPrice()  %> DKK</a><br>

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
</div>