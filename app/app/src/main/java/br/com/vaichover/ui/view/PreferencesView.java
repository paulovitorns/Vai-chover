package br.com.vaichover.ui.view;

import java.io.File;
import java.io.IOException;

import br.com.vaichover.model.UserPreferences;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface PreferencesView extends BaseView {

    void setDataInfoUser(UserPreferences user);

    void onClickBtnSave();

    void setLocaleEmptyError();

    void setLocaleDefaultState();

    void showSuccessDialog();

    void dialogError(String title, String msg);
}
