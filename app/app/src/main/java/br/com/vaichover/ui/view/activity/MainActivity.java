package br.com.vaichover.ui.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import br.com.vaichover.R;
import br.com.vaichover.model.ApiResponseType;
import br.com.vaichover.model.OpenWeatherMap;
import br.com.vaichover.model.UserPreferences;
import br.com.vaichover.ui.presenter.MainPresenter;
import br.com.vaichover.ui.presenter.impl.MainPresenterImpl;
import br.com.vaichover.ui.view.DashBoardView;
import br.com.vaichover.ui.view.fragment.MapsFragment;
import br.com.vaichover.util.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class MainActivity extends BaseActivity implements DashBoardView {


    @Bind(R.id.frameLayout) FrameLayout frameLayout;

    private boolean             confirmedExit = false;
    private Menu                menu;
    private UserPreferences     user;
    private MainPresenter       presenter;
    private boolean             isFullLoaded;
    private static final int    LOCATION_REQUEST_CODE  = 200;
    private OpenWeatherMap      map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupNavigateActionBarHome(R.string.window_dash);

        if(savedInstanceState != null){
            user    = (UserPreferences) savedInstanceState.getSerializable(UserPreferences.KEY);
            map     = (OpenWeatherMap) savedInstanceState.getSerializable(OpenWeatherMap.KEY);
        }

        this.presenter = new MainPresenterImpl(this, user, map);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putSerializable(OpenWeatherMap.KEY, map);
        outState.putSerializable(UserPreferences.KEY, user);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.isNetworkAvailable()){
            if (isFullLoaded && presenter.hasLocationPermission()) {
                emptyStateContainer.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                this.presenter.tryAgain();
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if(this.user == null && presenter.hasLocationPermission())
            this.presenter.updateToolbar();

        isFullLoaded = true;

        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onBackPressed() {
        if(confirmedExit) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_exit_alert), Toast.LENGTH_SHORT).show();
            this.presenter.prepareDefaultFragment();
            confirmedExit = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_list) {
            item.setVisible(false);
            MenuItem mapsMenu = menu.findItem(R.id.action_gmaps);
            mapsMenu.setVisible(true);
            onTapDashList();
            return true;
        }

        if (id == R.id.action_gmaps) {
            item.setVisible(false);
            MenuItem listMenu = menu.findItem(R.id.action_list);
            listMenu.setVisible(true);
            onTapDashMap();
            return true;
        }

        if(id == R.id.action_scale){
            Intent intent = new Intent(this, PreferencesActivity.class);
            intent.putExtra(UserPreferences.KEY, user);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDegreesPreferencesIcon(UserPreferences user) {
        this.user           = user;

        if(this.map == null)
            this.presenter.getWeathers();

        if(this.user.getDegrees() == UserPreferences.Degrees.CELSIUS)
            showCelsiusIcon();
        else
            showFahrenheitIcon();

    }

    @Override
    public void loadDefaultFragment(Fragment fragment) {

        frameLayout.setVisibility(View.VISIBLE);

        confirmedExit = true;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void changeFragment(Fragment fragment) {

        frameLayout.setVisibility(View.VISIBLE);

        if(confirmedExit)
            confirmedExit = false;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void showCelsiusIcon() {
        MenuItem mapsScale = menu.findItem(R.id.action_scale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mapsScale.setIcon(getDrawable(R.drawable.ic_celsius));
        else
            mapsScale.setIcon(getResources().getDrawable(R.drawable.ic_celsius));
    }

    @Override
    public void showFahrenheitIcon() {
        MenuItem mapsScale = menu.findItem(R.id.action_scale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mapsScale.setIcon(getDrawable(R.drawable.ic_fahrenheit));
        else
            mapsScale.setIcon(getResources().getDrawable(R.drawable.ic_fahrenheit));
    }

    @Override
    public void onTapDashMap() {
        this.presenter.prepareFragment();
    }

    @Override
    public void onTapDashList() {
        this.presenter.prepareDefaultFragment();
    }

    @Override
    public void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_REQUEST_CODE);
    }

    @Override
    public void updateWeathers(OpenWeatherMap map) {
        this.map = map;
    }

    @Override
    public void updateWeathersForNewLocation(OpenWeatherMap map, UserPreferences user) {
        this.presenter.updateWeathersAndUserData(map, user);
    }

    @Override
    public void showEptyState(ApiResponseType error) {
        frameLayout.setVisibility(View.GONE);
        super.showEptyState(error);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {

                if ( grantResults.length > 0
                        && ((grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        || (grantResults[1] == PackageManager.PERMISSION_GRANTED))) {

                    presenter.onGetUserLocation();
                }else{
                    showEptyState(ApiResponseType.PERMISSION_DENIED);
                }

                return;
            }

        }
    }

    @OnClick(R.id.btnReTry)
    public void onTapRetry(){
        if (presenter.hasLocationPermission())
            presenter.tryAgain();
        else
            requestLocationPermission();
    }


}
