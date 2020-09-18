package org.carlook.process.control;

import org.carlook.model.objects.dto.Kunde;
import org.carlook.model.objects.dto.User;
import org.carlook.model.objects.dto.Vertriebler;
import org.carlook.process.control.exception.*;
import org.carlook.model.dao.*;

import java.util.regex.Pattern;

public class RegistrationControl {


    public static boolean register(String email,  String prename, String name, String password, boolean isVertriebler) throws DatabaseException {


        String exception = "";


        if(email.isEmpty() || prename.isEmpty() || name.isEmpty() || password.isEmpty())
            exception += "Please fill in all fields\n";


        if (email.length() > 100) exception += "Die gewählte Emailadresse ist zu lang\n\n";
        if (prename.length() > 60) exception += "Der gewählte Vorname ist zu lang\n\n";
        if (name.length() > 60) exception += "Der gewählte Nachname ist zu lang\n\n";
        if (password.length() > 16) exception += "Das gewählte passwort ist zu lang\n\n";



        if (!RegistrierterBenutzerDAO.getInstance().emailIsAvailable(email)) exception += "Diese Email ist bereits vergeben!\n";
        if (password.length() < 8) exception += "Ihr Passwort muss mindestens 8 Zeichen beinhalten\n";


        if (!exception.isEmpty()) {
            throw new RegisterException(exception);
        }


        if (isVertriebler) {

            if (!isCompanyAddress(email)) {
                exception += "Bitte verwenden Sie als Vertriebler\nihre Firmenemailadresse";
                throw new RegisterException(exception);
            }

            Vertriebler vReg = new Vertriebler(email.toLowerCase(), prename, name, password);
            VertrieblerDAO.getInstance().registerVertriebler(vReg);
        } else {
            Kunde kReg = new Kunde(email.toLowerCase(), prename, name, password);
            KundeDAO.getInstance().registerKunde(kReg);
        }


        return true;
    }

    public static void updateAnrede(User user, String anrede) throws DatabaseException {
        RegistrierterBenutzerDAO.getInstance().updateAnrede(user, anrede);
    }

    public static void updateName(User user, String name) throws DatabaseException {
        RegistrierterBenutzerDAO.getInstance().updateName(user, name);
    }

    public static void updatePrename(User user, String prename) throws DatabaseException {
        RegistrierterBenutzerDAO.getInstance().updatePrename(user, prename);
    }


    public  static boolean isCompanyAddress(String email) {
        String emailRegex = "[\\w.%+-]+@carlook.de";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


}
