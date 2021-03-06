package org.carlook.gui.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.carlook.gui.pages.*;
import org.carlook.services.util.Views;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Title("Carlook Ltd.")
@PreserveOnRefresh
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Navigator navigator = new Navigator(this,this);
        navigator.addView(Views.LANDINGPAGE, LandingPage.class);
        navigator.addView(Views.LOGINPAGE, LoginPage.class);
        navigator.addView(Views.REGISTERPAGE, RegisterPage.class);
        navigator.addView(Views.PROFILEPAGE, ProfilePage.class);
        navigator.addView(Views.CONTACTPAGE, ContactPage.class);
        navigator.addView(Views.CARPAGE, CarPage.class);
        navigator.addView(Views.SEARCHPAGE, SearchPage.class);









        getCurrent().getNavigator().navigateTo(Views.LANDINGPAGE);




    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
