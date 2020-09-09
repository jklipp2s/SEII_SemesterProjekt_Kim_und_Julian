package org.example.gui.views.pages;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.example.gui.views.components.MyTextField;
import org.example.process.control.exceptions.DatabaseException;
import org.example.gui.views.components.Footer;
import org.example.gui.views.components.Header;
import org.example.util.Roles;
import org.example.util.Views;



/**
 * Repräsentiert die Loginseite
 */

public class LoginPage extends VerticalLayout implements View {

    MyTextField email;
    MyTextField password;


    public void setUp() {

        this.setSizeFull();

        this.addComponent(new Header());


        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName("landingpage_middle");
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.addStyleName("registerpage_middle_right");


        Image titleCar = new Image(null, new ThemeResource("img/registerpagecar.jpg"));
        titleCar.addStyleName("titlecar");


        VerticalLayout centered = new VerticalLayout();
        centered.addStyleName("centered");


        Label emailIcon = new Label();
        emailIcon.setIcon(VaadinIcons.ENVELOPE_OPEN_O);
        this.email = new MyTextField("450px", new TextField(), "EMAIL", emailIcon);


        Label passwordIcon = new Label();
        passwordIcon.setIcon(VaadinIcons.LOCK);
        this.password = new MyTextField("450px", new PasswordField(), "PASSWORD", passwordIcon);


        Button login = new Button("LOGIN");
        login.addStyleName("registerbutton loginbutton");


        centered.addComponents(email, password, login);

        verticalLayout.addComponent(centered);

        horizontalLayout.addComponents(titleCar, verticalLayout);


        this.addComponent(horizontalLayout);


        Footer regFooter = new Footer();
        regFooter.addStyleName("registerpage");
        this.addComponent(regFooter);


    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

        UI ui = UI.getCurrent();

        if(ui.getSession().getAttribute(Roles.CURRENT_USER) == null )
            this.setUp();

    }



}