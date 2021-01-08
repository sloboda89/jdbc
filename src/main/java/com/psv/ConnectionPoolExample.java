package com.psv;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPoolExample {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5432;
    private static final String DATABASE = "cursor_jdbc_sql";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        String url = "jdbc:postgresql://" + HOST + ":"+ PORT + "/" + DATABASE;

        String sqlQuery = "SELECT * FROM pg_catalog.pg_tables WHERE schemaname = ?;";

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(url);
        config.setUsername(USER);
        config.setPassword(PASSWORD);

        config.setMaximumPoolSize(10);
        config.setAutoCommit(false);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        DataSource dataSource = new HikariDataSource(config);

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
