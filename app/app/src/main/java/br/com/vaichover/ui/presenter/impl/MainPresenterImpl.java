package br.com.vaichover.ui.presenter.impl;

import br.com.vaichover.business.service.SessionManagerService;
import br.com.vaichover.business.service.impl.SessionManagerServiceImpl;
import br.com.vaichover.model.Session;
import br.com.vaichover.ui.presenter.MainPresenter;
import br.com.vaichover.ui.view.DashBoardView;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class MainPresenterImpl implements MainPresenter {

    private DashBoardView view;
    private SessionManagerService service;

    public MainPresenterImpl(DashBoardView view) {
        this.view = view;
        this.init();
    }

    @Override
    public void init() {
        this.service = new SessionManagerServiceImpl();
        this.view.loadDefaultFragment();
    }

    @Override
    public void tryAgain() {

    }

    @Override
    public void getUserInfor() {

    }
}
