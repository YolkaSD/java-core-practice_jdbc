package org.example.database.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBStatement {
    private Connection connection;

    public DBStatement(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public int executeInsert(String name, int age) throws SQLException {
        String insertQuery = "INSERT INTO users (name, age) VALUES(?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)){
            statement.setString(1, name);
            statement.setInt(2, age);
            return statement.executeUpdate();
        }
    }

}
