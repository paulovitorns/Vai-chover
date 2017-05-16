package br.com.vaichover.ui.view;

import android.app.Activity;
import android.content.Context;

/**
 * © Copyright 2016 Skatavel.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Skatavel app.
 */

public interface BaseFragmentView {

    void showLoading();

    void hideLoading();

    Context getContext();

}
