<!--<link href="styling_1.css" rel="stylesheet" type="text/css"/>-->
<%@page import="entities.User"%>
<ul>

    <li>
        <a id="nopad" href="index.jsp">
            <img  id="menpic" src="images/7cc02d75f9ffa470bca561b9628eb157.jpg" alt=""/>
        </a>
    </li>








    <% User userc = (User) session.getAttribute("user");
        if (userc == null) {
    %>
    <li style="float:right"><a href="login.jsp">Log in</a></li>
        <% } else { %>

    <li><a href="configurator.jsp">Configure your CupCake</a></li>
    <li><a href="orders.jsp">Orders</a></li>


    <% if (userc.isAdmin()) { %>
    <li><a href="allorders.jsp">All orders</a></li>

    <% }%>
    <li style="float:right"><a href="Logout">Log out</a></li>
    <li style="float:right">
        <a href="user.jsp"><%= userc.getName().toUpperCase()%></a>
    </li>
    <% }%>
</ul>
