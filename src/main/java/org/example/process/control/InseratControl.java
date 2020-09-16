package org.example.process.control;
import org.example.model.objects.*;
import org.example.model.dao.AutoDAO;
import org.example.process.control.exceptions.DatabaseException;
import org.example.process.control.exceptions.RegisterException;

import java.time.LocalDate;
import java.util.Calendar;

public class InseratControl {

    public static void inseriere(Vertriebler vertriebler, String fin, String marke, LocalDate baujahr, String beschreibung,
                                 String ps, String verbrauch, String preis) throws RegisterException {

        String error = "";

        Auto car = new Auto();

        if (fin.isEmpty() || marke.isEmpty() || baujahr == null || ps.isEmpty()
                || verbrauch.isEmpty() || preis.isEmpty()) {
            error += "Bitte geben Sie alle Daten an!\n";
            throw new RegisterException(error);
        }

        if (fin.length() > 17)
            error += "Ihre FIN ist zu lang!\n";
        if (marke.length() > 50)
            error += "Ihre Markenbezeichnung ist zu lang!\n";
        if (beschreibung.length() > 300)
            error += "Ihre Beschreinung ist zu lang!\n";
        if (ps.length() > 3)
            error += "Bitte geben Sie die PS korrekt an!\n";


        try {
            if (AutoDAO.getInstance().autoIsRegistered(fin))
                error += "Das Fahrzeug mit der von Ihnen eingegebenen Fahrzeugidentifikationsnummer wurde bereits registriert!\n";

            car.setFahrzeugIdentifikationsNummer(fin);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        car.setMarke(marke);

        if (baujahr.getYear() > Calendar.getInstance().get(Calendar.YEAR)) {
            error += "Bitte geben Sie das Baujahr korrekt an!\n";
        }

        car.setBaujahr(baujahr.getYear());
        car.setBeschreibung(beschreibung);


        try {
            int psVal = Integer.parseInt(ps);
            car.setPs(psVal);

        } catch (NumberFormatException e) {
            error += "Bitte geben Sie PS als Zahl an!\n";
        }


        try {
            double verbrauchVal = Double.parseDouble(verbrauch);
            car.setVerbrauch(verbrauchVal);
        } catch (NumberFormatException e) {
            error += "Bitte geben Sie den Verbrauch als Zahl an!\n";
        }


        try {
            double preisVal = Double.parseDouble(preis);
            car.setVerkaufsPreis(preisVal);
        } catch (NumberFormatException e) {
            error += "Bitte geben Sie den Preis als Zahl an!\n";
        }


        if (!error.isEmpty())
            throw new RegisterException(error);

        try {
            AutoDAO.getInstance().registerAuto(car, vertriebler);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }


    }


}