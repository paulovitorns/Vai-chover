package br.com.vaichover.business.service;

import br.com.vaichover.model.Session;
import br.com.vaichover.model.UserPreferences;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface SessionManagerService {

    void createNewSession(UserPreferences user);

    Session getCurrentSession();

    void updateCurrentSession(UserPreferences user);

    void destroyCurrentSession();
}
