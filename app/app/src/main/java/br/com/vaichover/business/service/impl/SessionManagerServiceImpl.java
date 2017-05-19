package br.com.vaichover.business.service.impl;

import br.com.vaichover.business.service.SessionManagerService;
import br.com.vaichover.model.Session;
import br.com.vaichover.model.UserPreferences;
import br.com.vaichover.util.SharedPreferencesUtils;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class SessionManagerServiceImpl implements SessionManagerService {

    @Override
    public void createNewSession(UserPreferences user) {

        if(SharedPreferencesUtils.getSessionData() == null)
        {
            SharedPreferencesUtils.saveSessionData(new Session(user));
        }else{
            SharedPreferencesUtils.unsetSessionData();
            SharedPreferencesUtils.saveSessionData(new Session(user));
        }

    }

    @Override
    public Session getCurrentSession() {
        return SharedPreferencesUtils.getSessionData();
    }

    @Override
    public void updateCurrentSession(UserPreferences user) {
        SharedPreferencesUtils.saveSessionData(new Session(user));
    }

    @Override
    public void destroyCurrentSession() {
        SharedPreferencesUtils.unsetSessionData();
    }
}
