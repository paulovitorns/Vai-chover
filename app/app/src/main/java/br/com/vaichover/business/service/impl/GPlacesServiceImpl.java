package br.com.vaichover.business.service.impl;

import android.util.Log;

import br.com.vaichover.R;
import br.com.vaichover.VaiChoverApp;
import br.com.vaichover.business.api.Api;
import br.com.vaichover.business.api.vo.response.GPlacesResponseVO;
import br.com.vaichover.business.service.ApiErrorService;
import br.com.vaichover.business.service.GPlacesService;
import br.com.vaichover.common.OnGPlacesRequestFinished;
import br.com.vaichover.model.ApiResponseType;
import br.com.vaichover.model.GPlaces;
import br.com.vaichover.util.Utils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class GPlacesServiceImpl implements GPlacesService {

    ApiErrorService apiErrorService = new ApiErrorServiceImpl();

    @Override
    public void onRequestPlaces(final OnGPlacesRequestFinished listener, String param) {

        String token = VaiChoverApp.getContext().getString(R.string.gmapsApiKey);
        String types = "geocode";
        param = param.replaceAll(" ", "+");

        Call<GPlacesResponseVO> call = Api.getGmapsAdapter().requestPlaces(
                param,
                types,
                token
        );

        call.enqueue(new Callback<GPlacesResponseVO>() {

            @Override
            public void onResponse(Response<GPlacesResponseVO> response, Retrofit retrofit) {

                if(response.isSuccess()){
                    if(response.body().predictions.size() > 0)
                        listener.onSuccess(new GPlaces(response.body()));
                    else
                        listener.onPlacesErros(ApiResponseType.EMPTY_STATE);
                }else{
                    Log.e("Places Error","Response Error 4xx/5xx");
                    listener.onPlacesErros(ApiResponseType.SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e("LOGIN_ERROR","Places Request");
                Log.e("LOGIN_ERROR","Places Request " + t.getMessage());
                Log.e("LOGIN_ERROR","Places Request " + t.getCause());
                Log.e("LOGIN_ERROR","Places Request " + t.getStackTrace());

                if(!Utils.isNetworkAvailable()){
                    listener.onPlacesErros(ApiResponseType.NO_INTENET_CONNECTION);
                }else{
                    listener.onPlacesErros(ApiResponseType.SERVER_TIMEOUT);
                }

            }

        });

    }

}
