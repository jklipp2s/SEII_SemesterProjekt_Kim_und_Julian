package org.example.gui.views.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import org.example.util.Views;




public class SuccessWindow extends Window {

    public SuccessWindow(Class cls, String name) {


        this.center();
        this.setClosable(false);
        this.setResizable(false);
        this.setStyleName("regsuccesswindow");
        this.setCaption(cls.getSimpleName().equals("RegisterPage") ? " Welcome" : " Success");
        this.setIcon(cls.getSimpleName().equals("RegisterPage") ?  VaadinIcons.GROUP : VaadinIcons.CAR);


        VerticalLayout verticalLayout = new VerticalLayout();
        Label text = new Label("Willkommen " + name + "!");

        Button button = new Button("Ok");

        verticalLayout.addComponents(text, button);
        verticalLayout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);



        button.addClickListener(clickEvent -> {

            this.close();

            if(cls.getSimpleName().equals("RegisterPage")) {
                UI.getCurrent().getNavigator().navigateTo(Views.LOGINPAGE);
            }

        });


        this.setContent(verticalLayout);


    }


}
