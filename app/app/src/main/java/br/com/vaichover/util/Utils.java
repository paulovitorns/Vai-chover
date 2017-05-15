package br.com.vaichover.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.vaichover.VaiChoverApp;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class Utils {

    /**
     * Retorna true caso haja uma conexão ativa ou false para negativo
     * <p>
     * Este metodo é usado nos serviços antes de iniciar a chamada
     * a API. Mas o mesmo pode ser implementado em qualquer outra
     * parte do projeto.
     * </p>
     *
     * @return      boolean informando o status da conexão do usuário
     * @see         boolean
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) VaiChoverApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static Gson getGson(){
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
    }

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, VaiChoverApp.getContext().getResources().getDisplayMetrics());
    }
}
