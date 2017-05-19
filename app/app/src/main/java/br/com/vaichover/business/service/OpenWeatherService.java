package br.com.vaichover.business.service;

import com.google.android.gms.maps.model.LatLng;

import br.com.vaichover.common.OnOpenWeatherFinishedListener;
import br.com.vaichover.model.UserPreferences;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface OpenWeatherService {

    void requestData(OnOpenWeatherFinishedListener listener, UserPreferences user);

    void resquestDataForNewLocation(OnOpenWeatherFinishedListener listener, UserPreferences user);
}
