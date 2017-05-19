package br.com.vaichover.ui.presenter;

/**
 * © Copyright 2016 Skatavel.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Skatavel app.
 */

public interface MapsPresenter extends BasePresenter {

    void requestWeathers();

    void checkIfMapsPermissionsIsGranted();

    void getMyLocation();

    void putPlacesOnMap();
}
