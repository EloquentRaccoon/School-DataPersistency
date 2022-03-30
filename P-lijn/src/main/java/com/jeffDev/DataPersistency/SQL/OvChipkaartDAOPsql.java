package com.jeffDev.DataPersistency.SQL;

import com.jeffDev.DataPersistency.Domein.OvChipkaart;
import com.jeffDev.DataPersistency.Domein.Product;
import com.jeffDev.DataPersistency.Domein.Reiziger;
import com.jeffDev.DataPersistency.Interface.ProductDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class OvChipkaartDAOPsql {
    private static Connection connection = null;
    private ProductDAO productDAO;

    public OvChipkaartDAOPsql(Connection connection) {
        OvChipkaartDAOPsql.connection = connection;
    }


    public List<OvChipkaart> findAll() {
        try {
            List<OvChipkaart> kaarten = new ArrayList<OvChipkaart>();
            String query = "SELECT * FROM ov_chipkaart;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet set = statement.executeQuery();

            while (set != null && set.next()) {
                kaarten.add(buildOvChipkaart(set));
            }
            return kaarten;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public OvChipkaart findByKaartnummer(int kaartnummer) throws SQLException {
        try {
            String query = "SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?;";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, kaartnummer);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
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
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, reiziger.getReizigerID());
            ResultSet set = statement.executeQuery();

            List<OvChipkaart> kaarten = new ArrayList<>();
            while (set != null && set.next()) {
                kaarten.add(buildOvChipkaart(set));
            }
            return kaarten;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OvChipkaart save(OvChipkaart ovChipkaart) throws SQLException {
        try {
            String query = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ovChipkaart.getKaartNummer());
            statement.setDate(2, (Date) ovChipkaart.getAfloopDatum());
            statement.setInt(3, ovChipkaart.getKlasse());
            statement.setDouble(4, ovChipkaart.getSaldo());
            statement.setInt(5, ovChipkaart.getReizigerId());
            statement.executeUpdate();

            if (this.productDAO != null) {
                for (Product product : ovChipkaart.getProducten()) {
                    query = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?, ?);";
                    statement = connection.prepareStatement(query);
                    statement.setInt(1, ovChipkaart.getKaartNummer());
                    statement.setInt(2, product.getProductNummer());
                    statement.executeUpdate();
                }
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public OvChipkaart update(OvChipkaart ovChipkaart) throws SQLException {
        try {
            String query = "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?;";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setDate(1, (Date) ovChipkaart.getAfloopDatum());
            statement.setInt(2, ovChipkaart.getKlasse());
            statement.setDouble(3, ovChipkaart.getKlasse());
            statement.setInt(4, ovChipkaart.getReizigerId());
            statement.setInt(5, ovChipkaart.getKaartNummer());
            statement.executeUpdate();

            if (this.productDAO != null) {
                List<Product> newProducts = ovChipkaart.getProducten();
                List<Product> oldProducts = new ArrayList<>();
                List<Product> difference;
                query = "SELECT * FROM product JOIN ov_chipkaart_product WHERE kaart_nummer = (?);";
                statement = connection.prepareStatement(query);
                statement.setInt(1, ovChipkaart.getKaartNummer());
                ResultSet set = statement.executeQuery();

                while (set != null && set.next()) {
                    Product oldProduct = new Product(
                            set.getInt("product_nummer"),
                            set.getString("naam"),
                            set.getString("beschrijving"),
                            set.getDouble("prijs"));
                    oldProducts.add(oldProduct);
                }

                if (newProducts.size() > oldProducts.size()){
                    difference = newProducts;
                    difference.removeAll(oldProducts);
                    for (Product product : difference) {
                        query = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?, ?);";
                        statement = connection.prepareStatement(query);
                        statement.setInt(1, ovChipkaart.getKaartNummer());
                        statement.setInt(2, product.getProductNummer());
                        statement.executeUpdate();
                    }
                } else if (oldProducts.size() > newProducts.size()){
                    difference = oldProducts;
                    difference.removeAll(newProducts);
                    for (Product product : difference) {
                        query = "DELETE FROM ov_chipkaart_product WHERE (kaart_nummer, product_nummer) VALUES (?, ?);";
                        statement = connection.prepareStatement(query);
                        statement.setInt(1, ovChipkaart.getKaartNummer());
                        statement.setInt(2, product.getProductNummer());
                        statement.executeUpdate();
                    }
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OvChipkaart delete(OvChipkaart ovChipkaart) throws SQLException {
        try {
            String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ovChipkaart.getKaartNummer());

            statement.executeUpdate();
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

            ReizigerDAOPsql reizigerDAOPsql = new ReizigerDAOPsql(connection);
            ovChipkaart.setReiziger(reizigerDAOPsql.findById(ovChipkaart.getReizigerId()));

            return ovChipkaart;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
