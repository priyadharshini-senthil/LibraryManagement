package com.kce.book.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    private static Connection connection;

    public static Connection getDBConnection() {
        try {

            // Load Oracle Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            if (connection == null || connection.isClosed()) {

                // Database URL
                String url = "jdbc:oracle:thin:@localhost:1521:XE";  
                // If your instance name is ORCL, replace XE with ORCL

                // Connect
                connection = DriverManager.getConnection(
                        url,
                        "system",     // username
                        "system"       // ← REPLACE with your actual password
                );

                System.out.println("DB Connected Successfully: " + connection);
            }

        } catch (Exception e) {
            System.out.println("DB Connection FAILED");
            e.printStackTrace();
        }

        return connection;
    }
}
