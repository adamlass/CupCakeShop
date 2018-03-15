/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import datasource.DataSource;
import dbconnector.DBConnector;
import entities.CakePart;
import entities.CupCake;
import entities.Order;
import entities.ShoppingCart;
import entities.User;
import interfaces.Item;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This is a datamapper/dao that reads and writes data via a database connection.
 *
 * Every method uses PreparedStatements to prevent user injections.
 * 
 * @author adamlass
 */
public class Order_dao {

    DBConnector dbc;

    /**
     * Initiating the connection
     */
    public Order_dao() {
        this.dbc = new DBConnector(new DataSource().getDataSource());
    }

    /**
     * Write an order on the database with this method.
     * 
     * @param owner An object of the user that is the "owner" of the purchase
     * @param shoppingCart The shoppingcart with all the CupCakes
     * @throws SQLException 
     */
    public void makeOrder(User owner, ShoppingCart shoppingCart) throws SQLException {
        dbc.open();

        String sql = "INSERT INTO orders (owner,price) VALUES (?, ?)";

        PreparedStatement pre = dbc.preparedStatement(sql);

        pre.setString(1, owner.getName());
        pre.setDouble(2, shoppingCart.getPrice());

        pre.execute();

        sql = "select * from orders";

        ResultSet res = dbc.query(sql);
        res.last();
        int index = Integer.parseInt(res.getString("idorders"));

        dbc.close();

        for (Item item : shoppingCart.getItems()) {
            for (int i = 0; i < item.getAmount(); i++) {
                dbc.open();
                sql = "INSERT INTO orderline (idorder,topping,buttom) values (?,?,?)";
                pre = dbc.preparedStatement(sql);

                CupCake ck = (CupCake) item;

                pre.setInt(1, index);
                pre.setInt(2, ck.getTopping().getDbIndex());
                pre.setInt(3, ck.getButtom().getDbIndex());
                pre.execute();

                dbc.close();
            }
        }

        dbc.close();
    }
    
    /**
     * Find a single order in the database.
     * 
     * @param index the index of the order in the database (IdOrders)
     * @return orderobject
     * @throws SQLException 
     */
    public Order getOrder(int index) throws SQLException{
        return userOrders("", index, true,false).get(0);
    }
    
    /**
     * Find all the orders for any given user (the owner)
     * 
     * @param owner the username of the person that you want to have the list
     * of orders from
     * @return A list of order objects made by the owner (can be empty)
     * @throws SQLException 
     */
    public List<Order> userOrders(String owner) throws SQLException{
        return userOrders(owner, 0, false,false);
    }
    
    /**
     * Find all the orders from the Orders table in the database.
     * @return Order objects of all the orders in the database.
     * @throws SQLException 
     */
    public List<Order> allOrders() throws SQLException{
        return userOrders(null, 0, false, true);
    }

    /**
     * This is a multi-purpose method for reading orderdata from the database.
     * 
     * The method should be used in an overloaded method that is more specific
     * for the purpose.
     * 
     * @param owner the name of the owner in the database
     * @param singleIndex used together with the 'single' parameter if we only 
     * want a specific order added to the returned list.
     * 
     * @param single We only want to return a specific order
     * @param all We want all the the orders returned
     * @return A list of the orders recieved from the database
     * @throws SQLException 
     */
    private List<Order> userOrders(String owner, int singleIndex, boolean single, boolean all) throws SQLException {
        User_dao udao = new User_dao();
        CupCake_dao cdao = new CupCake_dao();
 
        dbc.open();
        List<Order> orders = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        
        sql.append("SELECT * FROM orders "
                + "inner join orderline ol on idorders=idorder");
        
        if(!all){
        if(single){
            sql.append(" where idorders=?");
        } else {
            sql.append(" where owner=?");
        }
        }

        PreparedStatement pre = dbc.preparedStatement(sql.toString());

        if(!all){
        if(single){
            pre.setInt(1, singleIndex);
        } else {
            pre.setString(1,owner);
        }
        }

        ResultSet res = pre.executeQuery();

        List<CakePart> cakeParts = cdao.getParts("topping");
        cakeParts.addAll(cdao.getParts("buttom"));

        while (res.next()) {
            List<CupCake> cupCakes = new ArrayList<>();
            int before = res.getInt("idorders");
            int after;

            do {
                int toppingIndex = res.getInt("topping");
                int buttomIndex = res.getInt("buttom");
                CakePart topping = getPart(toppingIndex,cakeParts);
                CakePart buttom = getPart(buttomIndex,cakeParts);

                cupCakes.add(new CupCake(topping, buttom));

                if(res.next()){
                after = res.getInt("idorders");
                } else {
                    break;
                }
            } while (before == after);
            res.previous();
            //test
            Calendar cal = Calendar.getInstance();
  
            Timestamp date = res.getTimestamp("date");
            
            if(date != null){
            cal.setTimeInMillis(date.getTime());
            }
            
            orders.add(new Order(res.getInt("idorders"), udao.findUser(res.getString("owner")), res.getDouble("price"), cal, cupCakes));
        }

        dbc.close();
        return orders;
    }
    
    public void deleteOrder(int idorders) throws SQLException{
        dbc.open();
        
        String sql = "DELETE FROM orders where idorders=?";
        
        PreparedStatement pre = dbc.preparedStatement(sql);
        
        pre.setInt(1,idorders);
        
        pre.execute();
        
        dbc.close();
    }

    public void changePrice(int idorders, double newprice) throws SQLException {
        dbc.open();
        
        String sql = "UPDATE orders SET price=? WHERE idorders=?";
        
        PreparedStatement pre = dbc.preparedStatement(sql);
        
        pre.setDouble(1, newprice);
        pre.setInt(2, idorders);
        
        pre.execute();
        dbc.close();
    }

    private CakePart getPart(int index, List<CakePart> cakeParts) {
        for(CakePart cp : cakeParts){
            if(cp.getDbIndex()== index){
                return cp;
            }
        }
        return null;
    }

    
}
//  while (res.next()) {
//            List<CupCake> cupCakes = new ArrayList<>();
//            sql = "SELECT * FROM orderline where idorder=?";
//
//            PreparedStatement pre1 = dbc.preparedStatement(sql);
//
//            int orderID =  res.getInt("idorders");
//            pre1.setInt(1,orderID);
//
//            ResultSet res1 = pre.executeQuery();
//
//            while (res1.next()) {
//                List<CakePart> cakeParts = cdao.getParts("topping");
//                cakeParts.addAll(cdao.getParts("buttom"));
//
//                int toppingIndex = Integer.parseInt(res1.getString("idorder"));
//                int buttomIndex = res1.getInt("buttom");
//                CakePart topping = cakeParts.get(toppingIndex - 1);
//                CakePart buttom = cakeParts.get(buttomIndex - 1);
//
//                cupCakes.add(new CupCake(topping, buttom));
//            }
//
//            orders.add(new Order(res.getInt("idorders"), ownerU, res.getDouble(""), null, cupCakes));
//
//        }
