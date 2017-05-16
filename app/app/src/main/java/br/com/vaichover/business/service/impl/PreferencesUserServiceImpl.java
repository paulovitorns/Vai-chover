package br.com.vaichover.business.service.impl;

import br.com.vaichover.business.service.PreferencesUserService;
import br.com.vaichover.common.PreferencesResultListener;
import br.com.vaichover.model.UserPreferences;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class PreferencesUserServiceImpl implements PreferencesUserService {

    @Override
    public void registerClient(UserPreferences user, PreferencesResultListener listener) {
        if(user != null)
            listener.onSuccess(user);
        else
            listener.onError("Erro ao cadastrar", "Preencha os seus dados");
    }

    @Override
    public void updateClient(UserPreferences user, PreferencesResultListener listener) {
        if(user != null)
            listener.onSuccess(user);
        else
            listener.onError("Erro ao atualizar sua conta", "Preencha os seus dados");
    }
}
