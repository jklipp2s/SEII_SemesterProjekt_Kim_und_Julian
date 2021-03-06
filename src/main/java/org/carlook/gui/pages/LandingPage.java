package org.carlook.gui.pages;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.carlook.gui.components.Footer;
import org.carlook.gui.components.Header;
import org.carlook.services.util.Views;

public class LandingPage extends VerticalLayout implements View {


    /**
     * Repräsentiert die Startseite
     */

    public void setUp(){

        this.setSizeFull();

        this.addComponent(new Header());
        this.setStyleName("landing");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName("landingpage_middle");
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.addStyleName("landingpage_middle_right");


        Image titleCar = new Image(null,new ThemeResource("img/titlecar.jpg"));
        titleCar.addStyleName("titlecar");


        Label welcome1 = new Label("Herzlich");
        welcome1.setStyleName("welcome1");
        Label welcome2 = new Label("Willkommen");
        welcome2.setStyleName("welcome2");
        Button regButton = new Button("REGISTRIEREN");
        regButton.setStyleName("regbutton");
        verticalLayout.addComponents(welcome1,welcome2,regButton);
        horizontalLayout.addComponents(titleCar,verticalLayout);


        VerticalLayout verticalLayoutgrey = new VerticalLayout();
        verticalLayoutgrey.addStyleName("verticallayoutgrey");



        Label upper = new Label("In Ihrem Autohaus Carlook Ltd.<br>Sie suchen junge und gepflegte Gebrauchtwagen? ", ContentMode.HTML);
        upper.setStyleName("upper");

        verticalLayoutgrey.addComponents(upper);

        verticalLayout.addComponent(verticalLayoutgrey);


        this.addComponent(horizontalLayout);


        this.addComponent(new Footer());


        // LOGIK

        regButton.addClickListener(clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo(Views.REGISTERPAGE);
        });


    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    this.setUp();
    }
}
