package org.carlook.gui.components;

import com.vaadin.ui.*;
import org.carlook.gui.pages.LoginPage;
import org.carlook.gui.pages.RegisterPage;
import org.carlook.gui.pages.SearchPage;
import static org.carlook.services.util.AccessData.isCurrentPage;



/**
 * Repräsentiert die Fußzeile, welche auf jeder Seite
 * eingeblendet wird
 */

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


        if (isCurrentPage(SearchPage.class))
            this.addComponents(middle);
        else
            this.addComponents(left, middle);

        if (!isCurrentPage(RegisterPage.class) &&
                !isCurrentPage(LoginPage.class) &&
                !isCurrentPage(SearchPage.class))
            this.addComponent(right);

    }





}
