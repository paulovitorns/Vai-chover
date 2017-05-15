package br.com.vaichover;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;
import android.util.Log;

import com.squareup.okhttp.internal.Util;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

//        TODO: Picasso lib for download images and save in cache
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));

        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

//        TODO:: descomente para gerar uma hash sha1 do app
//        printHashKey();
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

    private void printHashKey() {

        try{

            PackageInfo info = getPackageManager().getPackageInfo(
                    "br.com.vaichover.dev",
                    PackageManager.GET_SIGNATURES
            );

            for(Signature signature : info.signatures){

                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.d("HASHKEY", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT));
            }

        }catch (PackageManager.NameNotFoundException pn){
            Log.d("HASHKEY_ERROR", pn.getMessage());
            pn.getCause();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.d("HASHKEY_ERROR", e.getMessage());
            e.getCause();
        }

    }
}
