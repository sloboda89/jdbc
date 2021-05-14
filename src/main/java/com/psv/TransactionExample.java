package com.psv;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionExample {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5432;
    private static final String DATABASE = "jdbc_sql";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        String url = "jdbc:postgresql://" + HOST + ":"+ PORT + "/" + DATABASE;
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setUrl(url);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);

        String dropQuery = "DROP TABLE trans_table;";
        String createQuery = "CREATE TABLE trans_table (id SERIAL PRIMARY KEY)";

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement dropStatement = connection.prepareStatement(dropQuery);
                 PreparedStatement createStatement = connection.prepareStatement(createQuery)) {
                createStatement.execute();
                dropStatement.execute();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Rollback with error: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
