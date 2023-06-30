package org.example.database.dao;

import org.example.database.model.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class JdbcUserDao implements UserDao {

    private Connection connection;

    public JdbcUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(UserDTO user) {
        String insertQuery = "INSERT INTO users (name, age) VALUES (?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getAge());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(int id) {
        String deleteQuery = "DELETE FROM users WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            int countRows = preparedStatement.executeUpdate();
            return countRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserDTO user) {
        String updateQuery = "UPDATE users SET age = ?, name = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, user.getAge());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserDTO> selectAll() {
        String selectQuery = "SELECT * FROM users;";
        List<UserDTO> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    int age = resultSet.getInt(3);
                    users.add(new UserDTO(id, name, age));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasIdInTable(int id) {
        String hasIdQuery = "SELECT COUNT(*) AS count FROM users WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(hasIdQuery)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();

                if (resultSet.getInt("count") >= 1) {
                    return true;
                }

            }

            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
