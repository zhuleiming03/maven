package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtil {

    private static String url;
    private static String username;
    private static String password;

    static {
        try {
            InputStream inputStream = JdbcUtil.class.getClassLoader().getResourceAsStream("application.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            String driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

            Class.forName(driver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
