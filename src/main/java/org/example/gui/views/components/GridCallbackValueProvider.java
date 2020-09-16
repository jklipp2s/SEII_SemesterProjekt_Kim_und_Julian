package org.example.gui.views.components;


import com.vaadin.data.ValueProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;






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



            grid.deselectAll();



        });
        return al;
    }

}

