package com.psv;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchExample {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5432;
    private static final String DATABASE = "cursor_jdbc_sql";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        String url = "jdbc:postgresql://" + HOST + ":"+ PORT + "/" + DATABASE;

        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setUrl(url);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);

        try (Connection connection = dataSource.getConnection()) {
            connection.createStatement().execute("TRUNCATE team CASCADE;");

            String insertQuery = "INSERT INTO team (id, alias) VALUES (?, ?);";

            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {

                for (int i = 1; i <= 10; i++) {
                    statement.setInt(1, i);
                    statement.setString(2, "team" + i);

                    statement.addBatch();
                }

                statement.executeBatch();
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Rollback with error: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
