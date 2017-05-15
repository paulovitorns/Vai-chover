package br.com.vaichover;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.squareup.okhttp.internal.Util;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class VaiChoverApp extends MultiDexApplication {

    private static Context  context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        //TODO: Picasso lib for download images and save in cache
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));

        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

    }

    /**
     * Recupera o Context da Aplicação
     * <p>
     *     Metodo usado para recuperar o context geral da aplicação
     * </p>
     *
     * @return      Context  context da aplicação
     * @see         Context
     */
    public static Context getContext() {
        return context;
    }

}
