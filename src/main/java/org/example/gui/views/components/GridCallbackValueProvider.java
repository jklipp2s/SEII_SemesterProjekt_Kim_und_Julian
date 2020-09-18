package org.example.gui.views.components;


import com.vaadin.data.ValueProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import org.example.gui.views.windows.CarWindow;
import org.example.model.dao.AutoDAO;
import org.example.model.objects.*;

import org.example.process.control.exceptions.DatabaseException;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *  Repräsentiert eine Gridkomponente die genutzt
 *  wird um entweder auf Autos oder auf Reservierungen
 *  zuzugreifen
 * @param <T> ist in diesem Fall Auto oder Reservierung
 */



public  class GridCallbackValueProvider<T>
        implements ValueProvider<T, Layout> {


    private final Grid<T> grid;


    public GridCallbackValueProvider(Grid<T> grid) {
        this.grid = grid;
    }

    @Override
    public Layout apply(T object) {
        HorizontalLayout al = new HorizontalLayout();
        al.setWidth("0px");
        Button button = new Button();


        al.addComponent(button);
        button.setIcon(VaadinIcons.CAR);
        button.addStyleName("carbutton");
        button.addClickListener( clickEvent -> {




            if(object instanceof Auto) {
                UI.getCurrent().addWindow(new CarWindow((Auto) object));
            } else

            if(object instanceof Reservierung) {
                try {
                    // Wenn die Grid keine Grid<Auto> ist, muss das auto zuerst aus der Datenbank geladen werden.
                    Auto auto = AutoDAO.getInstance().fetchAuto(((Reservierung) object).getFahrzeug());
                    UI.getCurrent().addWindow(new CarWindow(auto));
                } catch (DatabaseException e) {
                    Logger.getLogger(AutoDAO.class.getName()).log(Level.SEVERE, null, e);
                }
            }

            grid.deselectAll();



        });
        return al;
    }

}

