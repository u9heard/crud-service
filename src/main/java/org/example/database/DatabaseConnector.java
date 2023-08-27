package org.example.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {

    private DataSource dataSource;

    public DatabaseConnector(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    public Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
