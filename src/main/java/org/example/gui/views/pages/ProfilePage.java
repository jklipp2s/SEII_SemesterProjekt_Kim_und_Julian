
package org.example.gui.views.pages;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.example.gui.views.components.GenderSelectWindow;
import org.example.process.control.exceptions.DatabaseException;
import org.example.gui.views.components.Footer;
import org.example.gui.views.components.Header;
import org.example.gui.views.components.MyTextField;
import org.example.model.objects.User;
import org.example.model.objects.Vertriebler;
import org.example.model.dao.RegistrierterBenutzerDAO;
import org.example.util.Roles;
import org.example.util.Views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfilePage extends VerticalLayout implements View {



    public void setUp() {

        this.setSizeFull();

        this.setStyleName("profilepage");

        User user = (User) UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);


        if (user.getAnrede() == null) {
            UI.getCurrent().addWindow(new GenderSelectWindow(user));
        }


        Header header = new Header();

        this.addComponent(header);

        Button editProfile = new Button("Profil bearbeiten");
        editProfile.setIcon(VaadinIcons.COG);
        editProfile.setStyleName("regbutton edit");


        VerticalLayout centered = centered(user, false);


        centered.addComponent(editProfile);

        this.addComponents(centered, new Footer());

        editProfile.addClickListener(clickEvent -> {
            int i = this.getComponentIndex(centered);
            this.removeComponent(centered);

            VerticalLayout centeredEdit = centered(user, true);

            this.addComponent(centeredEdit, i);

        });


    }

    public VerticalLayout centered(User user, boolean editing) {

        Label userArt = new Label(user instanceof Vertriebler ? "VERTRIEB" : "KUNDE");
        userArt.setStyleName("tableart long");


        Label userLabel = new Label();
        userLabel.setIcon(VaadinIcons.USER);
        userLabel.setStyleName("user_pic");


        VerticalLayout centered = new VerticalLayout();
        centered.addStyleName("centered_p");

        Label calendar = new Label();
        calendar.setIcon(VaadinIcons.CALENDAR);

        Label regLabel = new Label("Dabei seit:");
        regLabel.addStyleName("reglabel");

        MyTextField regDate = new MyTextField("regular", new TextField(), "Registrationsdatum", calendar, false);
        try {

            Date rdate =  RegistrierterBenutzerDAO.getInstance().fetchDate(user.getEmail());

            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("dd-MM-yyyy");

            String newFormat = sdf.format(rdate);

            regDate.setPlaceholder(newFormat);
        } catch (DatabaseException e) {
            Notification.show(null, e.getReason(), Notification.Type.ERROR_MESSAGE);
        }



        ComboBox<String> anredeSelect = new ComboBox<>();
        anredeSelect.addStyleName("notRegular");
        anredeSelect.setHeight("55px");
        List<String> list = new ArrayList<>();
        list.add("Herr");
        list.add("Frau");
        list.add("*");
        anredeSelect.setItems(list);
        anredeSelect.setPlaceholder("Anrede");


        Label anredeLabel = new Label();
        anredeLabel.setIcon(VaadinIcons.PLUS_CIRCLE_O);
        MyTextField anrede = new MyTextField("regular", new TextField(), "Anrede", anredeLabel, editing);


        Label u1 = new Label();
        u1.setIcon(VaadinIcons.USER);
        Label u2 = new Label();
        u2.setIcon(VaadinIcons.USER);


        MyTextField prename = new MyTextField("regular", new TextField(), "Vorname", u1, editing);
        MyTextField name = new MyTextField("regular", new TextField(), "Nachname", u2, editing);

        Label em = new Label();
        em.setIcon(VaadinIcons.AT);
        MyTextField email = new MyTextField("regular", new TextField(), "Email", em, false);
        email.setPlaceholder(user.getEmail());
        email.addStyleName(editing ? "right_edit" : "right");
        email.addStyleName(editing ? "email_edit" : "email");
        regLabel.addStyleName(editing ? "right_edit" : "right");
        regDate.addStyleName(editing ? "right_edit" : "right");
        regDate.addStyleName(" margin");



        Button save = new Button("Änderungen speichern");
        save.setStyleName("savebutton");

        Button abort = new Button("Abbrechen");
        abort.setStyleName("savebutton");


        centered.addComponents(userLabel, userArt, new HorizontalLayout(new VerticalLayout(editing ? anredeSelect :
                anrede, prename, name, email), new VerticalLayout(regLabel, regDate, email)));


        if (editing) {
            HorizontalLayout horiz = new HorizontalLayout(save, abort);
            centered.addComponents(horiz);
            centered.setComponentAlignment(horiz, Alignment.MIDDLE_CENTER);

        } else {
            anrede.setPlaceholder(user.getAnrede());
            name.setPlaceholder(user.getNachname());
            prename.setPlaceholder(user.getVorname());

        }


        abort.addClickListener(clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILEPAGE);
        });

        save.addClickListener(clickEvent -> {
            try {
                if (!anredeSelect.isEmpty()) {
                    RegistrierterBenutzerDAO.getInstance().updateAnrede(user, anredeSelect.getValue());
                }

                if (!name.getValue().isEmpty()) {
                    RegistrierterBenutzerDAO.getInstance().updateName(user, name.getValue());
                }


                if (!prename.getValue().isEmpty()) {
                    RegistrierterBenutzerDAO.getInstance().updatePrename(user, prename.getValue());
                }


            } catch (DatabaseException e) {
                e.printStackTrace();
            }

            User.refreshMe(user);

            UI.getCurrent().getNavigator().navigateTo(Views.PROFILEPAGE);
        });


        return centered;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

        UI ui = UI.getCurrent();

        if (ui.getSession().getAttribute(Roles.CURRENT_USER) != null)
            this.setUp();
        else ui.getNavigator().navigateTo(Views.LANDINGPAGE);
    }
}
