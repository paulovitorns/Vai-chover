package br.com.vaichover.ui.view;

import android.content.Context;

import br.com.vaichover.model.ApiResponseType;


/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface BaseView {

    void setupNavigateActionBarHome(int resId);

    void setupNavigateActionBar(int resId);

    void setupNavigateActionBarModal(int resId);

    void showLoading();

    void hideLoading();

    void showEptyState(ApiResponseType error);

    Context getContext();

    void onBackPressed();

}
