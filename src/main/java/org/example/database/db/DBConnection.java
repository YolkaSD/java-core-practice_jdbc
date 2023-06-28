package org.example.database.db;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    Connection connection;

    public Connection connect() throws IOException, SQLException, IllegalAccessException {
        Properties dbProperties = new Properties();
        try (FileReader dbPropertiesReader = new FileReader("src/main/resources/db.properties")){
            dbProperties.load(dbPropertiesReader);
        }
        String url = dbProperties.getProperty("url");
        String username = dbProperties.getProperty("username");
        String password = dbProperties.getProperty("password");

        if (url == null || username == null || password == null) {
            throw new IllegalAccessException("Invalid db.properties file. Missing required properties.");
        }

        return DriverManager.getConnection(url, username, password);
    }
}
