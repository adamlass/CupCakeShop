package dbconnector;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.text.html.HTMLDocument;

public class DBConnector {

    private DataSource dataSource;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public DBConnector() {
    }

    public DBConnector(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        return connection;
    }

    public void open() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = dataSource.getConnection();
        }
    }

    public Statement getStatement() throws SQLException {
        return connection.createStatement();
    }

    public void close() throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }

        if (statement != null) {
            statement.close();
        }

        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }

    public ResultSet query(String sql) throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);

        return resultSet;
    }

    public void update(String sql) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate(sql);
    }
    public PreparedStatement preparedStatement(String sql) throws SQLException{
        return connection.prepareStatement(sql);
    }
}
