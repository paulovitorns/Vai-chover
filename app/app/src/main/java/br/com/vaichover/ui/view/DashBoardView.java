package br.com.vaichover.ui.view;

import android.support.v4.app.Fragment;

import br.com.vaichover.model.UserPreferences;

/**
 * © Copyright 2017 Ande.
 * Autor : Paulo Sales - dev@paulovns.com.br
 * Empresa : Ande app.
 */

public interface DashBoardView extends BaseView {

    void setUserInfo(UserPreferences user);

    void loadDefaultFragment();

    void changeFragment(Fragment fragment);

    void onTapDashMap();

    void onTapDashList();

    void setPic();

}
