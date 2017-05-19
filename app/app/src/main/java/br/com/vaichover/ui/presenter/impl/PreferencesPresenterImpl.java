package br.com.vaichover.ui.presenter.impl;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import br.com.vaichover.business.service.GPlaceService;
import br.com.vaichover.business.service.GPlacesService;
import br.com.vaichover.business.service.PreferencesUserService;
import br.com.vaichover.business.service.SessionManagerService;
import br.com.vaichover.business.service.impl.GPlaceServiceImpl;
import br.com.vaichover.business.service.impl.GPlacesServiceImpl;
import br.com.vaichover.business.service.impl.PreferencesUserServiceImpl;
import br.com.vaichover.business.service.impl.SessionManagerServiceImpl;
import br.com.vaichover.common.OnGPlacesRequestFinished;
import br.com.vaichover.common.OnPlaceResponseFinishedListener;
import br.com.vaichover.common.PreferencesResultListener;
import br.com.vaichover.model.ApiResponseType;
import br.com.vaichover.model.GPlace;
import br.com.vaichover.model.GPlaces;
import br.com.vaichover.model.GPlacesAutoComplete;
import br.com.vaichover.model.Session;
import br.com.vaichover.model.UserPreferences;
import br.com.vaichover.ui.adapter.PlacesAdapter;
import br.com.vaichover.ui.presenter.PreferencesPresenter;
import br.com.vaichover.ui.view.PreferencesView;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class PreferencesPresenterImpl implements PreferencesPresenter, PreferencesResultListener,
        OnGPlacesRequestFinished, OnPlaceResponseFinishedListener {

    private UserPreferences         user;
    private PreferencesView         view;
    private PreferencesUserService  service;
    private SessionManagerService   sessionManagerService;
    private GPlacesService          gPlacesService;
    private GPlaceService           gPlaceService;
    private GPlaces                 places;
    private PlacesAdapter           adapter;

    public PreferencesPresenterImpl(PreferencesView view, UserPreferences user) {
        this.view = view;

        if(user != null)
            this.user = user;

        this.init();
    }

    @Override
    public void init() {
        this.service                = new PreferencesUserServiceImpl();
        this.sessionManagerService  = new SessionManagerServiceImpl();
        this.gPlacesService         = new GPlacesServiceImpl();
        this.gPlaceService          = new GPlaceServiceImpl();

        this.view.loadAdapter();

        Session session = this.sessionManagerService.getCurrentSession();

        if(session != null && session.getUser() != null){
            this.user = session.getUser();
        }else{
            if(this.user == null)
                this.user = new UserPreferences();
        }

        this.view.setDataInfoUser(this.user);

        if(this.user.getDegrees() == UserPreferences.Degrees.CELSIUS)
            this.view.setCelsiusDegrees();
        else
            this.view.setFahrenheitDegrees();
    }

    @Override
    public void tryAgain() {}

    @Override
    public void sendToRegister(UserPreferences user) {
        this.view.showLoading();
        this.user = user;

        if(validateRegisterData()){
            this.user.setLastDragPlace(null);
            this.service.registerClient(this.user, this);
        }else{
            this.view.hideLoading();
        }
    }

    @Override
    public void sendToUpdate(UserPreferences user) {
        this.view.showLoading();
        this.user = user;

        if(validateRegisterData()){
            this.service.updateClient(user, this);
        }else{
            this.view.hideLoading();
        }
    }

    @Override
    public void updateImagemUser(UserPreferences user) {
        this.sessionManagerService.updateCurrentSession(user);
    }

    @Override
    public boolean validateRegisterData() {

        if(this.user.getPlace().getResult().getFormatted_address().isEmpty()){
            this.view.setLocaleEmptyError();
            return false;
        }else{
            this.view.setLocaleDefaultState();
        }

        return true;
    }

    @Override
    public void requestPlaces(String param) {
        this.view.showProgress();
        this.gPlacesService.onRequestPlaces(this, param);
    }

    @Override
    public void setPlaceSelected(GPlacesAutoComplete place) {
        this.gPlaceService.resquestPlace(this, place);
    }

    @Override
    public void setLastUserLocation() {

        LocationManager manager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
        Location utilLocation   = null;

        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        utilLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(utilLocation == null)
            utilLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(utilLocation != null){
            this.user = new UserPreferences();
            this.user.getPlace().getResult().setFormatted_address("minha localização");
            this.user.getPlace().getResult().getGeometry().setLat(utilLocation.getLatitude());
            this.user.getPlace().getResult().getGeometry().setLng(utilLocation.getLongitude());
            onSuccess(this.user.getPlace());
        }
    }

    @Override
    public void onSuccess(UserPreferences user) {
        this.view.hideLoading();
        this.sessionManagerService.createNewSession(user);
        this.view.showSuccessDialog();
    }

    @Override
    public void onError(String title, String msg) {
        this.view.hideLoading();
        this.view.dialogError(title, msg);
    }

    @Override
    public void onApiError(ApiResponseType error) {
//        this.view.showEptyState(error);
    }

    @Override
    public void onSuccess(GPlaces places) {
        this.view.hideProgress();
        this.view.hideErrorPlaces();

        if(this.places == null){
            this.places = places;
            this.adapter = new PlacesAdapter(places, view.getContext(), this);
            this.view.setPlacesAdapter(adapter);
        }else{
            if(adapter.getItemCount() > 0)
                adapter.removeAllItens();

            adapter.addAll(places.getPredictions());
        }

    }

    @Override
    public void onPlacesErros(ApiResponseType error) {
        if(adapter != null && adapter.getItemCount() > 0)
            adapter.removeAllItens();

        this.view.hideProgress();
        this.view.setErrorPlaces(error);
    }

    @Override
    public void onSuccess(GPlace place) {
        this.view.setPlaceSuggest(place);
    }
}
