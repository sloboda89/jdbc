package com.psv;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;

public class DataSourceExample {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5432;
    private static final String DATABASE = "cursor_jdbc_sql";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        String url = "jdbc:postgresql://" + HOST + ":"+ PORT + "/" + DATABASE;
        String sqlQuery = "SELECT * FROM pg_catalog.pg_tables WHERE schemaname = ?;";

        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setUrl(url);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, "public");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("tablename"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
