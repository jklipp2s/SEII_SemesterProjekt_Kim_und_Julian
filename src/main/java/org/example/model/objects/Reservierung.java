package org.example.model.objects;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Reservierung {

    String marke;
    String fahrzeug;
    int reg_nr;
    LocalDate besichtigungsDatum;

    public Reservierung() {

    }


    public Reservierung(String marke, String fahrzeug, int reg_nr, LocalDate besichtigungsdatum) {
        this.marke = marke;
        this.fahrzeug = fahrzeug;
        this.reg_nr = reg_nr;
        this.besichtigungsDatum = besichtigungsdatum;
    }


    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public String getFahrzeug() {
        return fahrzeug;
    }

    public void setFahrzeug(String fahrzeug) {
        this.fahrzeug = fahrzeug;
    }

    public int getReg_nr() {
        return reg_nr;
    }

    public void setReg_nr(int reg_nr) {
        this.reg_nr = reg_nr;
    }

    public LocalDate  getBesichtigungsDatum() {
        return besichtigungsDatum;
    }

    public void setBesichtigungsDatum(LocalDate besichtigungsDatum) {
        this.besichtigungsDatum = besichtigungsDatum;
    }
}
