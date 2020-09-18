package org.example.gui.views.components;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import org.example.gui.views.pages.LoginPage;
import org.example.gui.views.pages.RegisterPage;
import org.example.gui.views.pages.SearchPage;


public class Footer extends HorizontalLayout {

    public Footer() {


        this.setSizeFull();
        this.addStyleName("footer");

        HorizontalLayout left = new HorizontalLayout();
        left.addStyleName("footer_left");


        Label fb = new Label("FB");

        fb.setStyleName("footer_fb");
        Label tw = new Label("Tw");
        tw.setStyleName("footer_tw");
        Label in = new Label("In");
        in.setStyleName("footer_in");


        left.addComponents(fb, tw, in);

        HorizontalLayout middle = new HorizontalLayout();
        middle.addStyleName("footer_middle");
        HorizontalLayout right = new HorizontalLayout();
        right.addStyleName("footer_right");
        Label editedBy = new Label("KIMBERLY ZIMMER   >   JULIAN KLIPPEL SEII SEMESTERPROJEKT SS2020 CARLOOK LTD.");
        editedBy.setStyleName("editedBy");
        middle.addComponent(editedBy);


        if (UI.getCurrent().getNavigator().getCurrentView().getClass().equals(SearchPage.class))
            this.addComponents(middle);
        else
            this.addComponents(left, middle);

        if (!UI.getCurrent().getNavigator().getCurrentView().getClass().equals(RegisterPage.class) &&
                !UI.getCurrent().getNavigator().getCurrentView().getClass().equals(LoginPage.class) &&
                !UI.getCurrent().getNavigator().getCurrentView().getClass().equals(SearchPage.class))
        this.addComponent(right);

    }


}
