package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl methodsForUsers1 = new UserServiceImpl();
        UserDaoHibernateImpl methodForUsers = new UserDaoHibernateImpl();
        methodForUsers.createUsersTable();
        methodForUsers.saveUser("Екатерина", "Иванова", (byte) 24);
        methodForUsers.saveUser("Максим", "Рябов", (byte) 25);
        methodForUsers.saveUser("Вадим", "Рябов", (byte) 19);
        methodForUsers.saveUser("Ральф", "Рябов", (byte) 4);
        for (User user : methodForUsers.getAllUsers()) {
            System.out.println(user);
        }
        methodForUsers.cleanUsersTable();
        methodForUsers.dropUsersTable();
    }
}
