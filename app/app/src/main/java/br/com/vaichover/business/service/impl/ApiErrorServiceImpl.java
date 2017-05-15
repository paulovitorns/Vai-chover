package br.com.vaichover.business.service.impl;

import android.util.Log;

import br.com.vaichover.business.service.ApiErrorService;
import br.com.vaichover.common.OnApiErrorResponseListener;
import br.com.vaichover.model.ApiResponseType;
import retrofit.Response;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public class ApiErrorServiceImpl implements ApiErrorService {

    @Override
    public void processErrorResponse(Response response, OnApiErrorResponseListener listener) {

        Log.e("ERROR_RESPONSE",response.message());

        switch (response.code()){
            case 500:
                listener.onApiError(ApiResponseType.SERVER_ERROR);
                break;
            default:
                ApiResponseType type = ApiResponseType.SERVER_ERROR;
                type.setMsgError(response.message());
                listener.onApiError(type);
                break;
        }

    }

}
