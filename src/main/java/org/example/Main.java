package org.example;

import org.example.database.db.DBConnection;
import org.example.database.db.DBStatement;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, IllegalAccessException {
        DBConnection connection = new DBConnection();
        System.out.println("Подключение к базе данных: " + connection.connect() + "\n" + "_____________________");
        while (true) {

        }
    }
}