package br.com.vaichover.business.service.impl;

import android.util.Log;

import br.com.vaichover.R;
import br.com.vaichover.VaiChoverApp;
import br.com.vaichover.business.api.Api;
import br.com.vaichover.business.api.vo.response.OpenWeatherMapResponseVO;
import br.com.vaichover.business.service.ApiErrorService;
import br.com.vaichover.business.service.OpenWeatherService;
import br.com.vaichover.common.OnOpenWeatherFinishedListener;
import br.com.vaichover.model.ApiResponseType;
import br.com.vaichover.model.OpenWeatherMap;
import br.com.vaichover.model.UserPreferences;
import br.com.vaichover.util.Utils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class OpenWeatherServiceImpl implements OpenWeatherService {

    ApiErrorService apiErrorService = new ApiErrorServiceImpl();

    @Override
    public void requestData(final OnOpenWeatherFinishedListener listener, UserPreferences user) {

        String appId = VaiChoverApp.getContext().getString(R.string.apiKey);

        Call<OpenWeatherMapResponseVO> call = Api.getAdapter().requestWeather(
                user.getPlace().getResult().getGeometry().getLat(),
                user.getPlace().getResult().getGeometry().getLng(),
                user.getRadius(),
                user.getDegrees().getUnits(),
                user.getLang(),
                appId
        );

        call.enqueue(new Callback<OpenWeatherMapResponseVO>() {
            @Override
            public void onResponse(Response<OpenWeatherMapResponseVO> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    if(response.body().list.size() > 0)
                        listener.onSuccess(new OpenWeatherMap(response.body()));
                    else
                        listener.onApiError(ApiResponseType.EMPTY_STATE);
                }else{
                    Log.e("Characters Error","Response Error 4xx/5xx");
                    apiErrorService.processErrorResponse(response, listener);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("LOGIN_ERROR","Characters Request");
                Log.e("LOGIN_ERROR","Characters Request "+t.getMessage());
                Log.e("LOGIN_ERROR","Characters Request "+t.getCause());
                Log.e("LOGIN_ERROR","Characters Request "+t.getStackTrace());
                if(!Utils.isNetworkAvailable()){
                    listener.onApiError(ApiResponseType.NO_INTENET_CONNECTION);
                }else{
                    listener.onApiError(ApiResponseType.SERVER_TIMEOUT);
                }
            }
        });

    }
}
