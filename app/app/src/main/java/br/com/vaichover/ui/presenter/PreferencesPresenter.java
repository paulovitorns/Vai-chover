package br.com.vaichover.ui.presenter;


import br.com.vaichover.model.UserPreferences;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface PreferencesPresenter extends BasePresenter {

    void sendToRegister(UserPreferences user);

    void sendToUpdate(UserPreferences user);

    void updateImagemUser(UserPreferences user);

    boolean validateRegisterData();

}
