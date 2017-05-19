package br.com.vaichover.ui.view;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;

import br.com.vaichover.model.OpenWeatherMap;
import br.com.vaichover.model.OpenWeatherMapResult;
import br.com.vaichover.model.UserPreferences;

/**
 * © Copyright 2016 Skatavel.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Skatavel app.
 */

public interface MapsFragmentView extends BaseFragmentView {

    void onClickFabLocale();

    void requestLocationPermission();

    void drawMarker();

    void drawMarker(OpenWeatherMapResult result);

    void clearAllMarkers();

    void drawMarkerWithAnimation();

    void updateLocation(Location location);

    Context getContext();

    GoogleApiClient getGmapsClient();

    void updateMainView(OpenWeatherMap map, UserPreferences user);

}
