package org.example.process.control;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.example.process.control.exceptions.DatabaseException;
import org.example.model.objects.*;
import org.example.model.dao.*;
import org.example.util.Roles;
import org.example.util.Views;

public class LoginControl {


    public static void authenticate(String email, String password) throws DatabaseException {


        if (!RegistrierterBenutzerDAO.getInstance().authenticate(email,password))
            throw new DatabaseException("Email oder Passwort inkorrekt.");

        VaadinSession session = UI.getCurrent().getSession();

        if(VertrieblerDAO.getInstance().isVertriebler(email)) {
            Vertriebler logged = VertrieblerDAO.getInstance().fetchVertriebler(email);
            session.setAttribute(Roles.CURRENT_USER, logged);
            return;
        }

        Kunde logged = KundeDAO.getInstance().fetchKunde(email);
        session.setAttribute(Roles.CURRENT_USER, logged);
    }


    public static void logout(){
        UI.getCurrent().getSession().close();
        UI.getCurrent().getPage().setLocation(Views.LANDINGPAGE);
    }


    public static boolean isLoggedIn(){
        return UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER) != null;
    }


}