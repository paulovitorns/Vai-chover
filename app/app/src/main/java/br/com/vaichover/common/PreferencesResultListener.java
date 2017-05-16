package br.com.vaichover.common;

import br.com.vaichover.model.UserPreferences;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface PreferencesResultListener {
    void onSuccess(UserPreferences user);
    void onError(String title, String msg);
}
