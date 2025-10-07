package com.t2404e.baitap1.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlHelper {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/hello_t2404e?useSSL=false&serverTimezone=UTC";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(DATABASE_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL Driver not found: " + e.getMessage());
        }
    }

    // ✅ luôn mở mới connection mỗi lần được gọi
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            // System.out.println("Connected to database successfully");
            return conn;
        } catch (SQLException e) {
            System.out.println("❌ Unable to connect to database: " + e.getMessage());
            return null;
        }
    }

    // ✅ đóng connection an toàn
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                // System.out.println("Connection closed successfully");
            } catch (SQLException e) {
                System.out.println("⚠️ Error closing connection: " + e.getMessage());
            }
        }
    }

    // quick test
    public static void main(String[] args) {
        try (Connection conn = MySqlHelper.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connection test OK!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
