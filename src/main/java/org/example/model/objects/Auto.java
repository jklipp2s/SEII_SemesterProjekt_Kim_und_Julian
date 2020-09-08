package org.example.model.objects;

import java.util.Date;

public class Auto {
    private String fahrzeugIdentifikationsNummer;
    private String marke;
    private int baujahr;
    private String beschreibung;
    private int ps;
    private double verbrauch;
    private double verkaufsPreis;
    private boolean verfuegbar;

    public Auto() {

    }

    public Auto(String fahrzeugIdentifikationsNummer, String marke, int baujahr, String beschreibung, int ps, double verbrauch, double verkaufsPreis, boolean verfuegbar) {
        this.fahrzeugIdentifikationsNummer = fahrzeugIdentifikationsNummer;
        this.marke = marke;
        this.baujahr = baujahr;
        this.beschreibung = beschreibung;
        this.ps = ps;
        this.verbrauch = verbrauch;
        this.verkaufsPreis = verkaufsPreis;
        this.verfuegbar = verfuegbar;
    }

    public String getFahrzeugIdentifikationsNummer() {
        return fahrzeugIdentifikationsNummer;
    }

    public void setFahrzeugIdentifikationsNummer(String fahrzeugIdentifikationsNummer) {
        this.fahrzeugIdentifikationsNummer = fahrzeugIdentifikationsNummer;
    }

    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public int getBaujahr() {
        return baujahr;
    }

    public void setBaujahr(int baujahr) {
        this.baujahr = baujahr;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public double getVerbrauch() {
        return verbrauch;
    }

    public void setVerbrauch(double verbrauch) {
        this.verbrauch = verbrauch;
    }


    public double getVerkaufsPreis() {
        return verkaufsPreis;
    }


    public String getFormattedVerkaufsPreis() {
        return String.format("%.2f €", verkaufsPreis);
    }

    public void setVerkaufsPreis(double verkaufsPreis) {
        this.verkaufsPreis = verkaufsPreis;
    }

    public boolean isVerfuegbar() {
        return verfuegbar;
    }

    public void setVerfuegbar(boolean verfuegbar) {
        this.verfuegbar = verfuegbar;
    }


}


