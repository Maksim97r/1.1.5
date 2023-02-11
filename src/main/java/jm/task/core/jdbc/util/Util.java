package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.*;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/pred_progect1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection OK");
        } catch (SQLException e) {
            System.out.println("Connection ERROR ");
        }
        return connection;
    }

    public static void executeUpdate(String query) throws SQLException {
        executeUpdate(query, Collections.emptyMap());
    }

    public static void executeUpdate(String query, Map<Integer, Object> queryArguments) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            fillPreparedStatement(preparedStatement, queryArguments).executeUpdate();
        }
    }


    public static <T> T executeQuery(String query, Function<ResultSet, T> resultSetHandler) throws SQLException {
        return executeQuery(query, Collections.emptyMap(), resultSetHandler);
    }

    public static <T> T executeQuery(String query, Map<Integer, Object> queryArguments, Function<ResultSet, T> resultSetHandler) throws SQLException {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = fillPreparedStatement(preparedStatement, queryArguments).executeQuery()) {
            return resultSetHandler.apply(resultSet);
        }
    }

    private static PreparedStatement fillPreparedStatement(PreparedStatement preparedStatement, Map<Integer, Object> queryArguments) throws SQLException {

        for (Map.Entry<Integer, Object> entry : queryArguments.entrySet()) {
            int key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Long) {
                preparedStatement.setLong(key, (Long) value);
            } else if (value instanceof String) {
                preparedStatement.setString(key, (String) value);
            } else if (value instanceof Byte) {
                preparedStatement.setByte(key, (Byte) value);
            } else if (value instanceof Integer) {
                preparedStatement.setInt(key, (Integer) value);
            } else {
                preparedStatement.setObject(key, value);
            }
        }

        return preparedStatement;
    }

    public static SessionFactory getFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, DRIVER);
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USERNAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, true);
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Ошибка при подключении к БД");
            }
        }
        return sessionFactory;
    }
}
