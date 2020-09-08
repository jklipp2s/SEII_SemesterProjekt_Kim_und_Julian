package org.example.model.objects;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.example.process.control.exceptions.DatabaseException;
import org.example.model.dao.KundeDAO;
import org.example.model.dao.VertrieblerDAO;
import org.example.util.Roles;

public class User {
    private String anrede;
    private String email;
    private String vorname;
    private String nachname;
    private String passwort;


    public User() {

    }

    public User(String email, String vorname, String nachname, String passwort) {
        this.email = email;
        this.vorname = vorname;
        this.nachname = nachname;
        this.passwort = passwort;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }


    public static void refreshMe(User user){
        if (user instanceof Vertriebler) {
            try {
                UI.getCurrent().getSession().setAttribute(Roles.CURRENT_USER, VertrieblerDAO.getInstance().fetchVertriebler(user.getEmail()));
            } catch (DatabaseException e) {
                Notification.show(null, e.getReason(), Notification.Type.ERROR_MESSAGE);
            }
        } else {
            try {
                UI.getCurrent().getSession().setAttribute(Roles.CURRENT_USER, KundeDAO.getInstance().fetchKunde(user.getEmail()));
            } catch (DatabaseException e) {
                Notification.show(null, e.getReason(), Notification.Type.ERROR_MESSAGE);
            }
        }
    }



}
