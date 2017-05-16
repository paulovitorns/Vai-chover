package br.com.vaichover.ui.presenter.impl;

import br.com.vaichover.business.service.PreferencesUserService;
import br.com.vaichover.business.service.SessionManagerService;
import br.com.vaichover.business.service.impl.PreferencesUserServiceImpl;
import br.com.vaichover.business.service.impl.SessionManagerServiceImpl;
import br.com.vaichover.common.PreferencesResultListener;
import br.com.vaichover.model.Session;
import br.com.vaichover.model.UserPreferences;
import br.com.vaichover.ui.presenter.PreferencesPresenter;
import br.com.vaichover.ui.view.PreferencesView;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class PreferencesPresenterImpl implements PreferencesPresenter, PreferencesResultListener {

    private UserPreferences         user;
    private PreferencesView         view;
    private PreferencesUserService  service;
    private SessionManagerService   sessionManagerService;

    public PreferencesPresenterImpl(PreferencesView view) {
        this.view = view;
        this.init();
    }

    @Override
    public void init() {
        this.service = new PreferencesUserServiceImpl();
        this.sessionManagerService = new SessionManagerServiceImpl();

        Session session = this.sessionManagerService.getCurrentSession();

        if(session != null && session.getUser() != null){
            this.view.setDataInfoUser(session.getUser());
        }
    }

    @Override
    public void tryAgain() {}

    @Override
    public void sendToRegister(UserPreferences user) {
        this.view.showLoading();
        this.user = user;

        if(validateRegisterData()){
            this.service.registerClient(user, this);
        }else{
            this.view.hideLoading();
        }
    }

    @Override
    public void sendToUpdate(UserPreferences user) {
        this.view.showLoading();
        this.user = user;

        if(validateRegisterData()){
            this.service.updateClient(user, this);
        }else{
            this.view.hideLoading();
        }
    }

    @Override
    public void updateImagemUser(UserPreferences user) {
        this.sessionManagerService.updateCurrentSession(user);
    }

    @Override
    public boolean validateRegisterData() {

        if(this.user.getAddress().isEmpty()){
            this.view.setLocaleEmptyError();
            return false;
        }else{
            this.view.setLocaleDefaultState();
        }

        return true;
    }

    @Override
    public void onSuccess(UserPreferences user) {
        this.view.hideLoading();
        this.sessionManagerService.createNewSession(user);
        this.view.showSuccessDialog();
    }

    @Override
    public void onError(String title, String msg) {
        this.view.hideLoading();
        this.view.dialogError(title, msg);
    }
}
