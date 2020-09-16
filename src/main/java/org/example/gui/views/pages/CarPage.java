package org.example.gui.views.pages;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.example.process.control.exceptions.DatabaseException;
import org.example.gui.views.components.Footer;
import org.example.gui.views.components.GridCallbackValueProvider;
import org.example.gui.views.components.Header;
import org.example.gui.views.windows.InseratWindow;
import org.example.model.dao.AutoDAO;
import org.example.model.dao.ReserviertDAO;
import org.example.model.objects.*;
import org.example.util.Roles;
import org.example.util.Views;

import java.util.List;

public class CarPage extends VerticalLayout implements View {




    public void setUp(){

        this.setSizeFull();
        this.setStyleName("carpage");

        this.addComponent(new Header());

        User user = (User) UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName(user instanceof Vertriebler ? "carpage_middle" : "carpage_middle_hitlist");


        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setStyleName("inner");

        HorizontalLayout center = new HorizontalLayout();
        center.setStyleName("quad");
        center.addComponent(verticalLayout);


        Button meineAutos = new Button("MEINE AUTOS");
        meineAutos.setStyleName("quaderbutton");
        Button einstellen = new Button("AUTO INSERIEREN");
        einstellen.setStyleName("quaderbutton");

        verticalLayout.addComponents(meineAutos, einstellen);


        Label header = new Label(user instanceof  Vertriebler ? "Meine Inserate" : "Meine Reservierungen");
        header.setStyleName("tableart");

        if(user instanceof Kunde)
            horizontalLayout.addComponent(header);

        horizontalLayout.addComponent(user instanceof Kunde ? setUpReservierungGrid((Kunde) user) : center);


        this.addComponents(horizontalLayout, new Footer());

        if(user instanceof  Vertriebler) {

            einstellen.addClickListener(clickEvent -> {
                UI.getCurrent().addWindow(new InseratWindow((Vertriebler) user));
            });


            meineAutos.addClickListener(clickEvent -> {

                horizontalLayout.removeComponent(center);



                horizontalLayout.removeStyleName("carpage_middle");
                horizontalLayout.setStyleName("carpage_middle_hitlist");


                horizontalLayout.addComponents(header, setUpAutoGrid((Vertriebler) user));


            });

        }
    }

    public Component setUpAutoGrid(Vertriebler vertriebler) {

        List<Auto> myCars = null;


        try {
            myCars =AutoDAO.getInstance().fetchAutos(vertriebler);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        if(myCars.size() == 0) {
            Label reservNoResult = new Label("Sie haben noch keine Fahrzeuge inseriert");
            reservNoResult.setStyleName("reservnoresult");
            return  reservNoResult;
        }

        Grid<Auto> autoGrid = new Grid<>();
        autoGrid.addColumn(Auto::getMarke).setCaption("Marke/Modell");
        autoGrid.addColumn(Auto::getBaujahr).setCaption("Baujahr");
        autoGrid.addColumn(Auto::getBeschreibung).setCaption("Beschreibung");
        autoGrid.addColumn(Auto::getFormattedVerkaufsPreis).setCaption("Verkaufspreis").setWidth(180);
        autoGrid.addComponentColumn(new GridCallbackValueProvider<>(autoGrid)).setWidth(95);



        autoGrid.setStyleName("autogrid");
        autoGrid.setItems(myCars);
        autoGrid.setHeightByRows(myCars.size() > 0 ? myCars.size() : 1);

        return autoGrid;
    }




    public Component setUpReservierungGrid(Kunde kunde) {


        List<Reservierung> reservierungen = null;

        try {
            reservierungen = ReserviertDAO.getInstance().fetchReservierungen(kunde);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }


        if(reservierungen.size() == 0) {
            Label reservNoResult = new Label("Sie haben noch keine Fahrzeuge reserviert");
            reservNoResult.setStyleName("reservnoresult");
            return  reservNoResult;
        }


        Grid<Reservierung> grid = new Grid<>();



        grid.addColumn(Reservierung::getReg_nr).setCaption("NR").setWidth(180);
        grid.addColumn(Reservierung::getMarke).setCaption("Marke");
        grid.addColumn(Reservierung::getFahrzeug).setCaption("FahrzeugID");
        grid.addColumn(Reservierung::getBesichtigungsDatum).setCaption("Reserviert bis:").setWidth(220);
        grid.addComponentColumn(new GridCallbackValueProvider<>(grid)).setWidth(95);

        grid.setStyleName("autogrid");
        grid.setItems(reservierungen);
        grid.setHeightByRows(reservierungen.size() > 0 ? reservierungen.size() : 1);

        return grid;
    }




    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        UI ui = UI.getCurrent();

        if (ui.getSession().getAttribute(Roles.CURRENT_USER) != null)
            this.setUp();
        else ui.getNavigator().navigateTo(Views.LOGINPAGE);
    }

}
