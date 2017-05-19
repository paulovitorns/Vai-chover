package br.com.vaichover.ui.presenter;

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
}
