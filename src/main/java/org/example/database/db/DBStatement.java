package org.example.database.db;

import java.sql.*;
import java.util.StringJoiner;

public class DBStatement {
    private Connection connection;

    public DBStatement(Connection connection) {
        this.connection = connection;
    }

    public boolean createTableUsers() {
        String createTable = "CREATE TABLE users (" + "id BIGSERIAL NOT NULL PRIMARY KEY, " + "name VARCHAR(60) NOT NULL, " + "age int NOT NULL" + ");";
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(createTable)) {
            statement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.err.println("ОШИБКА: отношение \"users\" уже существует");
        }

        return result;
    }

    public boolean deleteTableUsers() {
        String deleteTable = "DROP TABLE users;";
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(deleteTable)) {
            statement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.err.println("ОШИБКА: таблица \"users\" не существует");
        }
        return result;
    }

    public boolean insertIntoTableUsers(String name, int age) {
        String insertInto = "INSERT INTO users (name, age) VALUES(?, ?);";
        boolean result;
        try (PreparedStatement statement = connection.prepareStatement(insertInto)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void deleteUserFromTableUsers(int id) {
        String deleteUser = "DELETE FROM users WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteUser)) {
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if (result == 0) {
                printErr(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUserFromTableUsers(int id, String name, int age) {
        String updateUser = "UPDATE users SET age = ?, name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateUser)) {
            statement.setInt(1, age);
            statement.setString(2, name);
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasIdInTableUsers(int id) {
        String selectCount = "SELECT COUNT(*) FROM users WHERE id = ?;";
        boolean result = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectCount)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(1) > 0) {
                    result = true;
                } else {
                    printErr(id);
                }
            }
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public String selectAllFromUsers() {
        String selectQuery = "SELECT * FROM users;";
        StringJoiner stringJoiner = new StringJoiner("");
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery); ResultSet resultSet = preparedStatement.executeQuery()) {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnsCount = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= columnsCount; i++) {
                stringJoiner.add(resultSetMetaData.getColumnLabel(i)).add("\t");
            }
            stringJoiner.add("\n");

            while (resultSet.next()) {
                for (int i = 1; i <= columnsCount; i++) {
                    stringJoiner.add(resultSet.getString(i)).add("\t");
                }
                stringJoiner.add("\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stringJoiner.toString();
    }

    private void printErr(int id) {
        System.err.println("Запись по id " + id + " не найдена");
    }

}
