<%@page import="entities.ShoppingCart"%>
<%@page import="entities.CupCake"%>
<%@page import="entities.CakePart"%>
<%@page import="interfaces.Item"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="entities.User"%>

<div id="cart" class="col-sm-12">
<h3 >Cart:</h3>
<table class="table table-striped">
    <tr>
        <th>Item</th>
        <th>Price</th>
        <th>Amount</th>
        <th>Delete</th>
    </tr>
    <%
        User user = (User) session.getAttribute("user");
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("ShoppingCart");
        List<Item> items = shoppingCart.getItems();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);


    %>
    <tr>
        <td> 
            <% if (item.getClass().getSimpleName().equals("CupCake")) {%>
            <table class="table table-striped">
                <% List<CakePart> parts = ((CupCake) item).getSubParts();
                    for (CakePart part : parts) {

                %>
                <tr>
                    <td>
                        <%= "" + part.getName()%>
                    </td>
                    <td>
                        <%= "" + part.getPrice() + " DKK"%>
                    </td>

                </tr>
                <% } %>
            </table>
            <% } else {
                    out.print(item.getName());
                }%>
        </td>
        <td> <%= "" + item.getPrice() + " DKK"%></td>
        <td>
            
            <form action="UpdateAmount" method="post">
                <input type="hidden" name="index" value="<%= i %>">
                <input type="text" name="amount" maxlength="2" size="2" value="<%= "" + shoppingCart.getItems().get(i).getAmount() %>">
                <input type="submit" name="submit" value="Update">
            </form>
        </td>
        <td>
            <form action="DeleteItem" method="post">
                <input type="hidden" name="index" value="<%= i%>">
                <input type="submit" name="submit" value="Delete <%= item.getName()%>">
            </form>
        </td>
    </tr>
    <% }%>


</table>

<a>Full Price: <%= "" + shoppingCart.getPrice()%> DKK</a>
<br>
<br>
<a>Your Balance: <%= "" + user.getBalance()%> DKK</a>
<br>
<a>Balance after purchase: <%= "" + (user.getBalance() - shoppingCart.getPrice())%> DKK</a>

<form action="Purchase" method="post">
    <button type="submit">Submit Purchase</button>
</form>
</div>