package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl methodsForUsers1 = new UserServiceImpl();
        methodsForUsers1.createUsersTable();
        methodsForUsers1.saveUser("Екатерина", "Иванова", (byte) 24);
        methodsForUsers1.saveUser("Максим", "Рябов", (byte) 25);
        methodsForUsers1.saveUser("Вадим", "Рябов", (byte) 19);
        methodsForUsers1.saveUser("Ральф", "Рябов", (byte) 4);
        for (User user : methodsForUsers1.getAllUsers()) {
            System.out.println(user);
        }
        methodsForUsers1.cleanUsersTable();
        methodsForUsers1.dropUsersTable();
    }
}
