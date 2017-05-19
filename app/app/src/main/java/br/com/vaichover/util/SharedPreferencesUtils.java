package br.com.vaichover.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.com.vaichover.R;
import br.com.vaichover.VaiChoverApp;
import br.com.vaichover.model.Session;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class SharedPreferencesUtils {

    private static final String KEY             = VaiChoverApp.getContext().getString(R.string.shared_session);
    private static final String SESSION_DATA    = VaiChoverApp.getContext().getString(R.string.shared_session_data);

    private static SharedPreferences getPreferences(){
        SharedPreferences sharedPreferences = VaiChoverApp.getContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static void saveSessionData(Session session){

        SharedPreferences preferences   = getPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        String dataPersistence          = new Gson().toJson(session, Session.class);

        editor.putString(SESSION_DATA, dataPersistence).commit();
    }

    public static void unsetSessionData(){
        SharedPreferences sharedPreferences = getPreferences();
        SharedPreferences.Editor editor     = sharedPreferences.edit();

        editor.putString(SESSION_DATA, null).commit();
    }

    public static Session getSessionData(){
        SharedPreferences sharedPreferences = getPreferences();
        String sessionStr                   = sharedPreferences.getString(SESSION_DATA, "");
        return new Gson().fromJson(sessionStr, Session.class);
    }


}
