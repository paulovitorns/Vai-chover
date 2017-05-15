package br.com.vaichover.ui.presenter.impl;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import br.com.vaichover.ui.presenter.MapsPresenter;
import br.com.vaichover.ui.view.MapsFragmentView;

/**
 * © Copyright 2016 Skatavel.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Skatavel app.
 */

public class MapsPresenterImpl implements MapsPresenter, LocationListener {

    private MapsFragmentView view;
    private boolean localePermissionCheck;

    public MapsPresenterImpl(MapsFragmentView view) {
        this.view = view;
        init();
    }

    @Override
    public void init() {
        this.checkIfMapsPermissionsIsGranted();
    }

    @Override
    public void tryAgain() {

    }

    @Override
    public void checkIfMapsPermissionsIsGranted() {

        localePermissionCheck = ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (!localePermissionCheck) {
            view.requestLocationPermission();
        }

    }

    @Override
    public void getLocationAfterPermissionGranted() {
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Request Permission
            this.checkIfMapsPermissionsIsGranted();
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(view.getGmapsClient());
        view.updateLocation(location);
    }

    @Override
    public void getMyLocation() {

        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Request Permission
            this.checkIfMapsPermissionsIsGranted();
            return;
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(view.getGmapsClient());
        view.updateLocation(location);

    }

    @Override
    public void onLocationChanged(Location location) {
        view.updateLocation(location);
    }
}
