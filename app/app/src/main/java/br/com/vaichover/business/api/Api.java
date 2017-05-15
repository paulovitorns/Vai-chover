package br.com.vaichover.business.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.HttpUrl;

import br.com.vaichover.R;
import br.com.vaichover.VaiChoverApp;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class Api {

    private static ServiceApi adapter;

    public static final String url =  VaiChoverApp.getContext().getString(R.string.url_api);
    public static final HttpUrl API_URL = HttpUrl.parse(url);

    private Api() {}

    private static ServiceApi createRestAdapter(){

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        System.setProperty("http.keepAlive", "false");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(ApiClient.getInstance())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ServiceApi.class);
    }

    public static ServiceApi getAdapter(){
        if (adapter == null)
            adapter = createRestAdapter();
        return adapter;
    }

}
