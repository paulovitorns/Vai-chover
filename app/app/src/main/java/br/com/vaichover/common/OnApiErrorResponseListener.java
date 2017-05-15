package br.com.vaichover.common;

import br.com.vaichover.model.ApiResponseType;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface OnApiErrorResponseListener {
    void onApiError(ApiResponseType error);
}
