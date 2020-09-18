package org.carlook.model.objects.dto;

public class Kunde extends User {

    public Kunde() {

    }

    public Kunde(String email, String vorname, String nachname, String passwort) {
        super(email,vorname,nachname,passwort);
    }



}
