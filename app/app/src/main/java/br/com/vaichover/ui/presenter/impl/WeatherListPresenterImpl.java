package br.com.vaichover.ui.presenter.impl;

import android.os.Bundle;

import br.com.vaichover.model.OpenWeatherMap;
import br.com.vaichover.ui.adapter.WeatherAdapter;
import br.com.vaichover.ui.presenter.WeatherListPresenter;
import br.com.vaichover.ui.view.WeatherListView;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class WeatherListPresenterImpl implements WeatherListPresenter {

    private WeatherListView view;
    private OpenWeatherMap  map;

    public WeatherListPresenterImpl(WeatherListView view, Bundle bundle) {
        this.view   = view;
        this.map    = (OpenWeatherMap) bundle.getSerializable(OpenWeatherMap.KEY);
        this.init();
    }

    @Override
    public void init() {
        this.view.loadAdapter();
        this.view.setAdapter(new WeatherAdapter(map, view.getContext()));
    }

    @Override
    public void tryAgain() {}
}
