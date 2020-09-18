package org.carlook.process.control;


import com.vaadin.ui.Notification;
import org.carlook.process.control.exception.*;
import org.carlook.model.objects.dto.Auto;
import org.carlook.model.dao.AutoDAO;

import java.util.ArrayList;
import java.util.List;

public class SearchControl {


    public static List<Auto> searchCars(String marke, String preis, String baujahr, String ps, String eigenschaften) throws RegisterException {

        double preisVal = 0;
        int baujahrVal = 0;
        int psVal = 0;


        String error = "";

        List<Auto> result = new ArrayList<Auto>();

        if(marke.isEmpty() && (preis.isEmpty() || preis.equals("0"))  && (baujahr.isEmpty() ||  baujahr.equals("0")) &&
                (ps.isEmpty() || ps.equals("0")) && eigenschaften.isEmpty()) {
            return result;
        }

        try {
            if(!preis.isEmpty() && !preis.equals("0"))
                preisVal = Double.parseDouble(preis);
            if(!baujahr.isEmpty() && !baujahr.equals("0"))
                baujahrVal = Integer.parseInt(baujahr);
            if(!ps.isEmpty() && !ps.equals("0"))
                psVal =  Integer.parseInt(ps);

        } catch (NumberFormatException e) {
            error += "Bei den Feldern PREIS, BAUJAHR und PS  sind nur Zahlen zulässig!\n";
        }

        if(!error.isEmpty()) {
            throw new RegisterException(error);
        }



        try {
            result = AutoDAO.getInstance().searchCars(marke,preisVal,baujahrVal,psVal,eigenschaften);
        } catch (DatabaseException e) {
            Notification.show(null,e.getReason(), Notification.Type.ERROR_MESSAGE);
        }
        return result;
    }



}
