package org.carlook.process.control;

import org.carlook.model.dao.ReserviertDAO;
import org.carlook.model.objects.dto.Auto;
import org.carlook.model.objects.dto.Kunde;
import org.carlook.process.control.exception.DatabaseException;
import org.carlook.process.control.exception.RegisterException;

import java.time.LocalDate;
import java.sql.Date;

public class ReserviertControl {


    public static void reserviere(Auto auto, Kunde kunde, LocalDate termin) throws DatabaseException {




        if(termin == null)
            throw new RegisterException("Bitte wählen Sie ein Besichtigungstermin");



            Date last = null;
            last =  ReserviertDAO.getInstance().getLatestTermin(kunde.getEmail(), auto.getFahrzeugIdentifikationsNummer());

            LocalDate lastTermin = last != null ? last.toLocalDate() : null;


            if( (lastTermin != null && termin.isBefore(lastTermin)) || termin.isBefore(LocalDate.now()) || (lastTermin != null && termin.isEqual(lastTermin)) ) {
                throw new RegisterException("Bitte wählen Sie einen anderen Besichtigungstermin");

            }

            if( lastTermin != null && lastTermin.isAfter(LocalDate.now()))
                throw new RegisterException("Sie haben noch einen bevorstehenden Besichtigungstermin mit diesem Auto!");





            ReserviertDAO.getInstance().registerAutoReservierung(auto, kunde, termin);



    }

}