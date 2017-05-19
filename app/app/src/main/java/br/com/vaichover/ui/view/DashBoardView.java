package br.com.vaichover.ui.view;

import android.support.v4.app.Fragment;

import br.com.vaichover.model.OpenWeatherMap;
import br.com.vaichover.model.UserPreferences;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface DashBoardView extends BaseView {

    void showDegreesPreferencesIcon(UserPreferences user);

    void loadDefaultFragment(Fragment fragment);

    void changeFragment(Fragment fragment);

    void showCelsiusIcon();

    void showFahrenheitIcon();

    void onTapDashMap();

    void onTapDashList();

    void requestLocationPermission();

    void updateWeathers(OpenWeatherMap map);
}
