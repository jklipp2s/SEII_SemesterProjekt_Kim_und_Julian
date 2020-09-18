package org.carlook.gui.pages;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import org.carlook.gui.components.Footer;
import org.carlook.gui.components.Header;
import org.carlook.gui.components.MyTextField;
import org.carlook.gui.windows.SuccessWindow;
import org.carlook.process.control.exception.DatabaseException;
import org.carlook.process.control.RegistrationControl;
import org.carlook.services.util.Roles;

import java.util.regex.Pattern;

public class RegisterPage extends VerticalLayout implements View {


    /**
     * Repräsentiert die Seite auf der sich Kunden und Vertriebler registrieren können
     */

    public void setUp() {

        this.setSizeFull();

        this.addComponent(new Header());


        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName("landingpage_middle registerpage");
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.addStyleName("registerpage_middle_right");


        Image titleCar = new Image(null, new ThemeResource("img/registerpagecar.jpg"));
        titleCar.addStyleName("titlecar");


        VerticalLayout centered = new VerticalLayout();
        centered.addStyleName("centered");


        Label emailIcon = new Label();
        emailIcon.setIcon(VaadinIcons.ENVELOPE_OPEN_O);
        MyTextField email = new MyTextField("regular", new TextField(), "EMAIL", emailIcon);


        Label userIcon = new Label();
        userIcon.setIcon(VaadinIcons.USER);
        MyTextField prename = new MyTextField("regularmini", new TextField(), "VORNAME", userIcon);
        prename.addStyleName("prename");

        Label userIcon2 = new Label();
        userIcon2.setIcon(VaadinIcons.USERS);
        MyTextField name = new MyTextField("regularmini", new TextField(), "NAME", userIcon2);
        name.addStyleName("name");


        HorizontalLayout user = new HorizontalLayout(prename, name);

        Label passwordIcon = new Label();
        passwordIcon.setIcon(VaadinIcons.LOCK);
        MyTextField password = new MyTextField("regular", new PasswordField(), "PASSWORT", passwordIcon);

        Label passwordIcon2 = new Label();
        passwordIcon2.setIcon(VaadinIcons.LOCK);
        MyTextField passwordRepeat = new MyTextField("regular", new PasswordField(), "PASSWORT WIEDERHOLEN", passwordIcon2);


        HorizontalLayout radioButtonWrapper = new HorizontalLayout();

        radioButtonWrapper.setStyleName("radiobuttonwrapper");


        CheckBox kunde = new CheckBox();
        kunde.setHeight("100px");
        CheckBox mitarbeiter = new CheckBox();


        radioButtonWrapper.addComponents(kunde, mitarbeiter);
        radioButtonWrapper.addStyleName("notRegular");

        kunde.setCaption("Kunde");
        kunde.setStyleName("kunde");
        mitarbeiter.setCaption("Mitarbeiter");
        mitarbeiter.setStyleName("mitarbeiter");


        Button registerButton = new Button("Registrieren");
        registerButton.addStyleName("registerbutton functional");


        centered.addComponents(email, user, password, passwordRepeat, radioButtonWrapper);

        verticalLayout.addComponent(centered);

        horizontalLayout.addComponents(titleCar, verticalLayout);


        this.addComponent(horizontalLayout);


        Footer regFooter = new Footer();
        regFooter.addStyleName("registerpage");
        this.addComponent(regFooter);



        Label errorEmail = new Label("Bitte wählen Sie eine korrekte Email!");
        errorEmail.setStyleName("errorf");

        Label errorPassword = new Label("Ihre PasswortEinträge stimmen nicht überein!");
        errorPassword.setStyleName("errorf");


        /**
         * Überprüfung ob Passworteingaben übereinstimmen und ob Email das korrekte Format besitzt
         *
         */


        email.getTextField().addBlurListener(e -> {
            if (!isValidEmail(email.getValue()) && !email.getValue().isEmpty()) {
                centered.addComponent(errorEmail, centered.getComponentIndex(email));
            } else if (errorEmail.isAttached()) {
                centered.removeComponent(errorEmail);
            }
        });


        passwordRepeat.getTextField().addBlurListener(e -> {
            if (!passwordRepeat.getValue().equals(password.getValue())) {
                centered.addComponent(errorPassword, centered.getComponentIndex(passwordRepeat));
                registerButton.setEnabled(false);
            } else if (errorPassword.isAttached()) {
                centered.removeComponent(errorPassword);

            }
        });

        passwordRepeat.getTextField().setValueChangeMode(ValueChangeMode.LAZY);


        /**
         * verhindert, dass Kunde und Mitarbeiter gleichzeitig ausgewählt werden können.
         */

        kunde.addValueChangeListener(valueChangeEvent -> {

            if (kunde.getValue()) {
                mitarbeiter.setValue(false);
                centered.addComponent(registerButton);
            } else {
                centered.removeComponent(registerButton);
            }

        });


        mitarbeiter.addValueChangeListener(valueChangeEvent -> {
            if (mitarbeiter.getValue()) {
                kunde.setValue(false);
                centered.addComponent(registerButton);
            } else {
                centered.removeComponent(registerButton);
            }


        });

        /**
         * Wenn die Errorlabels erscheinen soll der RegisterButton deaktiviert werden, wenn sie verschwinden soll er
         * aktiviert werden
         */

        centered.addComponentDetachListener(e -> {
            if (centered.getComponentIndex(errorEmail) == -1 && centered.getComponentIndex(errorPassword) == -1
                    && !email.getValue().isEmpty() && !name.getValue().isEmpty() && !prename.getValue().isEmpty() && !password.getValue().isEmpty()
                    && !passwordRepeat.getValue().isEmpty()) {
                registerButton.setEnabled(true);
            }
        });


        registerButton.addClickListener(clickEvent -> {
            try {
                boolean result = RegistrationControl.register(email.getValue(), prename.getValue(), name.getValue(), password.getValue(), mitarbeiter.getValue() ? true : false);

                UI.getCurrent().addWindow(new SuccessWindow(this.getClass(), prename.getValue()));


            } catch (DatabaseException e) {
                Notification.show(null, e.getReason(), Notification.Type.WARNING_MESSAGE);
            }

        });


    }


    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if(UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER) == null) {
            this.setUp();
        }
    }


}
