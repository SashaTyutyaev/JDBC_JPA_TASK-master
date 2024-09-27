package vladdossik.jdbc.jpa.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import vladdossik.jdbc.jpa.model.User;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class Util {
    private static SessionFactory sessionFactory;
    private static Connection connection;

    public static Connection getConnectionJDBC() {
        if (connection != null) {
            log.info("Connection is already open");
            return connection;
        }

        try {
            Properties properties = loadProperties();
            connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            );
        } catch (SQLException e) {
            log.error("Could not connect to database");
            throw new RuntimeException(e);
        }
        log.info("Connected to database: {}", "preproject1_db");
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.addProperties(loadProperties());
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                log.info("Session factory created");
                return sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                log.error("Failed to create session factory");
                if (sessionFactory != null) {
                    StandardServiceRegistryBuilder.destroy(sessionFactory
                            .getSessionFactoryOptions()
                            .getServiceRegistry());
                }
                throw new RuntimeException(e);
            }
        }
        log.info("Session factory is already created");
        return sessionFactory;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream("src/main/java/resources/application.properties")) {
            properties.load(fis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
