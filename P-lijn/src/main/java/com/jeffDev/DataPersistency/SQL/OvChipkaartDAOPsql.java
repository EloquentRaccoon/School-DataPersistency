package com.jeffDev.DataPersistency.SQL;

import com.jeffDev.DataPersistency.Object.OvChipkaart;
import com.jeffDev.DataPersistency.Object.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class OvChipkaartDAOPsql {
    private static Connection connection = null;


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

    public List<OvChipkaart> findAll() {
        try {
            List<OvChipkaart> kaarten = new ArrayList<OvChipkaart>();
            String query = "SELECT * FROM ov_chipkaart;";
            PreparedStatement statement = getConnection().prepareStatement(query);
            ResultSet set = statement.executeQuery();

            while (set != null && set.next()) {
                kaarten.add(buildOvChipkaart(set));
            }
            closeConnection();
            return kaarten;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public OvChipkaart findByKaartnummer(int kaartnummer) throws SQLException {
        try {
            String query = "SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?;";
            PreparedStatement statement = getConnection().prepareStatement(query);

            statement.setInt(1, kaartnummer);
            ResultSet set = statement.executeQuery();

            closeConnection();
            if(set.next()) {
                return buildOvChipkaart(set);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<OvChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        try {
            String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?;";
            PreparedStatement statement = getConnection().prepareStatement(query);

            statement.setInt(1, reiziger.getReizigerID());
            ResultSet set = statement.executeQuery();

            List<OvChipkaart> kaarten = new ArrayList<>();
            while (set != null && set.next()) {
                kaarten.add(buildOvChipkaart(set));
            }
            closeConnection();
            return kaarten;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OvChipkaart save(OvChipkaart ovChipkaart) throws SQLException {
        try {
            String query =
                    "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) " +
                            "VALUES (?, ?, ?, ?, ?);";
            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setInt(1, ovChipkaart.getKaartNummer());
            statement.setDate(2, (Date) ovChipkaart.getAfloopDatum());
            statement.setInt(3, ovChipkaart.getKlasse());
            statement.setDouble(4, ovChipkaart.getSaldo());
            statement.setInt(5, ovChipkaart.getReizigerId());

            statement.executeUpdate();

            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public OvChipkaart update(OvChipkaart ovChipkaart) throws SQLException {
        try {
            String query = "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?;";
            PreparedStatement statement = getConnection().prepareStatement(query);

            statement.setDate(1, (Date) ovChipkaart.getAfloopDatum());
            statement.setInt(2, ovChipkaart.getKlasse());
            statement.setDouble(3, ovChipkaart.getKlasse());
            statement.setInt(4, ovChipkaart.getReizigerId());
            statement.setInt(5, ovChipkaart.getKaartNummer());

            statement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OvChipkaart delete(OvChipkaart ovChipkaart) throws SQLException {
        try {
            String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?;";
            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setInt(1, ovChipkaart.getKaartNummer());

            statement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private OvChipkaart buildOvChipkaart(ResultSet set) throws SQLException {
        try {
            OvChipkaart ovChipkaart = new OvChipkaart(
                    set.getInt("kaart_nummer"),
                    set.getDate("geldig_tot"),
                    set.getInt("klasse"),
                    set.getDouble("saldo"),
                    set.getInt("reiziger_id")
            );

            ReizigerDAOPsql reizigerDAOPsql = new ReizigerDAOPsql();
            ovChipkaart.setReiziger(reizigerDAOPsql.findById(ovChipkaart.getReizigerId()));

            return ovChipkaart;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
