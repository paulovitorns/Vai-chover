package br.com.vaichover.ui.presenter;

import com.google.android.gms.maps.model.LatLng;

/**
 * © Copyright 2016 Skatavel.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Skatavel app.
 */

public interface MapsPresenter extends BasePresenter {

    void userHasDrag();

    boolean hasToGetNewLocation();

    void requestWeathers();

    void requestWeathers(LatLng latLng);

    void checkIfMapsPermissionsIsGranted();

    void getMyLocation();

    void getMySavedLocation();

    void putPlacesOnMap();
}
