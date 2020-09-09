package org.example.gui.views.components;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;



public class MyTextField extends HorizontalLayout {
    Label textlabel;
    Label label;
    HorizontalLayout overLayout;
    TextField textField;

    public MyTextField(String width, TextField textField, String placeholder, Label label) {

        this(width, textField, placeholder, label, true);
        this.textField.setId(placeholder);

    }



    public MyTextField(String width, TextField textField, String placeholder, Label label, boolean writable) {

        this.addStyleName("mytextfield");

        overLayout = new HorizontalLayout();
        this.label = label;
        label.setStyleName("textfieldicon");
        textField.setWidth(width);
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