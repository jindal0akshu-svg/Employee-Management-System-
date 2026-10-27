//package com.college.ems.util;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public final class DBConnection {
//    private static final String URL = "jdbc:mysql://localhost:3306/employee_db?useSSL=false&serverTimezone=UTC";
//    private static final String USER = "root";
//    private static final String PASSWORD = "imroot1";
//
//    private DBConnection() {}
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
//}

package com.college.ems.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3306/employee_db?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASSWORD = "imroot1";

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                "MySQL JDBC driver is not available to this application.",
                e
            );
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
