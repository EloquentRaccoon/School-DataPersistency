package com.jeffDev.DataPersistency;

import com.jeffDev.DataPersistency.Interface.AdresDAO;
import com.jeffDev.DataPersistency.Object.Adres;
import com.jeffDev.DataPersistency.Object.Reiziger;
import com.jeffDev.DataPersistency.Interface.ReizigerDAO;
import com.jeffDev.DataPersistency.SQL.AdresDAOPsql;
import com.jeffDev.DataPersistency.SQL.ReizigerDAOPsql;

import java.sql.*;
import java.util.List;

import static java.lang.String.format;

public class Main {
    private static Connection connection = null;

    public static void main(String[] args) throws SQLException {

        ReizigerDAOPsql reizigerDaoPsql = new ReizigerDAOPsql();
        testReizigerDAO(reizigerDaoPsql);
        AdresDAOPsql adresDaoPsql = new AdresDAOPsql();
        testAdresDAO(adresDaoPsql);

    }

    private static Connection getConnection() throws SQLException {

        if (connection == null) {
            String databaseName = "ovchip";
            String userName = "postgres";
            String password = "qwerty";

            String url = format(
                    "jdbc:postgresql://localhost/%s?user=%s&password=%s", databaseName, userName, password
            );

            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    private static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     * <p>
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
        System.out.println("-----[Test create and presist reiziger to database]-----");
        String gbdatum = "1981-03-14";
        int reizigerID = 66;
        while (rdao.findById(reizigerID) != null) {
            reizigerID += 1;
        }
        Reiziger sietske = new Reiziger(reizigerID, "S", null, "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        // Update de gbdatum van reiziger sietske en persisteer deze in de database
        System.out.println("-----[Test update and presist reiziger to database]-----");
        String newGbdatum = "1992-03-14";
        Reiziger updateSietske;
        updateSietske = rdao.findById(reizigerID);
        System.out.print("[Test] Sietske eerst: " + updateSietske + " \n[Test] Siettske na update: ");
        updateSietske.setGeboortedatum(java.sql.Date.valueOf(newGbdatum));
        rdao.update(updateSietske);
        System.out.println(updateSietske + "\n");

        // Delete reiziger Sietske uit de database
        System.out.println("-----[Test Delete reiziger from database]-----");
        Reiziger deleteSietske;
        deleteSietske = rdao.findById(reizigerID);
        System.out.print("[Test] Check bestaat Sietske " + deleteSietske + " \n[Test] Sietske na delete: ");
        rdao.delete(deleteSietske);
        deleteSietske = rdao.findById(reizigerID);
        System.out.println(deleteSietske + "\n");
    }

    private static void testAdresDAO(AdresDAO adao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle adressen op uit de database
        System.out.println("-----[Test pull adressen from database]-----");
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuw adres aan en persisteer deze in de database
        System.out.println("-----[Test create and presist adres to database]-----");
        int adresID = 50;
        while (adao.findById(adresID) != null) {
            adresID += 1;
        }
        Adres nieuwAdres = new Adres(adresID, "De latijnsamericalaan", "5", "DELFT", "2622KL", 6);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(nieuwAdres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");


        // Update de huisnummer van het adres en persisteer deze in de database
        System.out.println("-----[Test update and presist adres to database]-----");
        Adres updateAdres = adao.findById(adresID);
        System.out.print("[Test] Adres eerst: " + updateAdres + " \n[Test] na update: ");
        updateAdres.setStraat("De mirandastraat");
        updateAdres.setHuisnummer("3");
        updateAdres.setPostcode("2622 BN");
        adao.update(updateAdres);
        System.out.println(updateAdres + "\n");

        // Delete adres uit de database
        System.out.println("-----[Test Delete adres from database]-----");
        Adres deleteAdres = adao.findById(adresID);
        System.out.print("[Test] Check bestaat adres: " + deleteAdres + " \n[Test] na delete: ");
        adao.delete(deleteAdres);
        deleteAdres = adao.findById(adresID);
        System.out.println(deleteAdres + "\n");
    }
}
