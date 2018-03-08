
<%@page import="entities.User"%>
<ul>
    <li><img  id="menpic" src="images/7cc02d75f9ffa470bca561b9628eb157.jpg" alt=""/></li>
    <li><a href="configurator.jsp">Configure your CupCake</a></li>
   
    
    
    
    
    <% User userc = (User) session.getAttribute("user"); 
    if(userc == null){
    %>
    <li style="float:right"><a href="login.jsp">Log in</a></li>
    <% } else { %>
    <li style="float:right"><a href="Logout">Log out</a></li>
    <% } %>
</ul>