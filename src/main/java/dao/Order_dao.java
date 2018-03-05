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
 *
 * @author adamlass
 */
public class Order_dao {

    DBConnector dbc;

    public Order_dao() {
        this.dbc = new DBConnector(new DataSource().getDataSource());
    }

    public void makeOrder(User owner, ShoppingCart shoppingCart) throws SQLException {
        dbc.open();

        String sql = "INSERT INTO orders (owner,price) VALUES (?, ?)";
//        String sql = "INSERT INTO orders (name) VALUES (?)";

        PreparedStatement pre = dbc.preparedStatement(sql);

        pre.setString(1, owner.getName());
        pre.setDouble(2, shoppingCart.getPrice());

        pre.execute();

        sql = "select * from orders";

        //TODO takes worng index
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
    
    public Order getOrder(int index) throws SQLException{
        return userOrders("", index, true,false).get(0);
    }
    
    public List<Order> userOrders(String owner) throws SQLException{
        return userOrders(owner, 0, false,false);
    }
    
    public List<Order> allOrders() throws SQLException{
        return userOrders(null, 0, false, true);
    }

    private List<Order> userOrders(String owner, int singleIndex, boolean single, boolean all) throws SQLException {
        user_dao udao = new user_dao();
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
                CakePart topping = cakeParts.get(toppingIndex - 1);
                CakePart buttom = cakeParts.get(buttomIndex - 1);

                cupCakes.add(new CupCake(topping, buttom));

                if(res.next()){
                after = res.getInt("idorders");
                } else {
                    break;
                }
            } while (before == after);
            res.previous();
            
            Calendar cal = Calendar.getInstance();
  
            Timestamp ts = res.getTimestamp("date");
            cal.set(ts.getYear(), ts.getMonth(), ts.getDay(), ts.getHours(), ts.getMinutes(), ts.getSeconds());
            
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
