package webwbd.util;

import org.hibernate.SessionFactory;
import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import webwbd.model.Account;
import webwbd.model.Logging;
import webwbd.model.Request;

import java.util.Properties;

public class HibernateUtil {
    private static final String DB_HOST = Dotenv.load().get("DB_HOST", "localhost");
    private static final String DB_PORT = Dotenv.load().get("DB_PORT", "3306");
    private static final String DB_NAME = Dotenv.load().get("DB_NAME", "WBD_SOAP");
    private static final String DB_USER = Dotenv.load().get("DB_USER", "root");
    private static final String DB_PASSWORD = Dotenv.load().get("DB_PASSWORD", "");

    private static final String DB_URL = String.format("jdbc:mysql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME);

    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        Properties properties = getProperties();

        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(Account.class);
        configuration.addAnnotatedClass(Logging.class);
        configuration.addAnnotatedClass(Request.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties()).build();
        return (SessionFactory) configuration.buildSessionFactory(serviceRegistry);
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        properties.setProperty("hibernate.connection.url", DB_URL);
        properties.setProperty("hibernate.connection.username", DB_USER);
        properties.setProperty("hibernate.connection.password", DB_PASSWORD);
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.current_session_context_class", "thread");
        return properties;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
}
