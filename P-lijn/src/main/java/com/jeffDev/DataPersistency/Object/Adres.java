package com.jeffDev.DataPersistency.Object;

public class Adres {
    private int adresID;
    private String straat;
    private String huisnummer;
    private String woonplaats;
    private String postcode;
    private int reizigerId;

    public Adres(){}
    public Adres(int adresID, String straat, String huisnummer, String woonplaats, String postcode, int reizigerID){
        this.adresID = adresID;
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.woonplaats = woonplaats;
        this.postcode = postcode;
        this.reizigerId = reizigerID;
    }

    public int getAdresID() { return adresID; }

    public void setAdresID(int adresID) { this.adresID = adresID; }

    public String getStraat() { return straat; }

    public void setStraat(String straat) { this.straat = straat; }

    public String getHuisnummer() { return huisnummer; }

    public void setHuisnummer(String huisnummer) { this.huisnummer = huisnummer; }

    public String getWoonplaats() { return woonplaats; }

    public void setWoonplaats(String woonplaats) { this.woonplaats = woonplaats; }

    public String getPostcode() { return postcode; }

    public void setPostcode(String postcode) { this.postcode = postcode; }

    public int getReizigerID() { return reizigerId; }

    public void setReizigerID(int reizigerID) { this.reizigerId = reizigerID; }

    public String toString(){
        return "Adres:" + straat + ". " + huisnummer + ", " + woonplaats + " " + postcode + " Is het adres van: " + reizigerId;
    }
}
