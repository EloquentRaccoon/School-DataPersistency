package com.jeffDev.DataPersistency.SQL;



import com.jeffDev.DataPersistency.Interface.AdresDAO;
import com.jeffDev.DataPersistency.Object.Adres;
import com.jeffDev.DataPersistency.Object.Reiziger;

import java.sql.*;
import java.util.*;


import static java.lang.String.format;

public class AdresDAOPsql implements AdresDAO {
    private static Connection connection = null;


    private static Connection getConnection() throws SQLException {
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

    public List<Adres> findAll() {
        try {
            List<Adres> adressen = new ArrayList<Adres>();
            String query = "SELECT * FROM adres;";
            PreparedStatement statement = getConnection().prepareStatement(query);
            ResultSet set = statement.executeQuery();

            while (set != null && set.next()) {
                Adres adres = new Adres(
                        set.getInt("adres_id"),
                        set.getString("straat"),
                        set.getString("huisnummer"),
                        set.getString("woonplaats"),
                        set.getString("postcode"),
                        set.getInt("reiziger_id")
                );
                        adressen.add(adres);
            }
            closeConnection();
            return adressen;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Adres findByReiziger(Reiziger reiziger){
        try {
            String query = "SELECT * FROM adres WHERE reiziger_id = ?;";
            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setInt(1, reiziger.getReizigerID());
            ResultSet set = statement.executeQuery();
            while(set.next()) {
                return buildAdresObject(set);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public Adres findById(int adresId) {
        try {
            String query = "SELECT * FROM adres WHERE adres_id = ?;";
            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setInt(1, adresId);
            ResultSet set = statement.executeQuery();
            while(set.next()) {
                return buildAdresObject(set);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public Adres save(Adres adres) {
        try {
            String query = "INSERT INTO adres (adres_id, straat, huisnummer, woonplaats, postcode, reiziger_id) VALUES (?,?, ?, ?, ?, ?);";

            PreparedStatement statement = getConnection().prepareStatement(query);

            statement.setInt(1,adres.getAdresID());
            statement.setString(2, adres.getStraat());
            statement.setString(3, adres.getHuisnummer());
            statement.setString(4, adres.getWoonplaats());
            statement.setString(5, adres.getPostcode());
            statement.setInt(6, adres.getReizigerID());

            statement.executeUpdate();

            ResultSet set = statement.getGeneratedKeys();
            closeConnection();
            if(set.next()) {
                return findById(set.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Adres update(Adres adres) {

        try {
            String query =
                    "UPDATE adres SET straat = ?, huisnummer = ?, woonplaats = ?, postcode = ?, reiziger_id = ?" +
                            "WHERE adres_id = ?";
            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setString(1, adres.getStraat());
            statement.setString(2, adres.getHuisnummer());
            statement.setString(3, adres.getWoonplaats());
            statement.setString(4, adres.getPostcode());
            statement.setInt(5, adres.getReizigerID());
            statement.setInt(6,adres.getAdresID());

            statement.executeUpdate();

            ResultSet set = statement.getGeneratedKeys();
            if (set.rowUpdated()) {
                return findById(adres.getAdresID());
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean delete(Adres adres) {
        try {
            String query = "DELETE FROM adres WHERE adres_id = ?";
            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setInt(1, adres.getAdresID());
            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Adres buildAdresObject(ResultSet set) throws  SQLException {
        Adres adres = new Adres();
        adres.setAdresID(set.getInt("adres_id"));
        adres.setStraat(set.getString("straat"));
        adres.setHuisnummer(set.getString("huisnummer"));
        adres.setWoonplaats(set.getString("woonplaats"));
        adres.setPostcode(set.getString("postcode"));
        adres.setReizigerID(set.getInt("reiziger_id"));

        return adres;
    }

}
