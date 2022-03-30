package com.jeffDev.DataPersistency.Domein;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reiziger {
    private int reizigerID;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OvChipkaart> ovChipkaarten = new ArrayList<>();

    public Reiziger(){}
    public Reiziger(int ID, String voorletters, String tussenvoegsel, String achternaam, java.sql.Date geboortedatum) {
        this.reizigerID = ID;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;

    }

    public int getReizigerID() {
        return reizigerID;
    }

    public void setReizigerID(int reizigerID) {
        this.reizigerID = reizigerID;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Adres getAdres(){return adres;}

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OvChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(List<OvChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public String toString() {
        if (tussenvoegsel == null) {
            return "Reiziger:" + voorletters + ". " + achternaam + " geboren op: " + geboortedatum;
        } else {
            return "Reiziger:" + voorletters + ". " + tussenvoegsel + " " + achternaam + " geboren op: " + geboortedatum;
        }
    }
}