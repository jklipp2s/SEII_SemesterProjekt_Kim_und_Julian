package org.carlook.gui.windows;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import org.carlook.process.control.exception.DatabaseException;
import org.carlook.model.objects.dto.User;
import org.carlook.model.dao.RegistrierterBenutzerDAO;
import org.carlook.services.util.Views;

import java.util.*;

/**
 * Repräsentiert ein Fenster in dem der User seine Anrede wählen kann
 */


public class GenderSelectWindow extends Window {

    public GenderSelectWindow(User user) {


        this.setClosable(false);
        this.setResizable(false);
        this.center();
        this.setStyleName("regsuccesswindow");

        this.setCaption(" Anrede");
        this.setIcon(VaadinIcons.CLIPBOARD_CHECK);

        VerticalLayout verticalLayout = new VerticalLayout();
        Label text = new Label("Bitte wählen Sie eine Anrede!");

        ComboBox<String> anrede = new ComboBox<>();
        anrede.addStyleName("mytextfield");
        List<String> list = new ArrayList<>();
        list.add("Herr");
        list.add("Frau");
        anrede.setItems(list);
        anrede.setSelectedItem("*");



        Button button = new Button("Ok");
        button.setStyleName("genderselectbutton");

        verticalLayout.addComponents(text, anrede, button);
        verticalLayout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);

        //Logik

        button.addClickListener(clickEvent -> {
            try {
                RegistrierterBenutzerDAO.getInstance().updateAnrede(user, anrede.getValue());
            } catch (DatabaseException e) {
                Notification.show(null, e.getReason(), Notification.Type.ERROR_MESSAGE);
            }

            User.refreshMe(user);
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILEPAGE);
            this.close();

        });


        this.setContent(verticalLayout);


    }


}
