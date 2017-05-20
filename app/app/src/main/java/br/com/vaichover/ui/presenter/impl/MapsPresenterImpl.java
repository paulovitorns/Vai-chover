package br.com.vaichover.ui.presenter.impl;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import br.com.vaichover.business.service.OpenWeatherService;
import br.com.vaichover.business.service.SessionManagerService;
import br.com.vaichover.business.service.impl.OpenWeatherServiceImpl;
import br.com.vaichover.business.service.impl.SessionManagerServiceImpl;
import br.com.vaichover.common.OnOpenWeatherFinishedListener;
import br.com.vaichover.model.ApiResponseType;
import br.com.vaichover.model.OpenWeatherMap;
import br.com.vaichover.model.OpenWeatherMapResult;
import br.com.vaichover.model.Session;
import br.com.vaichover.model.UserLastDraggedLocation;
import br.com.vaichover.model.UserPreferences;
import br.com.vaichover.ui.presenter.MapsPresenter;
import br.com.vaichover.ui.view.MapsFragmentView;

/**
 * © Copyright 2016 Skatavel.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Skatavel app.
 */

public class MapsPresenterImpl implements MapsPresenter, OnOpenWeatherFinishedListener {

    private MapsFragmentView        view;
    private boolean                 localePermissionCheck;
    private SessionManagerService   sessionManagerService;
    private UserPreferences         user;
    private OpenWeatherService      openWeatherService;
    private OpenWeatherMap          openWeatherMap;
    private boolean                 isUserHasDrag;

    public MapsPresenterImpl(MapsFragmentView view, Bundle bundle) {
        this.view = view;
        if(bundle != null){
            this.user = (UserPreferences) bundle.getSerializable(UserPreferences.KEY);
            this.openWeatherMap = (OpenWeatherMap) bundle.getSerializable(OpenWeatherMap.KEY);
        }
        init();
    }

    @Override
    public void init() {
        this.checkIfMapsPermissionsIsGranted();

        this.sessionManagerService  = new SessionManagerServiceImpl();
        this.openWeatherService = new OpenWeatherServiceImpl();

        if(user == null){
            Session session = this.sessionManagerService.getCurrentSession();
            if(session != null && session.getUser() != null){
                this.user = session.getUser();
            }
        }

    }

    @Override
    public void tryAgain() {

    }

    @Override
    public void userHasDrag() {
        this.isUserHasDrag = true;
    }

    @Override
    public void uiHasDrag() {
        this.isUserHasDrag = false;
    }

    @Override
    public boolean hasToGetNewLocation() {
        if(openWeatherMap.isLastSearchByDragInMap() || !isUserHasDrag) {
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void requestWeathers() {
        this.view.showLoading();
        this.openWeatherService.requestData(this, user);
    }

    @Override
    public void requestWeathers(LatLng latLng) {
        this.view.showLoading();
        user.setLastDragPlace(new UserLastDraggedLocation(latLng.latitude, latLng.longitude));
        this.openWeatherService.resquestDataForNewLocation(this, user);
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
    public void getMyLocation() {

        LocationManager manager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Request Permission
            this.checkIfMapsPermissionsIsGranted();
            return;
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(view.getGmapsClient());

        if(location == null)
            location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(location == null)
            location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(user != null && user.getPlace()!=null){
            if(user.getLastDragPlace() != null){
                location.setLatitude(user.getLastDragPlace().getLat());
                location.setLongitude(user.getLastDragPlace().getLng());
            }else{

                if(location == null)
                    location = new Location(user.getPlace().getResult().getFormatted_address());

                location.setLatitude(user.getPlace().getResult().getGeometry().getLat());
                location.setLongitude(user.getPlace().getResult().getGeometry().getLng());
            }
        }

        view.updateLocation(location);

        if(openWeatherMap == null)
            requestWeathers();
        else
            putPlacesOnMap();

    }


    @Override
    public void getMySavedLocation() {

        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Request Permission
            this.checkIfMapsPermissionsIsGranted();
            return;
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(view.getGmapsClient());

        if(user != null && user.getPlace()!=null){
            location.setLatitude(user.getPlace().getResult().getGeometry().getLat());
            location.setLongitude(user.getPlace().getResult().getGeometry().getLng());
        }

        view.updateLocation(location);

        if(openWeatherMap == null)
            requestWeathers();
        else
            putPlacesOnMap();

    }

    @Override
    public void putPlacesOnMap() {
        for (OpenWeatherMapResult result : openWeatherMap.getList()){
            this.view.drawMarker(result);
        }
        this.openWeatherMap.setLastSearchByDragInMap(false);
    }

    @Override
    public void onApiError(ApiResponseType error) {
        this.view.hideLoading();
    }

    @Override
    public void onSuccess(OpenWeatherMap openWeatherMap) {
        this.openWeatherMap = openWeatherMap;
        this.putPlacesOnMap();
        this.view.hideLoading();
    }

    @Override
    public void onSuccessNewPlaces(OpenWeatherMap openWeatherMap) {
        this.openWeatherMap = openWeatherMap;
        this.view.clearAllMarkers();
        this.putPlacesOnMap();
        this.view.updateMainView(openWeatherMap, this.user);
        this.view.hideLoading();
    }
}
