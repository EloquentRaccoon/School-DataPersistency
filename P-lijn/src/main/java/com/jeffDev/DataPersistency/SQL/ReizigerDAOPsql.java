package com.jeffDev.DataPersistency.SQL;

import com.jeffDev.DataPersistency.Object.Reiziger;
import com.jeffDev.DataPersistency.Interface.ReizigerDAO;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import static java.lang.String.format;

public class ReizigerDAOPsql implements ReizigerDAO {
    private static Connection connection = null;
    private AdresDAOPsql adresDAOPsql = new AdresDAOPsql();


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

    public List<Reiziger> findAll() {
        try {
            List<Reiziger> reizigers = new ArrayList<Reiziger>();
            String query = "SELECT * FROM reiziger;";
            PreparedStatement statement = getConnection().prepareStatement(query);
            ResultSet set = statement.executeQuery();

            while (set != null && set.next()) {
                reizigers.add(buildReizigerObject(set));
            }

            closeConnection();
            return reizigers;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Reiziger findById(int id) {
        try {
            String query = "SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum " +
                    "FROM reiziger WHERE reiziger_id = ?;";

            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();

            closeConnection();
            if(set.next()) {
                return buildReizigerObject(set);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Reiziger> findByGBdatum(Date GBdatum) {
        try {
            String query = "SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum " +
                    "FROM reiziger WHERE geboortedatum = ?;";
            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setDate(1, GBdatum);

            ResultSet set = statement.executeQuery();

            ArrayList<Reiziger> reizigers = new ArrayList<Reiziger>();
            while (set != null && set.next()) {
                reizigers.add(buildReizigerObject(set));
            }
            closeConnection();
            return reizigers;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Reiziger save(Reiziger reiziger) {
        try {
            String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?,?, ?, ?, ?);";

            PreparedStatement statement = getConnection().prepareStatement(query);

            statement.setInt(1, reiziger.getReizigerID());
            statement.setString(2, reiziger.getVoorletters());
            statement.setString(3, reiziger.getTussenvoegsel());
            statement.setString(4, reiziger.getAchternaam());
            statement.setDate(5, (Date) reiziger.getGeboortedatum());

            statement.executeUpdate();
            //ResultSet set = statement.getGeneratedKeys();
            closeConnection();
//            if(set.next()) {
//                return findById(set.getInt(1));
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Reiziger update(Reiziger reiziger) {
        try {
            String query =
                    "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? " +
                            "WHERE reiziger_id = ?";
            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setString(1, reiziger.getVoorletters());
            statement.setString(2, reiziger.getTussenvoegsel());
            statement.setString(3, reiziger.getAchternaam());
            statement.setDate(4, (Date) reiziger.getGeboortedatum());
            statement.setInt(5, reiziger.getReizigerID());
            statement.executeUpdate();

//            ResultSet set = statement.getGeneratedKeys();
            closeConnection();
//            if (set.rowUpdated()) {
//                return findById(reiziger.getReizigerID());
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Reiziger delete(Reiziger reiziger) {
        try {
            String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setInt(1, reiziger.getReizigerID());
            statement.executeUpdate();

            closeConnection();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Reiziger buildReizigerObject(ResultSet set) throws SQLException {
        try {
            Reiziger reiziger = new Reiziger();
            reiziger.setReizigerID(set.getInt("reiziger_id"));
            reiziger.setVoorletters(set.getString("voorletters"));
            reiziger.setTussenvoegsel(set.getString("tussenvoegsel"));
            reiziger.setAchternaam(set.getString("achternaam"));
            reiziger.setGeboortedatum(set.getDate("geboortedatum"));

            if (this.adresDAOPsql != null) {
                reiziger.setAdres(adresDAOPsql.findByReiziger(reiziger));
            }

            return reiziger;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
