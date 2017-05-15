package br.com.vaichover.ui.view;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

/**
 * © Copyright 2016 Skatavel.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Skatavel app.
 */

public interface MapsFragmentView extends BaseFragmentView {

    void onClickFabLocale();

    void requestLocationPermission();

    Location requestMyLocation();

    void drawMarker();

    void drawMarker(LatLng latLng);

    void drawMarkerWithAnimation();

    void updateLocation(Location location);

    Context getContext();

    GoogleApiClient getGmapsClient();

}
