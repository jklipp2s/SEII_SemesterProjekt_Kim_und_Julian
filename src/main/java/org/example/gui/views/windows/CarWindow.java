package org.example.gui.views.windows;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.example.model.objects.*;
import org.example.process.control.ReserviertControl;
import org.example.process.control.exceptions.DatabaseException;
import org.example.util.Roles;




public class CarWindow extends Window {
    public CarWindow(Auto auto){

        this.center();
        this.setCaption(" Eigenschaften");
        this.setIcon(VaadinIcons.CAR);



        this.setStyleName("carwindow");

        VerticalLayout verticalLayout = new VerticalLayout();

        Label marke = new Label("<b>Marke/Modell:</b> " + auto.getMarke(), ContentMode.HTML);
        Label fin = new Label("<b>Fahrzeugidentifikationsnummer:</b><br> " + auto.getFahrzeugIdentifikationsNummer(), ContentMode.HTML);
        Label baujahr = new Label("<b>Baujahr:</b> " + auto.getBaujahr(), ContentMode.HTML);
        Label beschreibung  = new Label("<b>Eigenschaften:</b><br>" + auto.getBeschreibung(), ContentMode.HTML);
        beschreibung.setWidth("275px");
        Label ps = new Label("<b>PS:</b> " + auto.getPs(), ContentMode.HTML);
        Label verbrauch = new Label("<b>Verbrauch:</b> " + auto.getVerbrauch() + " l/100km", ContentMode.HTML);
        Label preis = new Label("<b>Preis:</b> " + auto.getFormattedVerkaufsPreis(), ContentMode.HTML);
        Label verfuegbar = new Label("<b>Verfuegbar:</b> " + (auto.isVerfuegbar() ? "Ja" : "Verkauft"), ContentMode.HTML);



        verticalLayout.addComponents(marke,fin,baujahr,beschreibung,ps,verbrauch,preis,verfuegbar);



        if(UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER) instanceof Kunde) {

            DateField termin = new DateField("Besichtigungstermin");
            Button reserviereButton = new Button("RESERVIEREN");
            reserviereButton.setStyleName("reservierebutton");
            verticalLayout.addComponents(termin, reserviereButton);

            verticalLayout.setComponentAlignment(reserviereButton, Alignment.MIDDLE_CENTER);


            reserviereButton.addClickListener(clickEvent -> {
                try {
                    UI ui = UI.getCurrent();
                    Kunde kunde = (Kunde) UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);

                    System.out.println(kunde.getEmail());

                    ReserviertControl.reserviere(auto, kunde ,termin.getValue());
                    this.close();
                    ui.addWindow(new SuccessWindow(this.getClass(), auto.getMarke()));

                } catch (DatabaseException e) {
                    Notification.show(null,e.getReason(), Notification.Type.HUMANIZED_MESSAGE);
                }
            });


        }

        this.setContent(verticalLayout);



    }




}
