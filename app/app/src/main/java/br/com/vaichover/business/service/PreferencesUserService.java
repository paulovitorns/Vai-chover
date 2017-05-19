package br.com.vaichover.business.service;

import br.com.vaichover.common.PreferencesResultListener;
import br.com.vaichover.model.UserPreferences;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface PreferencesUserService {
    void registerClient(UserPreferences user, PreferencesResultListener listener);
    void updateClient(UserPreferences user, PreferencesResultListener listener);
}
