package org.carlook.gui.windows;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import org.carlook.services.util.Views;


/**
 * Repräsentiert ein Fenster, in welchem mitgeteilt wird ob der Vorgang z.B Reservierung eines Autos
 * ein Erfolg war
 */

public class SuccessWindow extends Window {

    public SuccessWindow(Class cls, String name) {


        this.center();
        this.setClosable(false);
        this.setResizable(false);
        this.setStyleName("regsuccesswindow");
        this.setCaption(cls.getSimpleName().equals("RegisterPage") ? " Welcome" : " Success");
        this.setIcon(cls.getSimpleName().equals("RegisterPage") ?  VaadinIcons.GROUP : VaadinIcons.CAR);

        VerticalLayout verticalLayout = new VerticalLayout();
        Label text = new Label(cls.getSimpleName().equals("RegisterPage") ? "Willkommen " + name + "!" :
                cls.getSimpleName().equals("CarWindow") ? name + " erfolgreich reserviert!" : name + " erfolgreich registriert!"  );
        Button button = new Button("Ok");

        verticalLayout.addComponents(text, button);
        verticalLayout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);


        //Logik

        button.addClickListener(clickEvent -> {

            this.close();

            if(cls.getSimpleName().equals("RegisterPage")) {
                UI.getCurrent().getNavigator().navigateTo(Views.LOGINPAGE);
            }

        });


        this.setContent(verticalLayout);


    }


}