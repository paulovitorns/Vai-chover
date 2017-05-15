package br.com.vaichover.business.api;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import br.com.vaichover.BuildConfig;
import br.com.vaichover.R;
import br.com.vaichover.VaiChoverApp;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class ApiClient extends OkHttpClient {

    private static ApiClient instance;
    private final static long TIMEOUT = VaiChoverApp.getContext()
            .getResources().getInteger(R.integer.default_api_timeout);

    private ApiClient(){};

    public static ApiClient getInstance(){

        if (instance == null){
            instance = new ApiClient();

            if (BuildConfig.DEBUG) {
                instance.interceptors().add(getLogInterceptor());
            }

            instance.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
            instance.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
            instance.setWriteTimeout(TIMEOUT, TimeUnit.SECONDS);
        }
        return instance;
    }

    private static HttpLoggingInterceptor getLogInterceptor(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override public void log(String message) {
                        Log.d("OkHttp", message);
                    }
                });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

}
