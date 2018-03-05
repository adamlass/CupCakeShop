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
 *
 * @author adamlass
 */
public class CupCake_dao {

    DBConnector dbc;

    public CupCake_dao() {
        this.dbc = new DBConnector(new DataSource().getDataSource());
    }

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
            int price = res.getInt("price");
            result.add(new CakePart(dbIndex, name, price));
        }

        dbc.close();
        return result;
    }

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
