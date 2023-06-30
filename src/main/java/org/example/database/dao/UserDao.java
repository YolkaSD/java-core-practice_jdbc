package org.example.database.dao;

import lombok.ToString;
import org.example.database.model.UserDTO;
import java.util.List;

public interface UserDao {
    boolean insert(UserDTO user);

    boolean deleteById(int id);

    void update(UserDTO user);

    List<UserDTO> selectAll();

    boolean hasIdInTable(int id);

}
