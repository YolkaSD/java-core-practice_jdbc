package org.example;

import org.example.database.dao.JdbcUserDao;
import org.example.database.db.DBConnection;
import org.example.database.db.DBStatement;
import org.example.database.model.UserDTO;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        DBConnection connection = new DBConnection();
        DBStatement dbStatement = new DBStatement(connection.connect());
        JdbcUserDao jdbcUserDao = new JdbcUserDao(connection.connect());
        System.out.println("Подключение к базе данных: " + connection.connect() + "\n" + "_____________________");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите одну из команд:");
            System.out.println("1. Создать отношение Users");
            System.out.println("2. Удалить отношение Users");
            System.out.println("3. Внести запись отношение Users");
            System.out.println("4. Удалить запись в отношении Users по id");
            System.out.println("5. Обновить запись в отношении Users по id");
            System.out.println("6. Вывести все записи в отношении Users");

            int id;
            int age;
            String name;
            switch (scanner.nextLine()) {
                case "1":
                    System.out.println("Статус операции: " + dbStatement.createTableUsers());
                    break;
                case "2":
                    System.out.println("Статус операции: " + dbStatement.deleteTableUsers());
                    break;
                case "3":
                    System.out.println("Введите данные (имя, возраст) для их внесения в отношение users");
                    System.out.print("Имя: ");
                    name = scanner.next();
                    System.out.print("Возраст: ");
                    age = scanner.nextInt();
                    System.out.println("Статус операции: " + jdbcUserDao.insert(new UserDTO(name, age)));
                    break;
                case "4":
                    System.out.println("Введите id пользователя для удаления");
                    System.out.print("id: ");
                    id = scanner.nextInt();
                    boolean deleteStatus = jdbcUserDao.deleteById(id);
                    if (deleteStatus) {
                        System.out.println("Статус операции: " + true);
                    } else {
                        System.out.println("Статус операции: " + false);
                    }
                    break;

                case "5":
                    System.out.println("Введите id пользователя для изменения данных");
                    System.out.print("id: ");
                    id = scanner.nextInt();
                    if (jdbcUserDao.hasIdInTable(id)) {
                        System.out.println("Введите данные (имя, возраст) для внесения новых данных в отношение users");
                        System.out.print("Имя: ");
                        name = scanner.next();
                        System.out.print("Возраст: ");
                        age = scanner.nextInt();
                        jdbcUserDao.update(new UserDTO(id, name, age));
                        System.out.println("Статус операции: " + true);
                    } else {
                        System.out.println("Статус операции: " + false);
                    }
                    break;

                case "6":
                    System.out.println("id\tname\tage");
                    for (UserDTO user: jdbcUserDao.selectAll()) {
                        System.out.print(user.getId() + "\t" + user.getName() + "\t" + user.getAge() + "\n");
                    }
                    break;
            }

            System.out.println("_____________________");
        }
    }
}