package br.com.vaichover.common;

import br.com.vaichover.model.ApiResponseType;
import br.com.vaichover.model.GPlaces;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public interface OnGPlacesRequestFinished extends BaseCommonListener {
    void onSuccess(GPlaces places);

    void onPlacesErros(ApiResponseType error);
}
