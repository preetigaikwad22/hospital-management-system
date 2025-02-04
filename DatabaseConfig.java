package HospitalManagementSystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getResourceAsStream("/HospitalManagementSystem/config.properties")) {
            if (input == null) {
                throw new IOException("config.properties file not found");
            }
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Failed to load database configuration");
            e.printStackTrace();
        }
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUserName() {
        return properties.getProperty("db.user");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}
