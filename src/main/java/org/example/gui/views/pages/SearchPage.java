package org.example.gui.views.pages;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import org.example.process.control.SearchControl;
import org.example.process.control.exceptions.RegisterException;
import org.example.gui.views.components.Footer;
import org.example.gui.views.components.GridCallbackValueProvider;
import org.example.gui.views.components.Header;
import org.example.gui.views.components.MyTextField;
import org.example.model.objects.Auto;
import org.example.model.objects.User;
import org.example.util.Roles;
import org.example.util.Views;

import java.util.List;


public class SearchPage extends VerticalLayout implements View {
    Grid<Auto> autoGrid;
    List<Auto> myCars;
    MyTextField markeT;
    MyTextField preisT;
    MyTextField baujahrT;
    MyTextField psT;
    MyTextField eigenschaftenT;
    VerticalLayout left;
    VerticalLayout right;
    Label hits;


    public void setUp() {

        this.setSizeFull();

        this.setStyleName("searchpage");

        User user = (User) UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);


        Label title = new Label("FAHRZEUGSUCHE");
        title.setStyleName("suchart");

        Header header = new Header();



        HorizontalLayout centered = new HorizontalLayout();
        centered.addStyleName("centered_p");

        left = new VerticalLayout();
        left.addStyleName("searchpage_left");


        right = new VerticalLayout();
        right.addStyleName("searchpage_right");




        Label m = new Label("MARKE/MODELL");
        Label flashIcon = new Label();
        flashIcon.setIcon(VaadinIcons.FLASH);
        markeT = new MyTextField("450px", new TextField(),"Marke",  flashIcon);
        VerticalLayout marke = new VerticalLayout(m, markeT);
        marke.addStyleName("ml");

        Label dollar = new Label();
        dollar.setIcon(VaadinIcons.DOLLAR);
        Label pr = new Label("PREIS BIS €");
        preisT = new MyTextField("450px", new TextField(),"Verkaufspreis",  dollar);
        VerticalLayout preis = new VerticalLayout(pr, preisT );
        preis.addStyleName("ml");

        Label calendar = new Label();
        calendar.setIcon(VaadinIcons.CALENDAR);
        Label b = new Label("BAUJAHR AB");
        baujahrT = new MyTextField("450px", new TextField(),"Baujahr",  calendar);
        VerticalLayout baujahr = new VerticalLayout(b, baujahrT);
        baujahr.addStyleName("ml");


        Label rocket = new Label();
        rocket.setIcon(VaadinIcons.ROCKET);
        Label p = new Label("MOTORISIERUNG BIS (PS)");
        psT = new MyTextField("450px", new TextField(),"PS",  rocket);
        VerticalLayout ps = new VerticalLayout(p, psT);
        ps.addStyleName("ml");

        Label asterisk = new Label();
        asterisk.setIcon(VaadinIcons.ASTERISK);
        Label e = new Label("EIGENSCHAFTEN");
        eigenschaftenT = new MyTextField("450px", new TextField(),"Eigenschaften",  asterisk);
        VerticalLayout eigenschaften = new VerticalLayout(e, eigenschaftenT);
        eigenschaften.addStyleName("ml");

        left.addComponents(marke, preis, baujahr, ps, eigenschaften);


        autoGrid = new Grid<>();
        autoGrid.addColumn(Auto::getMarke).setCaption("Marke/Modell");
        autoGrid.addColumn(Auto::getBaujahr).setCaption("Baujahr");
        autoGrid.addColumn(Auto::getBeschreibung).setCaption("Beschreibung");
        autoGrid.addColumn(Auto::getFormattedVerkaufsPreis).setCaption("Verkaufspreis").setWidth(180);
        autoGrid.addComponentColumn(new GridCallbackValueProvider<>(autoGrid)).setWidth(95);
        myCars = null;


        autoGrid.setStyleName("searchpage_autogrid");
        right.addComponents(title, autoGrid);
        centered.addComponents(left, right);

        this.addComponents(header, centered, new Footer());


        Label reservNoResult = new Label("Keine Treffer...");
        reservNoResult.setStyleName("searchnoresult");


        hits = new Label();
        hits.addStyleName("hits");
        left.addComponent(hits);

        /**
         * Implementierung der ON-THE-FLY-Suche
         */

        markeT.getTextField().addValueChangeListener(valueChangeEvent -> {
            setUpListener(reservNoResult);
        });


        preisT.getTextField().addValueChangeListener(valueChangeEvent -> {
            setUpListener(reservNoResult);
        });

        baujahrT.getTextField().addValueChangeListener(valueChangeEvent -> {
            setUpListener(reservNoResult);
        });

        psT.getTextField().addValueChangeListener(valueChangeEvent -> {
            setUpListener(reservNoResult);
        });

        eigenschaftenT.getTextField().addValueChangeListener(valueChangeEvent -> {
            setUpListener(reservNoResult);
        });


        markeT.getTextField().setValueChangeMode(ValueChangeMode.LAZY);
        preisT.getTextField().setValueChangeMode(ValueChangeMode.LAZY);
        baujahrT.getTextField().setValueChangeMode(ValueChangeMode.LAZY);
        psT.getTextField().setValueChangeMode(ValueChangeMode.LAZY);
        eigenschaftenT.getTextField().setValueChangeMode(ValueChangeMode.LAZY);


    }


    public void setUpListener(Label noResult){
        List <Auto> res = null;
        try {
            res = SearchControl.searchCars(markeT.getValue(), preisT.getValue(), baujahrT.getValue(), psT.getValue(), eigenschaftenT.getValue());
        } catch (RegisterException e) {
            Notification.show(null, e.getReason(), Notification.Type.WARNING_MESSAGE);
        }

        if(res.size() == 0) {
            right.addComponent(noResult);
        } else {
            right.removeComponent(noResult);
        }

        hits.setValue(res.size() + "  Treffer");
        autoGrid.setItems(res);
        autoGrid.setHeightByRows(res.size() > 0 ? res.size() : 1);

    };




    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

        UI ui = UI.getCurrent();

        if (ui.getSession().getAttribute(Roles.CURRENT_USER) != null)
            this.setUp();
        else ui.getNavigator().navigateTo(Views.LOGINPAGE);
    }
}
