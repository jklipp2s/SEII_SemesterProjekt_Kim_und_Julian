package org.carlook.services.util;

import com.vaadin.ui.UI;

public class AccessData {
    public final static String DATABASE_USER = "jklipp2s";
    public final static String DATABASE_PASSWORD = "jklipp2s";



    public static Boolean isCurrentPage(Class cls) {
        return UI.getCurrent().getNavigator().getCurrentView().getClass().equals(cls);
    }
}
