package br.com.vaichover.ui.view;

import android.support.v7.widget.RecyclerView;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface WeatherListView extends BaseFragmentView {

    void loadAdapter();

    void setAdapter(RecyclerView.Adapter adapter);
}
