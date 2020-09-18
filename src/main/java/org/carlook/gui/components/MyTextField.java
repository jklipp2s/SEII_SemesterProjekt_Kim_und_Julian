package org.carlook.gui.components;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;


/**
 * Eigenes Component repräsentiert ein Textfeld welches
 * ein Usericon als Platzhalter ermöglicht
 */

public class MyTextField extends HorizontalLayout {
    Label textlabel;
    Label label;
    HorizontalLayout overLayout;
    TextField textField;



    public MyTextField(String width, TextField textField, String placeholder, Label label) {

        this(width, textField, placeholder, label, true);
        this.textField.setId(placeholder);

    }


    /**
     * Dieser Konstuktor erzeugt ein Textfeld mit deaktiviertem oder aktiviertem Textfeld - je nach dem wie der Parameter
     * "writable" platziert ist
     */

    public MyTextField(String width, TextField textField, String placeholder, Label label, boolean writable) {

        this.addStyleName("mytextfield");

        overLayout = new HorizontalLayout();
        this.label = label;
        label.setStyleName("textfieldicon");

        if(!width.equals("regular") && !width.equals("regularmini") ) {
            textField.setWidth(width);
        } else {
            this.addStyleName(width.equals("regular") ? "notRegular" : "notRegularMinimized" );
            textField.addStyleName(width.equals("regular") ? "notRegular" : "notRegularMinimized");
        }

        textlabel = new Label(placeholder);
        textlabel.setStyleName("textfieldplaceholder");
        overLayout.addComponents(label, textlabel);

        this.textField = textField;
        this.addComponents(textField, overLayout);


        if (writable == true) {

            textField.addFocusListener(focusEvent -> {
                if (overLayout.isAttached()) this.detachPlaceHolder();
            });

            textField.addBlurListener(blurEvent -> {
                if (textField.isEmpty()) this.attachPlaceHolder();
            });

            this.addLayoutClickListener(layoutClickEvent -> {
                textField.focus();
            });
        } else {
            textField.setReadOnly(true);
        }

    }


    public void detachPlaceHolder() {
        this.removeComponent(overLayout);
    }

    public void attachPlaceHolder() {
        this.addComponent(overLayout);
    }

    public void setPlaceholder(String placeholder) {
        textlabel.setValue(placeholder);
    }


    public String getValue() {
        return textField.getValue();
    }

    public TextField getTextField() {
        return textField;
    }


}