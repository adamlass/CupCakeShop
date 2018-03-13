/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datasource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 *
 * @author adamlass
 */
public class DataSource {
    private MysqlDataSource dataSource = new MysqlDataSource();

    public DataSource() {
        dataSource.setServerName("159.89.19.132");
        dataSource.setPort(3306);
        dataSource.setUser("tomcat8 server");
        dataSource.setPassword("Banana420=");
        dataSource.setDatabaseName("cupcakeshop");
    }

    public MysqlDataSource getDataSource() {
        return dataSource;
    }
    
    
}
