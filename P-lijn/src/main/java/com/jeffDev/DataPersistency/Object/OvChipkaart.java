package com.jeffDev.DataPersistency.Object;

import java.util.Date;

public class OvChipkaart {
    private int kaartNummer;
    private Date afloopDatum;
    private int klasse;
    private double saldo;
    private int reizigerId;
    private Reiziger reiziger;

    public OvChipkaart(){}
    public OvChipkaart(int kaartNummer, Date afloopDatum, int klasse, double saldo, int reizigerId){
        this.kaartNummer = kaartNummer;
        this.afloopDatum = afloopDatum;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reizigerId = reizigerId;
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public void setKaartNummer(int kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public Date getAfloopDatum() {
        return afloopDatum;
    }

    public void setAfloopDatum(Date afloopDatum) {
        this.afloopDatum = afloopDatum;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getReizigerId() {
        return reizigerId;
    }

    public void setReizigerId(int reizigerId) {
        this.reizigerId = reizigerId;
    }

    public Reiziger getReiziger() { return reiziger; }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public String toString() {
        return "OvChipkaart: " +
                "kaartNummer='" + kaartNummer + '\'' +
                ", geldig tot=" + afloopDatum +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reizigerId=" + reizigerId;
    }
}
