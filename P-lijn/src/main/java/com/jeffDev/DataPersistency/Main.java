package com.jeffDev.DataPersistency;

import java.sql.*;

import static java.lang.String.format;

public class Main {
    private static Connection connection = null;

    public static void main(String[] args) throws SQLException {
        testConnection();
        System.out.println("Hello");

    }
    private static Connection getConnection() throws SQLException{

        if (connection == null) {
            String databaseName = "ovchip";
            String userName = "postgres";
            String password = "qwerty";

            String url = format(
                    "jdbc:postgresql://localhost/%s?user=%s&password=%s",databaseName,userName,password
            );

            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    private static void closeConnection() throws SQLException{
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    private static void testConnection() throws SQLException{
        getConnection();

        String query = "SELECT * FROM reiziger;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();

        while (set != null && set.next()) {
            System.out.println(set.getString("achternaam"));
        }
        closeConnection();
    }

}
