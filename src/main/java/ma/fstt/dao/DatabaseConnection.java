package ma.fstt.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static String url = "jdbc:mysql://localhost:3306/mydb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static String user = "javauser";
    private static String password = "MyStrongPassword123!";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties");
            if (input != null) {
                Properties prop = new Properties();
                prop.load(input);
                if (prop.getProperty("db.url") != null) {
                    url = prop.getProperty("db.url");
                }
                if (prop.getProperty("db.user") != null) {
                    user = prop.getProperty("db.user");
                }
                if (prop.getProperty("db.password") != null) {
                    password = prop.getProperty("db.password");
                }
                if (prop.getProperty("db.driver") != null) {
                    driver = prop.getProperty("db.driver");
                }
            }
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
