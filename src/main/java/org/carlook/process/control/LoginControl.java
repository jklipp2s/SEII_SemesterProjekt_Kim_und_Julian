package org.carlook.process.control;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.carlook.model.objects.dto.Kunde;
import org.carlook.model.objects.dto.Vertriebler;
import org.carlook.process.control.exception.DatabaseException;
import org.carlook.model.dao.*;
import org.carlook.services.util.*;

public class LoginControl {


    public static void authenticate(String email, String password) throws DatabaseException {


        email = email.toLowerCase();

        if (!RegistrierterBenutzerDAO.getInstance().authenticate(email,password))
            throw new DatabaseException("Email oder Passwort inkorrekt.");




        VaadinSession session = UI.getCurrent().getSession();

        if(VertrieblerDAO.getInstance().isVertriebler(email.toLowerCase())) {
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