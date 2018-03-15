/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import datasource.DataSource;
import dbconnector.DBConnector;
import entities.CakePart;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a datamapper/dao that reads and writes data via a database connection.
 *
 * Every method uses PreparedStatements to prevent user injections.
 * 
 * @author adamlass
 */
public class CupCake_dao {

    DBConnector dbc;

    /**
     * Initiating the connection
     */
    public CupCake_dao() {
        this.dbc = new DBConnector(new DataSource().getDataSource());
    }

    /**
     * Use this method to get a list of CakeParts of a given type
     * 
     * 
     * @param type
     * 
     * There are two types of CakeParts:
     * 
     * 1. topping
     * 2. buttom
     * 
     * @return List of Cakeparts of the given type
     * @throws SQLException 
     */
    public List<CakePart> getParts(String type) throws SQLException {
        List<CakePart> result = new ArrayList();
        dbc.open();

        String sql = "SELECT * FROM cakeParts where type = ?";
        PreparedStatement pre = dbc.preparedStatement(sql);

        pre.setString(1, type);

        pre.execute();
        ResultSet res = pre.getResultSet();
        while (res.next()) {
            int dbIndex = res.getInt("id");
            String name = res.getString("name");
            double price = res.getDouble("price");
            result.add(new CakePart(dbIndex, name, price));
        }

        dbc.close();
        return result;
    }

    /**
     * Given a name and type this method will return a CakePart object read from
     * the database
     * 
     * @param name name of the CakePart
     * @param type type of the CakePart
     * 
     * @return CakePart
     * @throws SQLException 
     */
    public CakePart getPart(String name, String type) throws SQLException {
        dbc.open();

        String sql = "SELECT * FROM cakeParts where name = ? and type=?";
        PreparedStatement pre = dbc.preparedStatement(sql);

        pre.setString(1, name);
        pre.setString(2, type);

        ResultSet res = pre.executeQuery();

        res.next();

        CakePart cp = new CakePart(res.getInt("id"), res.getString("name"), res.getDouble("price"));

        dbc.close();

        return cp;
    }

    /**
     * Use to add a CakePart to the database
     * 
     * @param name the desired name of the new CakePart
     * @param type the desired type of the new CakePart
     * @param price the desired price of the new CakePart
     * @throws SQLException 
     */
    public void addPart(String name, String type, double price) throws SQLException {
        dbc.open();
        
        String sql = "INSERT INTO cakeParts (name,type,price) values (?,?,?)";
                
         PreparedStatement pre = dbc.preparedStatement(sql);
         
         pre.setString(1, name);
         pre.setString(2, type);
         pre.setDouble(3, price);
         
         pre.execute();
         
        dbc.close();
    }

}
