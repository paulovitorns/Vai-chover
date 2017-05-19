package br.com.vaichover.model;

import java.io.Serializable;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class Session implements Serializable {

    public static final String KEY = Session.class.getSimpleName();

    private UserPreferences user;

    public Session(UserPreferences user) {
        this.user = user;
    }

    public void setSession(UserPreferences user){
        this.user = user;
    }

    public UserPreferences getUser(){
        return this.user;
    }

    public void cleanSession(){
        this.user = null;
    }

    public void updateClient(UserPreferences user){
        this.user = user;
    }

}
