package br.com.vaichover.business.service.impl;

import android.util.Log;

import br.com.vaichover.R;
import br.com.vaichover.VaiChoverApp;
import br.com.vaichover.business.api.Api;
import br.com.vaichover.business.api.vo.response.GPlaceResponseVO;
import br.com.vaichover.business.service.ApiErrorService;
import br.com.vaichover.business.service.GPlaceService;
import br.com.vaichover.common.OnPlaceResponseFinishedListener;
import br.com.vaichover.model.ApiResponseType;
import br.com.vaichover.model.GPlace;
import br.com.vaichover.model.GPlacesAutoComplete;
import br.com.vaichover.util.Utils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class GPlaceServiceImpl implements GPlaceService {

    ApiErrorService apiErrorService = new ApiErrorServiceImpl();

    @Override
    public void resquestPlace(final OnPlaceResponseFinishedListener listener, GPlacesAutoComplete autoComplete) {

        String token = VaiChoverApp.getContext().getString(R.string.gmapsApiKey);

        Call<GPlaceResponseVO> call = Api.getGmapsAdapter().requestPlace(
                autoComplete.getPlace_id(),
                token
        );

        call.enqueue(new Callback<GPlaceResponseVO>() {
            @Override
            public void onResponse(Response<GPlaceResponseVO> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    if(response.body().result != null)
                        listener.onSuccess(new GPlace(response.body()));
                    else
                        listener.onApiError(ApiResponseType.EMPTY_STATE);
                }else{
                    Log.e("Place Error","Response Error 4xx/5xx");
                    apiErrorService.processErrorResponse(response, listener);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("LOGIN_ERROR","Place Request");
                Log.e("LOGIN_ERROR","Place Request "+t.getMessage());
                Log.e("LOGIN_ERROR","Place Request "+t.getCause());
                Log.e("LOGIN_ERROR","Place Request "+t.getStackTrace());
                if(!Utils.isNetworkAvailable()){
                    listener.onApiError(ApiResponseType.NO_INTENET_CONNECTION);
                }else{
                    listener.onApiError(ApiResponseType.SERVER_TIMEOUT);
                }
            }
        });

    }

}
