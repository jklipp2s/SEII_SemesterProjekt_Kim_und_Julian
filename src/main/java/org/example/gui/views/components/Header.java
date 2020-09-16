package org.example.gui.views.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.example.process.control.LoginControl;
import org.example.gui.views.pages.*;
import org.example.gui.views.pages.RegisterPage;
import org.example.util.Roles;
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
        homeButton.addStyleName(UI.getCurrent().getNavigator().getCurrentView().getClass().equals(LandingPage.class) ||
                UI.getCurrent().getNavigator().getCurrentView().getClass().equals(ProfilePage.class) ? "orangebutton" : "");



        homeButton.addStyleName(UI.getCurrent().getNavigator().getCurrentView().getClass().equals(LandingPage.class) ? "orangebutton" : "");


        Button fahrzeugeButton = new Button("Fahrzeuge");
        fahrzeugeButton.setStyleName("fahrzeugebutton");




        Button kontaktButton = new Button("Kontakt");
        kontaktButton.setStyleName("kontaktbutton");
        kontaktButton.addStyleName(UI.getCurrent().getNavigator().getCurrentView().getClass().equals(ContactPage.class)
                ? "orangebutton" : "");


        Button sucheButton = new Button("Suche");
        sucheButton.setStyleName("suchebutton");

        Button loginButton = new Button(LoginControl.isLoggedIn() ? VaadinIcons.POWER_OFF : VaadinIcons.USER);
        loginButton.setStyleName("loginbutton");

        loginButton.addStyleName(UI.getCurrent().getNavigator().getCurrentView().getClass().equals(LoginPage.class) ||
                UI.getCurrent().getNavigator().getCurrentView().getClass().equals(RegisterPage.class)
                ? "orangebutton" : "");




        section2.addComponents(homeButton, fahrzeugeButton, kontaktButton, sucheButton, loginButton);


        this.addComponents(section1, section2);


        // LOGIK

        UI ui = UI.getCurrent();
        homeButton.addClickListener(clickEvent -> {
            if (ui.getSession().getAttribute(Roles.CURRENT_USER) != null)
                ui.getNavigator().navigateTo(Views.PROFILEPAGE);
            else
                ui.getNavigator().navigateTo(Views.LANDINGPAGE);
        });

        loginButton.addClickListener(clickEvent -> {

            if (ui.getSession().getAttribute(Roles.CURRENT_USER) != null)
                LoginControl.logout();
            else
                ui.getNavigator().navigateTo(Views.LOGINPAGE);

        });

        kontaktButton.addClickListener(clickEvent -> {
            ui.getNavigator().navigateTo(Views.CONTACTPAGE);
        });


        fahrzeugeButton.addClickListener(clickEvent -> {
            ui.getNavigator().navigateTo(Views.CARPAGE);
        });


    }

}
