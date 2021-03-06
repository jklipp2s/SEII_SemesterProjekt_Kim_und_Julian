package org.carlook.gui.pages;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.carlook.gui.components.Footer;
import org.carlook.process.control.LoginControl;
import org.carlook.gui.components.MyTextField;
import org.carlook.process.control.exception.DatabaseException;
import org.carlook.gui.components.Header;
import org.carlook.services.util.Roles;
import org.carlook.services.util.Views;



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
        horizontalLayout.addStyleName("landingpage_middle loginpage");
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.addStyleName("registerpage_middle_right");


        Image titleCar = new Image(null, new ThemeResource("img/registerpagecar.jpg"));
        titleCar.addStyleName("titlecar");


        VerticalLayout centered = new VerticalLayout();
        centered.addStyleName("centered");


        Label emailIcon = new Label();
        emailIcon.setIcon(VaadinIcons.ENVELOPE_OPEN_O);
        this.email = new MyTextField("regular", new TextField(), "EMAIL", emailIcon);


        Label passwordIcon = new Label();
        passwordIcon.setIcon(VaadinIcons.LOCK);
        this.password = new MyTextField("regular", new PasswordField(), "PASSWORD", passwordIcon);


        Button login = new Button("LOGIN");
        login.addStyleName("registerbutton loginbutton");


        centered.addComponents(email, password, login);

        verticalLayout.addComponent(centered);

        horizontalLayout.addComponents(titleCar, verticalLayout);


        this.addComponent(horizontalLayout);


        Footer regFooter = new Footer();
        regFooter.addStyleName("registerpage");
        this.addComponent(regFooter);


        // LOGIK


            login.addClickListener(clickEvent -> {

                try {

                    LoginControl.authenticate(email.getTextField().getValue(), password.getTextField().getValue());
                    UI.getCurrent().getNavigator().navigateTo(Views.PROFILEPAGE);
                } catch (DatabaseException e) {
                    resetFields();
                    Notification.show(null, e.getReason(), Notification.Type.ERROR_MESSAGE);
                }

            });




    }


    private void resetFields(){
        this.email.getTextField().clear();
        this.email.attachPlaceHolder();
        this.password.getTextField().clear();
        this.password.attachPlaceHolder();
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

        UI ui = UI.getCurrent();

        if(ui.getSession().getAttribute(Roles.CURRENT_USER) == null )
            this.setUp();

    }



}