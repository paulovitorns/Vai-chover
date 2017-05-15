package br.com.vaichover.business.service.impl;

import java.util.Calendar;

import br.com.vaichover.BuildConfig;
import br.com.vaichover.business.service.AboutService;
import br.com.vaichover.common.AboutResultListener;
import br.com.vaichover.util.DateUtils;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class AboutServiceImpl implements AboutService {

    @Override
    public void getConfigInfos(AboutResultListener listener) {

        Calendar calendar   = Calendar.getInstance();
        listener.onSuccess(BuildConfig.VERSION_NAME, DateUtils.getYearFromDate(calendar.getTime()));
    }

}
