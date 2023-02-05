package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.*;

public class UserDaoJDBCImpl extends Util implements UserDao {


    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() throws SQLException {
        Util.executeUpdate("CREATE TABLE IF NOT EXISTS `pred_progect1`.`user` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastName` VARCHAR(45) NOT NULL,\n" +
                "  `age` INT(3) NOT NULL,\n" +
                "  PRIMARY KEY (`id`))");
    }


    public void dropUsersTable() throws SQLException {
        Util.executeUpdate("DROP TABLE IF EXISTS user;");
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        executeUpdate(
                "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)",
                Map.of(1, name, 2, lastName, 3, age));
    }

    public void removeUserById(long id) throws SQLException {
        Util.executeUpdate(
                "DELETE FROM user WHERE id = ?;",
                Map.of(1, id));

    }

    public List<User> getAllUsers() throws SQLException {

        return Util.executeQuery("SELECT * FROM user",
                resultSet -> {
                    List<User> allUsers = new ArrayList<>();
                    try {
                        while (resultSet.next()) {
                            User user = new User();
                            user.setId(resultSet.getLong("id"));
                            user.setName(resultSet.getString("name"));
                            user.setLastName(resultSet.getString("lastName"));
                            user.setAge(resultSet.getByte("age"));

                            allUsers.add(user);
                        }
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                    return allUsers;

                });
    }

    public void cleanUsersTable() throws SQLException {
        Util.executeUpdate("DELETE FROM user");
    }
}
