package com.jeffDev.DataPersistency.SQL;

import com.jeffDev.DataPersistency.Domein.OvChipkaart;
import com.jeffDev.DataPersistency.Domein.Product;
import com.jeffDev.DataPersistency.Domein.Reiziger;
import com.jeffDev.DataPersistency.Interface.OvChipkaartDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql {
    private static Connection connection;
    private OvChipkaartDAO ovChipkaartDAO;

    public ProductDAOPsql(Connection connection) {
        this.connection = connection;
    }

    public List<Product> findAll(){
        try {
            List<Product> producten = new ArrayList<Product>();
            String query = "SELECT * FROM product;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet set = statement.executeQuery();

            while (set != null && set.next()) {
                producten.add(buildProduct(set));
            }
            return producten;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Product findByProductNummer(int productnummer) throws SQLException {
        try{
            String query = "SELECT * FROM product WHERE product_nummer = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,productnummer);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return buildProduct(set);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> findByOvChipkaart(OvChipkaart kaart) throws SQLException {
        try {
            String query = "SELECT * FROM product JOIN ov_chipkaart_product WHERE kaart_nummer = ?;";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, kaart.getKaartNummer());
            ResultSet set = statement.executeQuery();

            List<Product> producten = new ArrayList<>();
            while (set != null && set.next()) {
                producten.add(buildProduct(set));
            }
            return producten;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Product save(Product product) throws SQLException {
        try {
            String query = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, product.getProductNummer());
            statement.setString(2, product.getNaam());
            statement.setString(3, product.getBeschrijving());
            statement.setDouble(4, product.getPrijs());

            if (ovChipkaartDAO != null) {
                for (OvChipkaart ovKaart: product.getOvChipkaarten()) {
                    ovChipkaartDAO.save(ovKaart);
                }
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Product update(Product product) throws SQLException {
        try {
            String query = "UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?;";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, product.getNaam());
            statement.setString(2, product.getBeschrijving());
            statement.setDouble(3, product.getPrijs());
            statement.setInt(4, product.getProductNummer());

            if (ovChipkaartDAO != null) {
                for (OvChipkaart ovKaart: product.getOvChipkaarten()) {
                    ovChipkaartDAO.update(ovKaart);
                }
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Product delete(Product product) throws SQLException {
        try {
            String query = "DELETE FROM product WHERE product_nummer = ?;";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, product.getProductNummer());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Product buildProduct(ResultSet result) throws SQLException {
        try {
            return new Product(
                    result.getInt("product_nummer"),
                    result.getString("naam"),
                    result.getString("beschrijving"),
                    result.getDouble("prijs")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
