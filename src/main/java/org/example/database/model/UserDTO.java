package org.example.database.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class UserDTO {
    @Getter@Setter
    private Integer id;
    @Getter@Setter
    private String name;
    @Getter@Setter
    private int age;

    public UserDTO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserDTO(int id, String name, int age) {
        this(name, age);
        this.id = id;
    }
}
