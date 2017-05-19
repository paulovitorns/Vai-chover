package br.com.vaichover.ui.view.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.vaichover.R;
import br.com.vaichover.model.OpenWeatherMap;
import br.com.vaichover.model.OpenWeatherMapResult;
import br.com.vaichover.model.UserPreferences;
import br.com.vaichover.ui.adapter.WeatherInfoAdapter;
import br.com.vaichover.ui.presenter.MapsPresenter;
import br.com.vaichover.ui.presenter.impl.MapsPresenterImpl;
import br.com.vaichover.ui.view.DashBoardView;
import br.com.vaichover.ui.view.MapsFragmentView;
import br.com.vaichover.util.Utils;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class MapsFragment extends Fragment implements MapsFragmentView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        OnMapReadyCallback,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnInfoWindowLongClickListener,
        GoogleMap.OnInfoWindowCloseListener{

    private View            view;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap       gMap;
    private Location        myLocation;
    private MapsPresenter   presenter;

    private static final int PERMISSION_LOCATION_REQUEST_CODE = 200;
    private static final int ZOOM_MAPS = 10;

    private boolean firstLoad = true;

    public static MapsFragment newInstance(){
        return new MapsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.mapFrag, mapFragment).commit();
        mapFragment.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        ButterKnife.bind(this, view);

        presenter = new MapsPresenterImpl(this, getArguments());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!Utils.isNetworkAvailable()){
        }else{
            onStart();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        presenter.getMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("CONNECTION_SUSPENDED", "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("CONNECTION_FAILED", "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.gMap = googleMap;

        // Hide the zoom controls as the button panel will cover it.
        this.gMap.getUiSettings().setZoomControlsEnabled(false);

        this.gMap.setOnCameraMoveStartedListener(this);
        this.gMap.setOnCameraIdleListener(this);
        this.gMap.setInfoWindowAdapter(new WeatherInfoAdapter(getActivity()));
        this.gMap.setOnMarkerClickListener(this);
        this.gMap.setOnInfoWindowClickListener(this);
        this.gMap.setOnInfoWindowCloseListener(this);
        this.gMap.setOnInfoWindowLongClickListener(this);

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = this.gMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.gmaps_style));

            if (!success) {
                Log.e("MapsActivityRaw", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivityRaw", "Can't find style.", e);
        }
    }

    @OnClick(R.id.fabGoToLocation)
    @Override
    public void onClickFabLocale() {
        presenter.getMySavedLocation();
        presenter.userHasDrag();
    }

    @Override
    public void requestLocationPermission() {
        requestPermissions(
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_LOCATION_REQUEST_CODE);
    }

    @Override
    public void drawMarker() {

        CameraUpdate updateCam = CameraUpdateFactory.newLatLngZoom(
                new LatLng(myLocation.getLatitude(), myLocation.getLongitude()),
                ZOOM_MAPS);

        gMap.moveCamera(updateCam);

    }

    @Override
    public void drawMarker(OpenWeatherMapResult result) {

        String temp = getString(R.string.temp);
        temp = temp.replace("...", String.valueOf(Math.round(result.getMain().getTemp())));

        gMap.addMarker(new MarkerOptions()
                .position(new LatLng(result.getCoord().getLat(), result.getCoord().getLon()))
                .title(result.getName())
                .snippet(temp)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_on_white_36dp))
        ).setTag(result);
    }

    @Override
    public void clearAllMarkers() {
        gMap.clear();
    }

    @Override
    public void updateLocation(Location location) {

        myLocation = location;

        if(myLocation != null){

            drawMarker();
        }
    }

    @Override
    public GoogleApiClient getGmapsClient() {
        return this.mGoogleApiClient;
    }

    @Override
    public void updateMainView(OpenWeatherMap map, UserPreferences user) {
        ((DashBoardView)getActivity()).updateWeathersForNewLocation(map, user);
    }

    @Override
    public void showLoading() {
        ((DashBoardView)getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((DashBoardView)getActivity()).hideLoading();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_LOCATION_REQUEST_CODE: {

                if ( grantResults.length > 0
                        && ((grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        || (grantResults[1] == PackageManager.PERMISSION_GRANTED))) {

                    if(firstLoad){
                        presenter.getMyLocation();
                    }
                    firstLoad = false;
                    presenter.getMyLocation();

                } else {

                }
                return;
            }

        }
    }

    @Override
    public void onCameraMoveStarted(int i) {
        if(i == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE){
            presenter.userHasDrag();
        }
    }

    @Override
    public void onCameraIdle() {
        if(presenter.hasToGetNewLocation())
            presenter.requestWeathers(gMap.getCameraPosition().target);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onInfoWindowClose(Marker marker) {

    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        presenter.uiHasDrag();
        return false;
    }

}
