package org.example.gui.views.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.example.gui.views.pages.*;
import org.example.util.Views;

public class Header extends HorizontalLayout {


    public Header() {

        this.setStyleName("header");
        this.setSizeFull();


        Image logo = new Image(null, new ThemeResource("img/logo.png"));


        HorizontalLayout section1 = new HorizontalLayout();
        section1.setStyleName("section1");
        HorizontalLayout section2 = new HorizontalLayout();
        section2.setStyleName("section2");

        section1.addComponent(logo);
        logo.addStyleName("logo");


        Button homeButton = new Button("Home");
        homeButton.setStyleName("homebutton");



        homeButton.addStyleName(UI.getCurrent().getNavigator().getCurrentView().getClass().equals(LandingPage.class) ? "orangebutton" : "");


        Button fahrzeugeButton = new Button("Fahrzeuge");
        fahrzeugeButton.setStyleName("fahrzeugebutton");




        Button kontaktButton = new Button("Kontakt");
        kontaktButton.setStyleName("kontaktbutton");


        Button sucheButton = new Button("Suche");
        sucheButton.setStyleName("suchebutton");

        Button loginButton = new Button(VaadinIcons.USER);
        loginButton.setStyleName("loginbutton");

        loginButton.addStyleName(UI.getCurrent().getNavigator().getCurrentView().getClass().equals(LoginPage.class) ?
                "orangebutton" : "");




        section2.addComponents(homeButton, fahrzeugeButton, kontaktButton, sucheButton, loginButton);


        this.addComponents(section1, section2);


        UI ui = UI.getCurrent();


        loginButton.addClickListener(clickEvent -> {


                ui.getNavigator().navigateTo(Views.LOGINPAGE);



        });
//
    }

}
