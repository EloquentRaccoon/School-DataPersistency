package com.jeffDev.DataPersistency.Domein;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private List<OvChipkaart> ovChipkaarten = new ArrayList<>();

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.productNummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public int getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(int product_nummer) {
        this.productNummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public List<OvChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(List<OvChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public String toString() {
        return "Product: productnummer " + productNummer + ", naam '" + naam + ", beschrijving '" + beschrijving + ", prijs " + prijs;
    }
}
