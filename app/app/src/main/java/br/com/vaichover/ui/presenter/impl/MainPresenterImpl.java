package br.com.vaichover.ui.presenter.impl;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import br.com.vaichover.business.service.OpenWeatherService;
import br.com.vaichover.business.service.SessionManagerService;
import br.com.vaichover.business.service.impl.OpenWeatherServiceImpl;
import br.com.vaichover.business.service.impl.SessionManagerServiceImpl;
import br.com.vaichover.common.OnOpenWeatherFinishedListener;
import br.com.vaichover.model.ApiResponseType;
import br.com.vaichover.model.OpenWeatherMap;
import br.com.vaichover.model.Session;
import br.com.vaichover.model.UserPreferences;
import br.com.vaichover.ui.presenter.MainPresenter;
import br.com.vaichover.ui.view.DashBoardView;
import br.com.vaichover.ui.view.fragment.MapsFragment;
import br.com.vaichover.ui.view.fragment.WeatherListFragment;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class MainPresenterImpl implements MainPresenter, OnOpenWeatherFinishedListener {

    private DashBoardView           view;
    private SessionManagerService   sessionManagerService;
    private OpenWeatherMap          openWeatherMap;
    private OpenWeatherService      openWeatherService;
    private UserPreferences         user;
    private boolean                 hasLocationPermission;

    public MainPresenterImpl(DashBoardView view, UserPreferences user, OpenWeatherMap map) {
        this.view = view;

        if(user != null)
            this.user = user;

        if(map != null)
            this.openWeatherMap = map;

        this.init();
    }

    @Override
    public void init() {
        this.sessionManagerService  = new SessionManagerServiceImpl();
        this.openWeatherService     = new OpenWeatherServiceImpl();

        Session session = this.sessionManagerService.getCurrentSession();

        if(session != null && session.getUser() != null)
            this.user = session.getUser();

        if(!hasLocationPermission())
            this.view.requestLocationPermission();
    }

    @Override
    public void tryAgain() {
        if(openWeatherMap == null)
            this.getWeathers();
    }

    @Override
    public boolean hasLocationPermission() {
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return false;
        else
            return true;
    }

    @Override
    public void onGetUserLocation() {

        LocationManager manager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
        Location utilLocation   = null;

        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            view.requestLocationPermission();
            this.hasLocationPermission = false;
            return;
        }

        utilLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(utilLocation == null)
            utilLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(utilLocation != null){
            this.user = new UserPreferences();
            this.user.getPlace().getResult().getGeometry().setLat(utilLocation.getLatitude());
            this.user.getPlace().getResult().getGeometry().setLng(utilLocation.getLongitude());
            this.view.showDegreesPreferencesIcon(user);
        }
    }

    @Override
    public void startUserPreferences() {
        onGetUserLocation();
    }

    @Override
    public void updateToolbar() {

        if(user != null) {
            this.view.showDegreesPreferencesIcon(user);
        }else {
            this.startUserPreferences();
        }

    }

    @Override
    public void getWeathers() {
        this.view.showLoading();
        this.openWeatherService.requestData(this, user);
    }

    @Override
    public void prepareDefaultFragment() {

        WeatherListFragment fragment = WeatherListFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable(UserPreferences.KEY, user);
        bundle.putSerializable(OpenWeatherMap.KEY, openWeatherMap);
        fragment.setArguments(bundle);

        this.view.loadDefaultFragment(fragment);
    }

    @Override
    public void prepareFragment() {

        MapsFragment fragment = MapsFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable(UserPreferences.KEY, user);
        bundle.putSerializable(OpenWeatherMap.KEY, openWeatherMap);
        fragment.setArguments(bundle);

        this.view.changeFragment(fragment);
    }

    @Override
    public void onApiError(ApiResponseType error) {
        this.view.showEptyState(error);
        this.view.hideLoading();
    }

    @Override
    public void onSuccess(OpenWeatherMap openWeatherMap) {
        this.openWeatherMap = openWeatherMap;
        this.view.updateWeathers(openWeatherMap);
        prepareDefaultFragment();
        this.view.hideLoading();
    }
}
