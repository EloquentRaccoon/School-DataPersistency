package com.jeffDev.DataPersistency;

import java.sql.*;
import java.util.List;

import static java.lang.String.format;

public class Main {
    private static Connection connection = null;

    public static void main(String[] args) throws SQLException {
        getAllRijzigers();
        ReizigerDAOPsql daoPsql = new ReizigerDAOPsql();
        testReizigerDAO(daoPsql);

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

    private static void getAllRijzigers() throws SQLException{
        getConnection();

        String query = "SELECT * FROM reiziger;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();

        while (set != null && set.next()) {
            System.out.println(set.getString("achternaam"));
        }
        closeConnection();
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        System.out.println("-----[Test pull reizigers from database]-----");
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        System.out.println("-----[Test create and presist to database]-----");
        String gbdatum = "1981-03-14";
        int reizigerID = 66;
        while (rdao.findById(reizigerID) != null){
            reizigerID += 1;
        }
        Reiziger sietske = new Reiziger(reizigerID, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        // Update de gbdatum van reiziger sietske en persisteer deze in de database
        System.out.println("-----[Test update and presist to database]-----");
        String newGbdatum = "1992-03-14";
        Reiziger updateSietske;
        updateSietske = rdao.findById(reizigerID);
        System.out.print("[Test] Sietske eerst: " + updateSietske + " , na update: ");
        updateSietske.setGeboortedatum(java.sql.Date.valueOf(newGbdatum));
        rdao.update(updateSietske);
        System.out.println(updateSietske + "\n");

        // Delete reiziger Sietske uit de database
        System.out.println("-----[Test Delete from database]-----");
        Reiziger deleteSietske;
        deleteSietske = rdao.findById(reizigerID);
        System.out.print("[Test] Check bestaat Sietske " + deleteSietske + " , na delete: ");
        rdao.delete(deleteSietske);
        deleteSietske = rdao.findById(reizigerID);
        System.out.println(deleteSietske + "\n");
    }
}
