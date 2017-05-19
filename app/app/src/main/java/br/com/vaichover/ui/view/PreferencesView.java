package br.com.vaichover.ui.view;

import android.support.v7.widget.RecyclerView;

import br.com.vaichover.model.ApiResponseType;
import br.com.vaichover.model.GPlace;
import br.com.vaichover.model.UserPreferences;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface PreferencesView extends BaseView {

    void setDataInfoUser(UserPreferences user);

    void setCelsiusDegrees();

    void setFahrenheitDegrees();

    void onClickBtnSave();

    void setLocaleEmptyError();

    void setLocaleDefaultState();

    void showSuccessDialog();

    void dialogError(String title, String msg);

    void navigateToDash();

    void showProgress();

    void hideProgress();

    void loadAdapter();

    void setPlacesAdapter(RecyclerView.Adapter adapter);

    void setErrorPlaces(ApiResponseType error);

    void hideErrorPlaces();

    void setPlaceSuggest(GPlace place);
}
