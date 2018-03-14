/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import datasource.DataSource;
import dbconnector.DBConnector;
import entities.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adamlass
 */
public class User_dao {

    DBConnector dbc;

    public User_dao() {
        this.dbc = new DBConnector(new DataSource().getDataSource());
    }

    public boolean connectiontest() throws SQLException {
        dbc.open();
        ResultSet res = dbc.query("SELECT * FROM cupcakeshop.test");
        boolean test = res.next();

        dbc.close();
        return test;

    }

    public User createUser(String username, String password) throws SQLException {
        return createUser(username, password, false, 0);
    }

    public User createUser(String username, String password, boolean isAdmin, double balance) throws SQLException {
        if (findUser(username, null) != null) {
            return null;
        }
        System.err.println("REACHED");

        dbc.open();
        String sql = "INSERT INTO users (username,password,isAdmin,balance) "
                + "values (?,?,?,?)";

        PreparedStatement pre = dbc.preparedStatement(sql);

        pre.setString(1, username);
        pre.setString(2, password);
        pre.setBoolean(3, isAdmin);
        pre.setDouble(4, balance);

        pre.execute();

        if (findUser(username, null) != null) {
            dbc.close();
            return new User(username, password, false, balance);
        }

        dbc.close();
        return null;
    }

    public User findUser(String username) throws SQLException {
        return findUser(username, null);
    }

    public User findUser(String username, String password) throws SQLException {
        User result = null;
        dbc.open();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM users where username = ?");

        if (password != null) {
            sql.append(" and password = ?");
        }

        PreparedStatement pre = dbc.preparedStatement(sql.toString());

        pre.setString(1, username);

        if (password != null) {
            pre.setString(2, password);
        }

        ResultSet res = pre.executeQuery();

        if (res.next()) {

            result = new User(res.getString("username"), res.getString("password"), res.getBoolean("isAdmin"), res.getDouble("balance"));
        }

        dbc.close();

        return result;
    }

    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();

        dbc.open();

        String sql = "SELECT * FROM cupcakeshop.users";
        ResultSet res = dbc.query(sql);

        while (res.next()) {
            String username = res.getString("username");
            String password = res.getString("password");
            boolean isAdmin = res.getBoolean("isAdmin");
            double balance = res.getDouble("balance");

            users.add(new User(username, password, isAdmin, balance));
        }

        return users;
    }

    public void toggleAdmin(String username) throws SQLException {
        User u = findUser(username);
        boolean newadmin = !u.isAdmin();

        dbc.open();
        
        String sql = "UPDATE users SET isadmin=? where username=?";
        
        PreparedStatement pre = dbc.preparedStatement(sql);
        
        pre.setBoolean(1, newadmin);
        pre.setString(2, username);
        
        pre.execute();
        
        dbc.close();
        
    }

    public void deleteUser(String username) throws SQLException {
        dbc.open();

        String sql = "DELETE FROM users WHERE username=?";
        PreparedStatement pre = dbc.preparedStatement(sql);

        pre.setString(1, username);

        pre.execute();

        dbc.close();
    }

    public User updateFunds(User balmoduser, double amount) throws SQLException {
        User user = findUser(balmoduser.getName());
        dbc.open();

        String sql = "UPDATE users SET balance=? WHERE username=?";

        PreparedStatement pre = dbc.preparedStatement(sql);

        pre.setDouble(1, user.getBalance() + amount);
        pre.setString(2, user.getName());

        pre.execute();

        dbc.close();
        return findUser(user.getName());
    }

}
