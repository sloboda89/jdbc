package com.psv;

import java.sql.*;

public class StatementExample {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5432;
    private static final String DATABASE = "jdbc_sql";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        String url = "jdbc:postgresql://" + HOST + ":"+ PORT + "/" + DATABASE;
        String sqlQuery = "SELECT * FROM pg_catalog.pg_tables WHERE schemaname = 'public';";
        try (Connection connection = DriverManager.getConnection(url, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                System.out.println(resultSet.getString("tablename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
