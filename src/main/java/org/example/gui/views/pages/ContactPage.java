package org.example.gui.views.pages;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.example.gui.views.components.Footer;
import org.example.gui.views.components.Header;
import org.example.model.objects.User;
import org.example.util.Roles;

public class ContactPage extends VerticalLayout implements View {

    /**
     * Repräsentiert die Kontaktseite mit denWichtigsten Infos zum Projekt
     */

    public void setUp() {

        this.setSizeFull();

        this.setStyleName("contactpage");

        Label title = new Label("Praktische Hausarbeit");
        title.setStyleName("contactlabel");

        Label fach = new Label("SOFTWARE  ENGINEERING 2 (BWI)");
        fach.addStyleName("fach");
        Label prof = new Label("Prof Dr. Sascha Alda");
        prof.addStyleName("prof");
        Label sem = new Label("SS 2020");
        sem.addStyleName("sem");

        Label kontakt = new Label("Kontakt");
        kontakt.addStyleName("kontakt");


        Label name1 = new Label("Julian Klippel");
        name1.addStyleName("name1");
        Label mn1 = new Label("Matrikelnummer: 9034628");
        mn1.addStyleName("mn1");

        Label name2 = new Label("Kimberly Zimmer");
        name2.addStyleName("name2");
        Label mn2 = new Label("Matrikelnummer: 9026306");
        mn2.addStyleName("mn2");


        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.addStyleName("layout_contact");
        verticalLayout.addComponents(title,fach,prof,sem,kontakt,name1,mn1,name2,mn2);

        HorizontalLayout centered = new HorizontalLayout();
        centered.addStyleName("centered_p");


        centered.addComponent(verticalLayout);

        this.addComponents(new Header(), centered, new Footer());

    }




    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        this.setUp();
    }
}