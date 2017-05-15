package br.com.vaichover.ui.view;

import android.support.v4.app.Fragment;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface DashBoardView extends BaseView {

    void loadDefaultFragment();

    void changeFragment(Fragment fragment);

    void onTapSearch();

    void onTapSettings();

    void onTapDashMap();

    void onTapDashList();

}
