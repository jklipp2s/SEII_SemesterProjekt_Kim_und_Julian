package org.carlook.gui.windows;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import org.carlook.gui.components.MyTextField;
import org.carlook.process.control.InseratControl;
import org.carlook.process.control.exception.RegisterException;
import org.carlook.model.objects.dto.Vertriebler;


/**
 * Repräsentiert ein Fenster in dem Vertriebler neue Fahrzeuge inserieren können
 */

public class InseratWindow extends Window {
    private MyTextField carID;
    private MyTextField marke;
    private DateField baujahr;
    private TextArea beschreibung;
    private MyTextField ps;
    private MyTextField verbrauch;
    private MyTextField preis;


    public InseratWindow(Vertriebler vertriebler) {

        this.center();
        this.setDraggable(false);
        this.setResizable(false);
        this.setStyleName("inserierewindow");
        this.setCaption("Fahrzeug Inserieren");

        VerticalLayout verticalLayout = new VerticalLayout();


        Label carIcon = new Label();
        carIcon.setIcon(VaadinIcons.CAR);
        this.carID = new MyTextField("450px", new TextField(), "FAHRZEUGIDENTIFIKATIONSNUMMER", carIcon);

        Label flashIcon = new Label();
        flashIcon.setIcon(VaadinIcons.FLASH);
        this.marke = new MyTextField("450px", new TextField(), "Marke/Modell", flashIcon);


        this.baujahr = new DateField("Baujahr");
        this.baujahr.setDateFormat("yyyy");


        this.beschreibung = new TextArea();
        this.beschreibung.setWidth("452px");
        this.beschreibung.setPlaceholder("Beschreibung");


        Label rocket = new Label();
        rocket.setIcon(VaadinIcons.ROCKET);
        this.ps = new MyTextField("450px", new TextField(), "PS", rocket);


        Label trend = new Label();
        trend.setIcon(VaadinIcons.TRENDING_UP);
        this.verbrauch = new MyTextField("450px", new TextField(), "Verbrauch", trend);


        Label dollar = new Label();
        dollar.setIcon(VaadinIcons.DOLLAR);
        this.preis = new MyTextField("450px", new TextField(), "Preis", dollar);


        Button inseriere = new Button("Inserieren");
        inseriere.addStyleName("inserierebutton");


        verticalLayout.addComponents(carID, marke, baujahr, beschreibung, ps, verbrauch, preis, inseriere);
        verticalLayout.setComponentAlignment(inseriere, Alignment.MIDDLE_CENTER);


        this.setContent(verticalLayout);


        //Logik

        inseriere.addClickListener(clickEvent -> {
            try {
                InseratControl.inseriere(vertriebler, carID.getValue(), marke.getValue(),
                        baujahr.getValue(), beschreibung.getValue(), ps.getValue(),
                        verbrauch.getValue(), preis.getValue());

                UI.getCurrent().addWindow(new SuccessWindow(this.getClass(), marke.getValue()));
                resetFields();

            } catch (RegisterException e) {
                Notification.show(null, e.getReason(), Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    public void resetFields(){
        this.carID.getTextField().clear();
        this.carID.attachPlaceHolder();
        this.marke.getTextField().clear();
        this.marke.attachPlaceHolder();
        this.baujahr.clear();
        this.beschreibung.clear();
        this.ps.getTextField().clear();
        this.ps.attachPlaceHolder();
        this.verbrauch.getTextField().clear();
        this.verbrauch.attachPlaceHolder();
        this.preis.getTextField().clear();
        this.preis.attachPlaceHolder();
    }


}