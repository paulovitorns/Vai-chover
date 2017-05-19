package br.com.vaichover.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.vaichover.R;
import br.com.vaichover.model.OpenWeatherMap;
import br.com.vaichover.ui.adapter.WeatherAdapter;
import br.com.vaichover.ui.presenter.WeatherListPresenter;
import br.com.vaichover.ui.presenter.impl.WeatherListPresenterImpl;
import br.com.vaichover.ui.view.WeatherListView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class WeatherListFragment extends Fragment implements WeatherListView {

    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    private View view;
    private WeatherListPresenter presenter;

    public WeatherListFragment() {
    }

    public static WeatherListFragment newInstance(){
        return new WeatherListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_list_weather, container, false);

        ButterKnife.bind(this, view);

        presenter = new WeatherListPresenterImpl(this, getArguments());

        return view;
    }

    @Override
    public void loadAdapter() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}
}
