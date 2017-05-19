package br.com.vaichover.ui.presenter;

import br.com.vaichover.model.OpenWeatherMap;
import br.com.vaichover.model.UserPreferences;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface MainPresenter extends BasePresenter {

    boolean hasLocationPermission();

    void onGetUserLocation();

    void startUserPreferences();

    void updateToolbar();

    void getWeathers();

    void prepareDefaultFragment();

    void prepareFragment();

    void updateWeathersAndUserData(OpenWeatherMap map, UserPreferences user);
}
